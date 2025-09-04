package tests;

import base.BaseTest;
import listeners.TestListener;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
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

public class ShadowDomTest extends BaseTest {

    @Test
    @Parameters("csvFilePath")
    public void testShadowDom(String csvFilePath) {
        WebDriver driver = getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        List<Map<String, String>> data = ReadDataFormCSV.read(csvFilePath);

        for (Map<String, String> row : data) {
            String url = row.get("url");

            try {
                if (url.contains("shadow")) {
                    driver.get(url);
                    TestListener.getTest().info("Navigated to URL: " + url);

                    // Locate Shadow DOM host element
                    WebElement host = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("book-app")));

                    // Access shadow root
                    SearchContext shadowRoot = (SearchContext) ((JavascriptExecutor) driver)
                            .executeScript("return arguments[0].shadowRoot", host);

                    // Interact with input inside Shadow DOM
                    WebElement searchBox = shadowRoot.findElement(By.cssSelector("input#input"));
                    searchBox.sendKeys("Selenium WebDriver");
                    TestListener.getTest().info("Entered text into Shadow DOM input");

                    // Validation
                    Assert.assertEquals(searchBox.getAttribute("value"), "Selenium WebDriver",
                            "Shadow DOM input should contain entered text");
                    TestListener.getTest().pass("Shadow DOM input contains expected text");
                }
            } catch (Exception e) {
                TestListener.getTest().fail("Shadow DOM test failed at URL: " + url + " | " + e.getMessage());
                Assert.fail("Shadow DOM handling failed at URL: " + url, e);
            }
        }
    }
}
