package tests;

import base.BaseTest;
import listeners.TestListener;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ReadDataFormCSV;

import java.time.Duration;
import java.util.List;

public class ScrollTest extends BaseTest {

    @Test
    public void testScrollingPage() {
        List<String[]> data = ReadDataFormCSV.read("src/main/resources/testdata.csv");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        for (String[] row : data) {
            String url = row[0];

            try {
                if (url.contains("infinite_scroll")) {
                    driver.get(url);
                    TestListener.getTest().info("Navigated to: " + url);

                    JavascriptExecutor js = (JavascriptExecutor) driver;

                    for (int i = 0; i < 3; i++) {
                        js.executeScript("window.scrollBy(0,1000)");
                        TestListener.getTest().info("Scrolled down [" + (i + 1) + "]");
                        Thread.sleep(1000);
                    }

                    Long scrollHeight = (Long) js.executeScript("return document.body.scrollHeight");
                    Long clientHeight = (Long) js.executeScript("return window.innerHeight");

                    Assert.assertTrue(scrollHeight > clientHeight, "Page did not scroll properly!");
                    TestListener.getTest().pass("Page scrolled successfully");
                }
            } catch (Exception e) {
                TestListener.getTest().fail("Scroll test failed: " + e.getMessage());
            }
        }
    }
}
