package tests;

import base.BaseTest;
import listeners.TestListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.ReadDataFormCSV;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public class SvgElementTest extends BaseTest {

    @Test
    @Parameters("csvFilePath")
    public void testSvgElement(String csvFilePath) {
        WebDriver driver = getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        List<Map<String, String>> data = ReadDataFormCSV.read(csvFilePath);

        for (Map<String, String> row : data) {
            String url = row.get("url");

            try {
                if (url.contains("svg")) {
                    driver.get(url);
                    TestListener.getTest().info("Navigated to URL: " + url);

                    // Example: Click USA in SVG
                    WebElement usa = wait.until(ExpectedConditions
                            .visibilityOfElementLocated(By.cssSelector("svg path[title='United States']")));

                    usa.click();
                    TestListener.getTest().info("Clicked on USA in SVG map");

                    // Validation
                    Assert.assertTrue(usa.isDisplayed(), "USA element should be visible in SVG");
                    TestListener.getTest().pass("SVG element 'USA' is visible and clickable");
                }
            } catch (Exception e) {
                TestListener.getTest().fail("SVG test failed for URL: " + url + " | " + e.getMessage());
                Assert.fail("SVG element handling failed for URL: " + url, e);
            }
        }
    }
}
