package nuevo;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Pasos {
	
	WebDriver driver;
	 
	@Given("^I have browser window open$")
	public void i_have_browser_window_open() throws Throwable {
		//System.setProperty("webdriver.chrome.driver", "D://chromedriver.exe");
		driver = new ChromeDriver();
	}
 
	@When("^I Navigate to santasusanastudenthouseinlima$")
	public void i_Navigate_to_santasusanastudenthouseinlima() throws Throwable {
		driver.get("http://santasusanastudenthouseinlima.com");
	}
 
	@When("^search for estudiantes$")
	public void search_for_cucumber() throws Throwable {
		driver.findElement(By.name("searchword")).sendKeys("estudiantes" + Keys.ENTER);
	}
 
	@Then("^I should see the number results page$")
	public void i_should_see_cucumber_results_page() throws Throwable {
		//Assert.assertEquals("Incorrect page title", "Total: encontrados 4 resultados", driver.);
		Assert.assertEquals("Total: encontrados 4 resultados.", driver.findElement(By.cssSelector("strong")).getText());
		driver.quit();
	}

}

