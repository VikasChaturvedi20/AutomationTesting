package tests;

import base.BaseTest;
import listeners.TestListener;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ReadDataFormCSV;
import reports.ExtentManager;
import com.aventstack.extentreports.ExtentTest;

import java.util.List;
import java.util.Set;

public class CookiesAndSession extends BaseTest {

    @Test
    public void validLogin() {
        // Get ExtentTest instance
        ExtentTest test = TestListener.getTest();

        // Read data from CSV
        List<String[]> data = ReadDataFormCSV.read("src/main/resources/testdata.csv");

        for (String[] row : data) {
            String url = row[0];
            String username = row[1];
            String password = row[2];

            try {
                // Navigate to URL
                driver.get(url);
                TestListener.getTest().info("Navigated to URL: " + url);

                // Enter username
                driver.findElement(By.id("user-name")).clear();
                driver.findElement(By.id("user-name")).sendKeys(username);
                TestListener.getTest().info("Entered username: " + username);

                // Enter password
                driver.findElement(By.id("password")).clear();
                driver.findElement(By.id("password")).sendKeys(password);
                TestListener.getTest().info("Entered password: " + password);

                // Click login
                driver.findElement(By.id("login-button")).click();
                TestListener.getTest().info("Clicked on Login button");

                // Validate login
               
                WebElement successMsg = driver.findElement(By.xpath("//span[text()='Products']"));
                Assert.assertTrue(successMsg.getText().contains("Products"));
                TestListener.getTest().pass("Login successful for user: " + username);

                // Handle cookies
                handleCookies(test);

                // Handle session storage
                handleSessionStorage(test);

                // Optional: logout for next iteration if app supports it
                // driver.findElement(By.id("logout-button")).click();
                // test.info("Logged out user: " + username);

            } catch (Exception e) {
                test.fail("Login failed for user: " + username + " - " + e.getMessage());
                Assert.fail("Login failed for user: " + username, e);
            }
        }
    }

    private void handleCookies(ExtentTest test) {
        try {
            // Add a cookie
            Cookie userCookie = new Cookie("username", "vikas");
            driver.manage().addCookie(userCookie);
            TestListener.getTest().info("Added cookie: " + userCookie.getName() + " = " + userCookie.getValue());

            // Retrieve all cookies
            Set<Cookie> allCookies = driver.manage().getCookies();
            TestListener.getTest().info("Retrieved cookies:");
            for (Cookie cookie : allCookies) {
                test.info(cookie.getName() + " = " + cookie.getValue());
            }

            // Delete a specific cookie
            driver.manage().deleteCookieNamed("username");
            TestListener.getTest().info("Deleted cookie: username");

            // Delete all cookies
            driver.manage().deleteAllCookies();
            TestListener.getTest().info("Deleted all cookies");

        } catch (Exception e) {
        	TestListener.getTest().fail("Error handling cookies: " + e.getMessage());
        }
    }

    private void handleSessionStorage(ExtentTest test) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;

            // Add session storage item
            js.executeScript("sessionStorage.setItem('token', '12345');");
            TestListener.getTest().info("Added session storage item: token = 12345");

            // Retrieve session storage item
            String token = (String) js.executeScript("return sessionStorage.getItem('token');");
            TestListener.getTest().info("Retrieved session storage item: token = " + token);

            // Remove session storage item
            js.executeScript("sessionStorage.removeItem('token');");
            TestListener.getTest().info("Removed session storage item: token");

            // Clear all session storage
            js.executeScript("sessionStorage.clear();");
            TestListener.getTest().info("Cleared all session storage");

        } catch (Exception e) {
            test.fail("Error handling session storage: " + e.getMessage());
        }
    }
}
