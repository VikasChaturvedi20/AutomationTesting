package tests;

import base.BaseTest;
import listeners.TestListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.ReadDataFormCSV;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TabTest extends BaseTest {

    @Test
    @Parameters("csvFilePath")
    public void testTabHandling(String csvFilePath) {
        WebDriver driver = getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        List<Map<String, String>> data = ReadDataFormCSV.read(csvFilePath);

        for (Map<String, String> row : data) {
            String url = row.get("url");
            try {
                if (url.contains("windows")) {
                    driver.get(url);
                    TestListener.getTest().info("Navigated to Tab URL: " + url);

                    String parentWindow = driver.getWindowHandle();

                    WebElement clickHereLink = wait.until(
                            ExpectedConditions.elementToBeClickable(By.linkText("Click Here")));
                    clickHereLink.click();
                    TestListener.getTest().info("Clicked on 'Click Here' to open new tab");

                    Set<String> handles = driver.getWindowHandles();
                    for (String handle : handles) {
                        if (!handle.equals(parentWindow)) {
                            driver.switchTo().window(handle);
                            TestListener.getTest().info("Switched to new tab with title: " + driver.getTitle());

                            // Optional: validation in new tab
                            WebElement body = wait.until(
                                    ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));
                            TestListener.getTest().info("New tab page loaded successfully");

                            driver.close();
                            TestListener.getTest().info("Closed the new tab");
                        }
                    }

                    driver.switchTo().window(parentWindow);
                    TestListener.getTest().info("Switched back to parent tab");
                }
            } catch (Exception e) {
                TestListener.getTest().fail("Tab test failed for URL: " + url + " due to: " + e.getMessage());
            }
        }
    }
}
