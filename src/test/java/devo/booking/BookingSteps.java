package devo;

import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class BookingSteps {

	String url="https://restful-booker.herokuapp.com";

	protected String body;
	protected int statusCode;
	protected Header contentheader;
	protected String bookingid;
	protected String token;

	@Before(value = "@Booking")
	public void beforeScenario() {

	}

	@After(value = "@Booking")
	public void afterScenario() {

	}

	@Given("^an existing booking id$")
	public void anExistingBookingId(Map<String, String> rows) throws Throwable {
		BookingRequest booking = new BookingRequest(rows.get("firstname").toString(), rows.get("lastname").toString(),
				Integer.parseInt(rows.get("totalprice")),Boolean.parseBoolean(rows.get("depositpaid")),
				new Bookingdtes(rows.get("checkin").toString(),rows.get("checkout").toString()),
				rows.get("additionalneeds").toString());
		//create booking
		createBooking(booking);
	}

	public HttpResponse createBooking(BookingRequest booking)  {
		try (CloseableHttpClient client = HttpClientBuilder.create().build()) {

			HttpPost postrequest = new HttpPost(url+"/booking");
			postrequest.setEntity(new StringEntity(new ObjectMapper().writeValueAsString(booking),ContentType.APPLICATION_JSON));
			postrequest.addHeader("content-type","application/json");
			postrequest.addHeader("Accept","application/json");
			HttpResponse response = client.execute(postrequest);

			this.statusCode = response.getStatusLine().getStatusCode();
			StringWriter writer = new StringWriter();
			if (response != null && response.getEntity() != null) {
				IOUtils.copy(response.getEntity().getContent(), writer, "UTF-8");
				this.body = writer.toString();
			}
			return response;

		} catch (IOException  e) {

			return null;
		}
	}

	public HttpResponse getBooking(String id)  {
		try (CloseableHttpClient client = HttpClientBuilder.create().build()) {

			URIBuilder builder = new URIBuilder(url);
			builder
					.setPath(String.format("/booking/%s", id));

			HttpGet getrequest = new HttpGet(builder.build());

			getrequest.addHeader("Accept","application/json");
			HttpResponse response = client.execute(getrequest);

			this.statusCode = response.getStatusLine().getStatusCode();
			this.contentheader = response.getFirstHeader("content-type");
			StringWriter writer = new StringWriter();
			if (response != null && response.getEntity() != null) {
				IOUtils.copy(response.getEntity().getContent(), writer, "UTF-8");
				this.body = writer.toString();
			}
			return response;

		} catch (IOException | URISyntaxException e) {

			return null;
		}
	}

	@When("^I get the details of booking id$")
	public void iGetTheDetailsOfBookingId() throws Throwable {
		//create booking with id
		ObjectMapper mapper = new ObjectMapper();
		BookingResponse bookingres = mapper.readValue(this.body, BookingResponse.class);
		this.bookingid = bookingres.getBookingid();
		getBooking(this.bookingid);
	}

	@And("^the details are correct$")
	public void theDetailsAreCorrect(Map<String, String> rows) throws Throwable {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		ObjectMapper mapper = new ObjectMapper();
		BookingRequest bookingres = mapper.readValue(this.body, BookingRequest.class);
		//if not valid it will throw an exception
		dateFormatter.parse(bookingres.getBookingdates().getCheckin());
		dateFormatter.parse(bookingres.getBookingdates().getCheckout());
		assertTrue(this.contentheader.toString().equalsIgnoreCase("Content-Type: application/json; charset=utf-8"));
		assertTrue(this.statusCode==Integer.parseInt(rows.get("responseCode")));
	}

	@And("^I authenticate to get a token$")
	public void iAuthenticateToGetAToken(Map<String, String> rows) throws Throwable {
		createToken(rows.get("username"),rows.get("password"));
	}

	public HttpResponse createToken(String user, String pass)  {
		try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
			JSONObject json = new JSONObject();
			json.put("username",user);
			json.put("password",pass);

			HttpPost postrequest = new HttpPost(url+"/auth");
			postrequest.setEntity(new StringEntity(json.toString(),ContentType.APPLICATION_JSON));
			postrequest.addHeader("content-type","application/json");
			HttpResponse response = client.execute(postrequest);

			this.statusCode = response.getStatusLine().getStatusCode();
			StringWriter writer = new StringWriter();
			if (response != null && response.getEntity() != null) {
				IOUtils.copy(response.getEntity().getContent(), writer, "UTF-8");
				this.body = writer.toString();
				this.token= new JSONObject(this.body).get("token").toString();
			}
			return response;

		} catch (IOException  e) {

			return null;
		}
	}

	public HttpResponse deleteToken(String token)  {
		try (CloseableHttpClient client = HttpClientBuilder.create().build()) {

			HttpDelete delrequest = new HttpDelete(url + "/booking/"+this.bookingid);
			delrequest.addHeader("content-type","application/json");
			delrequest.addHeader("Cookie","token="+token);
			HttpResponse response = client.execute(delrequest);

			this.statusCode = response.getStatusLine().getStatusCode();
			StringWriter writer = new StringWriter();
			if (response != null && response.getEntity() != null) {
				IOUtils.copy(response.getEntity().getContent(), writer, "UTF-8");
				this.body = writer.toString();
			}
			return response;

		} catch (IOException  e) {

			return null;
		}
	}

	@When("^I delete the booking id with token$")
	public void iDeleteTheBookingIdWithToken() throws Throwable {
		deleteToken(this.token);
	}

	@Then("^the id no longer exists$")
	public void theIdNoLongerExists(Map<String, String> rows) throws Throwable {
		assertTrue(this.statusCode==Integer.parseInt(rows.get("responseCode")));
	}
}


