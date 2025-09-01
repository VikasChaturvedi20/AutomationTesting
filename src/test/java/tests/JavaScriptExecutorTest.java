package tests;

import base.BaseTest;
import listeners.TestListener;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ReadDataFormCSV;

import java.time.Duration;
import java.util.List;

public class JavaScriptExecutorTest extends BaseTest {

    @Test
    public void testJavaScriptExecutor() {
        List<String[]> data = ReadDataFormCSV.read("src/main/resources/testdata.csv");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        for (String[] row : data) {
            String url = row[0];

            try {
                if (url.contains("javascript_alerts")) {
                    driver.get(url);
                    TestListener.getTest().info("Navigated to: " + url);

                    WebElement alertBtn = wait.until(
                            ExpectedConditions.elementToBeClickable(By.cssSelector("button[onclick='jsAlert()']"))
                    );

                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    js.executeScript("arguments[0].style.border='3px solid red'", alertBtn);
                    TestListener.getTest().info("Highlighted JS Alert button");

                    js.executeScript("arguments[0].click();", alertBtn);
                    TestListener.getTest().info("Clicked JS Alert button");

                    wait.until(ExpectedConditions.alertIsPresent()).accept();
                    TestListener.getTest().info("Accepted JS alert");

                    String resultText = wait.until(
                            ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#result"))
                    ).getText();

                    Assert.assertEquals(resultText, "You successfully clicked an alert", "Alert result mismatch!");
                    TestListener.getTest().pass("Verified alert result text");
                }
            } catch (Exception e) {
                TestListener.getTest().fail("JavaScriptExecutor test failed: " + e.getMessage());
            }
        }
    }
}
