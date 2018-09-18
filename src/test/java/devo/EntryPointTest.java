package devo;

import org.junit.runner.RunWith; 
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
 
@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features",
                 glue = "devo",
				 plugin = {"pretty"},
				 tags = {})

public class EntryPointTest {
 
}

