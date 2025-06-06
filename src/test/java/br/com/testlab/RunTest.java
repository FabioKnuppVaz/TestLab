package br.com.testlab;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import io.cucumber.junit.CucumberOptions.SnippetType;

@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = {"pretty"},
    monochrome = true,
    features = "classpath:features",
    extraGlue = "classpath:steps",
    snippets = SnippetType.CAMELCASE,
    tags = ""
)
public class RunTest {
}
