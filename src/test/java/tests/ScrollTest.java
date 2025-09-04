package tests;

import base.BaseTest;
import listeners.TestListener;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.ReadDataFormCSV;

import java.util.List;
import java.util.Map;

public class ScrollTest extends BaseTest {

    @Test
    @Parameters("csvFilePath")
    public void testScrollingPage(String csvFilePath) throws InterruptedException {
        WebDriver driver = getDriver();
        List<Map<String, String>> data = ReadDataFormCSV.read(csvFilePath);

        for (Map<String, String> row : data) {
            String url = row.get("url");

            try {
                if (url.contains("infinite_scroll")) {
                    driver.get(url);
                    TestListener.getTest().info("Navigated to URL: " + url);

                    JavascriptExecutor js = (JavascriptExecutor) driver;

                    // Scroll down 3 times
                    for (int i = 0; i < 3; i++) {
                        js.executeScript("window.scrollBy(0,1000)");
                        TestListener.getTest().info("Scrolled down [" + (i + 1) + "]");
                        Thread.sleep(1000); // Can replace with wait for content load if needed
                    }

                    // Validation
                    Long scrollHeight = (Long) js.executeScript("return document.body.scrollHeight");
                    Long clientHeight = (Long) js.executeScript("return window.innerHeight");
                    Assert.assertTrue(scrollHeight > clientHeight, "Page did not scroll properly!");
                    TestListener.getTest().pass("Page scrolled successfully");
                }
            } catch (Exception e) {
                TestListener.getTest().fail("Scroll test failed for URL: " + url + " - " + e.getMessage());
                Assert.fail("Scroll test failed for URL: " + url, e);
            }
        }
    }
}
