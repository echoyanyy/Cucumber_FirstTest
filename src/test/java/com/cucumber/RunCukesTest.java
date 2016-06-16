package com.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/feature/"},
        format = {"pretty", "html:target/cucumber", "json:target/cucumber.json"},
        glue = {"com.cucumber"},
        tags = {"@Login"}
)
public class RunCukesTest {
}
