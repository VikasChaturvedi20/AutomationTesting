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

public class WindowTest extends BaseTest {

    @Test
    @Parameters("csvFilePath")
    public void testWindowHandling(String csvFilePath) {
        WebDriver driver = getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        List<Map<String, String>> data = ReadDataFormCSV.read(csvFilePath);

        for (Map<String, String> row : data) {
            String url = row.get("url");

            try {
                if (url.contains("Windows.html")) {
                    driver.get(url);
                    TestListener.getTest().info("Navigated to Window URL: " + url);

                    String parentWindow = driver.getWindowHandle();

                    WebElement openWindowBtn = wait.until(
                            ExpectedConditions.elementToBeClickable(By.cssSelector("button.btn.btn-info")));
                    openWindowBtn.click();
                    TestListener.getTest().info("Clicked on button to open new window");

                    Set<String> handles = driver.getWindowHandles();
                    for (String handle : handles) {
                        if (!handle.equals(parentWindow)) {
                            driver.switchTo().window(handle);
                            TestListener.getTest().info("Switched to new window with title: " + driver.getTitle());

                            // Optional validation in new window
                            WebElement body = wait.until(
                                    ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));
                            TestListener.getTest().info("New window loaded successfully");

                            driver.close();
                            TestListener.getTest().info("Closed the new window");
                        }
                    }

                    driver.switchTo().window(parentWindow);
                    TestListener.getTest().info("Switched back to parent window");
                }
            } catch (Exception e) {
                TestListener.getTest().fail("Window test failed for URL: " + url + " due to: " + e.getMessage());
            }
        }
    }
}
