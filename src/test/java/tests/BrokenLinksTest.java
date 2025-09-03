/*package tests;

import base.BaseTest;
import listeners.TestListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ReadDataFormCSV;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.List;

public class BrokenLinksTest extends BaseTest {

    @Test
    public void testBrokenLinks() {
        List<String[]> data = ReadDataFormCSV.read("src/main/resources/testdata.csv");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        for (String[] row : data) {
            String url = row[0];

            try {
                if (url.contains("broken")) {
                    driver.get(url);
                    TestListener.getTest().info("Navigated to: " + url);

                    List<WebElement> links = driver.findElements(By.cssSelector("a"));

                    for (WebElement link : links) {
                        String linkUrl = link.getAttribute("href");
                        if (linkUrl != null && !linkUrl.isEmpty()) {
                            HttpURLConnection conn = (HttpURLConnection) new URL(linkUrl).openConnection();
                            conn.setRequestMethod("HEAD");
                            conn.connect();
                            int responseCode = conn.getResponseCode();

                            if (responseCode >= 400) {
                                TestListener.getTest().fail("Broken link: " + linkUrl + " | Code: " + responseCode);
                                Assert.fail("Broken link found: " + linkUrl);
                            } else {
                                TestListener.getTest().pass("Valid link: " + linkUrl);
                                Assert.assertTrue(responseCode < 400, "Valid link should have <400 status");
                            }
                        }
                    }
                }
            } catch (Exception e) {
            	TestListener.getTest().fail("Error at URL: " + url + " | " + e.getMessage());
                Assert.fail("Exception occurred: " + e.getMessage());
            }
        }
    }
}

*/

package tests;

import base.BaseTest;
import listeners.TestListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ReadDataFormCSV;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Map;

public class BrokenLinksTest extends BaseTest {

    @Test(groups = {"regression"})
    public void testBrokenLinks() {
        WebDriver driver = getDriver(); // ✅ use ThreadLocal driver
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // ✅ Read CSV as List<Map<String, String>>
        List<Map<String, String>> data = ReadDataFormCSV.read("src/main/resources/testdata.csv");

        for (Map<String, String> row : data) {
            String url = row.get("url"); // ✅ use column name

            try {
                if (url != null && url.contains("broken")) {
                    driver.get(url);
                    TestListener.getTest().info("Navigated to: " + url);

                    List<WebElement> links = driver.findElements(By.cssSelector("a"));

                    for (WebElement link : links) {
                        String linkUrl = link.getAttribute("href");

                        if (linkUrl != null && !linkUrl.isEmpty()) {
                            HttpURLConnection conn = (HttpURLConnection) new URL(linkUrl).openConnection();
                            conn.setRequestMethod("HEAD");
                            conn.connect();
                            int responseCode = conn.getResponseCode();

                            if (responseCode >= 400) {
                                TestListener.getTest().fail("❌ Broken link: " + linkUrl + " | Code: " + responseCode);
                                Assert.fail("Broken link found: " + linkUrl);
                            } else {
                                TestListener.getTest().pass("✅ Valid link: " + linkUrl + " | Code: " + responseCode);
                                Assert.assertTrue(responseCode < 400, "Valid link should have <400 status");
                            }
                        }
                    }
                }
            } catch (Exception e) {
                TestListener.getTest().fail("⚠️ Error at URL: " + url + " | " + e.getMessage());
                Assert.fail("Exception occurred: " + e.getMessage());
            }
        }
    }
}

