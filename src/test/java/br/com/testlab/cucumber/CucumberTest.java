package br.com.testlab.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.runner.RunWith;
import io.cucumber.junit.CucumberOptions.SnippetType;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = {"pretty"},
    monochrome = true,
    features = "classpath:features",
    extraGlue = "classpath:steps",
    snippets = SnippetType.CAMELCASE,
    tags = ""
)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@CucumberContextConfiguration
public class CucumberTest  {
}
