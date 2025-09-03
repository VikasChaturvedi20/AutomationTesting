package tests;

import base.BaseTest;
import listeners.TestListener;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.GoogleSearchPage;
import utils.ReadDataFormCSV;
import utils.RetryAnalyzer;

import java.util.List;
import java.util.Map;

public class GoogleSearch_AutoSuggestionDropdown extends BaseTest {

    @Test(groups = {"smoke"}, retryAnalyzer = RetryAnalyzer.class)
    @Parameters("csvFilePath")
    public void testAutoSuggestionSmoke(String csvFilePath) {
        WebDriver driver = getDriver();
        GoogleSearchPage searchPage = new GoogleSearchPage(driver);

        List<Map<String, String>> data = ReadDataFormCSV.read(csvFilePath);

        for (Map<String, String> row : data) {
            String url = row.get("url");        // CSV header
            String inputText = row.get("input"); // CSV header
            String expectedSuggestion = row.get("expected"); // new column for flexibility

            if (url.contains("playwright.dev")) {
                try {
                    driver.get(url);
                    TestListener.getTest().info("Navigated to URL: " + url);

                    searchPage.openSearchBox();
                    searchPage.enterSearchText(inputText);
                    TestListener.getTest().info("Entered search text: " + inputText);

                    boolean clicked = searchPage.selectSuggestion(expectedSuggestion);
                    Assert.assertTrue(clicked, "No matching suggestion found for: " + inputText);

                    String heading = searchPage.getHeadingText();
                    Assert.assertTrue(heading.contains(expectedSuggestion),
                            "Search result page heading mismatch!");

                    TestListener.getTest().pass("Search result displayed successfully for: " + inputText);

                } catch (Exception e) {
                    TestListener.getTest().fail("Failed for: " + inputText + " - " + e.getMessage());
                    Assert.fail("Search failed for: " + inputText, e);
                }
            }
        }
    }

    @Test(groups = {"regression"}, dependsOnMethods = "testAutoSuggestionSmoke")
    @Parameters("csvFilePath")
    public void verifySearchDependsOnSmoke(String csvFilePath) {
        WebDriver driver = getDriver();
        List<Map<String, String>> data = ReadDataFormCSV.read(csvFilePath);

        for (Map<String, String> row : data) {
            String url = row.get("url");
            if (url.contains("playwright.dev")) {
                driver.get(url);
                TestListener.getTest().info("Regression check navigation: " + url);

                Assert.assertTrue(driver.getTitle().contains("Playwright"),
                        "Title check failed for: " + url);
                TestListener.getTest().pass("Regression check successful on: " + url);
            }
        }
    }
}
