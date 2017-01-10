package nuevo;

import org.junit.runner.RunWith;
 
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
 
@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features",
                 glue = "nuevo",
				 plugin = { "pretty",
						 	"html:target/cucumber-html-report" },
				 tags = {})
public class CukeRunnerTest {
 
}

