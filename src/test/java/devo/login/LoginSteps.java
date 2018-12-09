package devo.login;

import java.util.Map;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import devo.login.pageObjects.LoginPage;
import devo.login.pageObjects.SecureAreaPage;

public class LoginSteps {

	protected WebDriver driver;
	protected ChromeOptions options;

	protected LoginPage loginP;
	protected SecureAreaPage secureP;

	@Before(value = "@Login")
	public void beforeScenario() {
		String path = System.getProperty("user.dir");

		System.setProperty("webdriver.chrome.driver", path + "/driver/chromedriver.exe");
		options = new ChromeOptions();
		options.addArguments("--no-sandbox");
	}

	@After(value = "@Login")
	public void afterScenario() {
		driver.close();
	}

	@Given("^I have browser window open$")
	public void openBrowser() throws Throwable {

		driver = new ChromeDriver(options);
		//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
 
	@When("^I navigate to login page (.*)$")
	public void navigateToLoginPage(String url) throws Throwable {
		loginP = new LoginPage(this.driver);
		loginP.goToPage(url);
	}
 
	@And("^login with the following credentials$")
	public void loginWithCredentials(Map<String, String> rows) throws Throwable {
		String username = rows.get("username");
		String password = rows.get("password");
		secureP = loginP.login(username,password);
	}
 
	@Then("^I am logged in succesfully$")
	public void checkLogged(Map<String, String> rows) throws Throwable {
		String message = rows.get("message");
		Assert.assertTrue("no",secureP.getLoginMessage().contains(message));
	}

	@Then("^I am not logged in$")
	public void checkNotLogged(Map<String, String> rows) throws Throwable {
		String message = rows.get("message");
		Assert.assertTrue("no",loginP.getLoginMessage().contains(message));
	}
}


