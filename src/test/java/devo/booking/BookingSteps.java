package devo.booking;

import com.fasterxml.jackson.databind.ObjectMapper;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import devo.booking.dto.Bookingdto;

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
	protected HttpResponse response;

	@Before(value = "@Booking")
	public void beforeScenario() {
	}

	@After(value = "@Booking")
	public void afterScenario() {
	}
	
	@Given("^I get the details of an existing booking id$")
	public void i_get_the_details_of_an_existing_booking_id() throws Throwable {
		this.response = getExistingBooking();
	}

	public HttpResponse getExistingBooking()  {
		try (CloseableHttpClient client = HttpClientBuilder.create().build()) {

			URIBuilder builder = new URIBuilder(url);
			builder
					.setPath(String.format("/booking/%s", 4));

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

	@And("^the details are correct$")
	public void theDetailsAreCorrect(Map<String, String> rows) throws Throwable {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		ObjectMapper mapper = new ObjectMapper();
		Bookingdto bookingres =  mapper.readValue(this.body, Bookingdto.class);
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

	public HttpResponse deleteBookingwithToken(String token)  {
		try (CloseableHttpClient client = HttpClientBuilder.create().build()) {

			HttpDelete delrequest = new HttpDelete(url + "/booking/4");
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
		deleteBookingwithToken(this.token);
	}

	@Then("^the id no longer exists$")
	public void theIdNoLongerExists(Map<String, String> rows) throws Throwable {
		assertTrue(this.statusCode==Integer.parseInt(rows.get("responseCode")));
	}
}


