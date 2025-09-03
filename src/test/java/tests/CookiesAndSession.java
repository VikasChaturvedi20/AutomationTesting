package tests;

import base.BaseTest;
import listeners.TestListener;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.ReadDataFormCSV;
import com.aventstack.extentreports.ExtentTest;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class CookiesAndSession extends BaseTest {

    @Test
    @Parameters("csvFilePath")
    public void validLogin(String csvFilePath) {
        ExtentTest test = TestListener.getTest();

        // Read data with header mapping
        List<Map<String, String>> data = ReadDataFormCSV.read(csvFilePath);

        for (Map<String, String> row : data) {
            String url = row.get("url");
            String username = row.get("username");
            String password = row.get("password");

            try {
                // Navigate to URL
                getDriver().get(url);
                test.info("Navigated to URL: " + url);

                // Enter username
                getDriver().findElement(By.id("user-name")).clear();
                getDriver().findElement(By.id("user-name")).sendKeys(username);
                test.info("Entered username: " + username);

                // Enter password
                getDriver().findElement(By.id("password")).clear();
                getDriver().findElement(By.id("password")).sendKeys(password);
                test.info("Entered password: " + password);

                // Click login
                getDriver().findElement(By.id("login-button")).click();
                test.info("Clicked on Login button");

                // Validate login
                WebElement successMsg = getDriver().findElement(By.xpath("//span[text()='Products']"));
                Assert.assertTrue(successMsg.getText().contains("Products"));
                test.pass("Login successful for user: " + username);

                // Handle cookies
                handleCookies(test);

                // Handle session storage
                handleSessionStorage(test);

            } catch (Exception e) {
                test.fail("Login failed for user: " + username + " - " + e.getMessage());
                Assert.fail("Login failed for user: " + username, e);
            }
        }
    }

    private void handleCookies(ExtentTest test) {
        try {
            Cookie userCookie = new Cookie("username", "vikas");
            getDriver().manage().addCookie(userCookie);
            test.info("Added cookie: " + userCookie.getName() + " = " + userCookie.getValue());

            Set<Cookie> allCookies = getDriver().manage().getCookies();
            test.info("Retrieved cookies:");
            for (Cookie cookie : allCookies) {
                test.info(cookie.getName() + " = " + cookie.getValue());
            }

            getDriver().manage().deleteCookieNamed("username");
            test.info("Deleted cookie: username");

            getDriver().manage().deleteAllCookies();
            test.info("Deleted all cookies");

        } catch (Exception e) {
            test.fail("Error handling cookies: " + e.getMessage());
        }
    }

    private void handleSessionStorage(ExtentTest test) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) getDriver();

            js.executeScript("sessionStorage.setItem('token', '12345');");
            test.info("Added session storage item: token = 12345");

            String token = (String) js.executeScript("return sessionStorage.getItem('token');");
            test.info("Retrieved session storage item: token = " + token);

            js.executeScript("sessionStorage.removeItem('token');");
            test.info("Removed session storage item: token");

            js.executeScript("sessionStorage.clear();");
            test.info("Cleared all session storage");

        } catch (Exception e) {
            test.fail("Error handling session storage: " + e.getMessage());
        }
    }
}
