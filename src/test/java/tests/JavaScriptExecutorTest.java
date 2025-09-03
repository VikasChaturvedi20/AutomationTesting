package tests;

import base.BaseTest;
import listeners.TestListener;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

public class JavaScriptExecutorTest extends BaseTest {

    @Test
    @Parameters("csvFilePath")
    public void testJavaScriptExecutor(String csvFilePath) {
        WebDriver driver = getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        List<Map<String, String>> data = ReadDataFormCSV.read(csvFilePath);

        for (Map<String, String> row : data) {
            String url = row.get("url");   // ✅ using CSV header

            try {
                if (url.contains("javascript_alerts")) {
                    driver.get(url);
                    TestListener.getTest().info("Navigated to URL: " + url);

                    WebElement alertBtn = wait.until(
                            ExpectedConditions.elementToBeClickable(By.cssSelector("button[onclick='jsAlert()']"))
                    );

                    JavascriptExecutor js = (JavascriptExecutor) driver;

                    // Highlight element
                    js.executeScript("arguments[0].style.border='3px solid red'", alertBtn);
                    TestListener.getTest().info("Highlighted JS Alert button");

                    // Click using JS
                    js.executeScript("arguments[0].click();", alertBtn);
                    TestListener.getTest().info("Clicked JS Alert button");

                    // Handle Alert
                    wait.until(ExpectedConditions.alertIsPresent()).accept();
                    TestListener.getTest().info("Accepted JS alert");

                    // Validate Result
                    String resultText = wait.until(
                            ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#result"))
                    ).getText();

                    Assert.assertEquals(resultText, "You successfully clicked an alert",
                            "Alert result mismatch!");
                    TestListener.getTest().pass("Verified alert result text successfully");
                }
            } catch (Exception e) {
                TestListener.getTest().fail("Exception occurred: " + e.getMessage());
                Assert.fail("Test failed for URL: " + url, e);
            }
        }
    }
}
