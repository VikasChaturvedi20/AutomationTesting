/*package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {
    @Test
    public void validLogin() {
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
        WebElement successMsg = driver.findElement(By.xpath("//span[text()='Products']"));
        Assert.assertTrue(successMsg.getText().contains("Products"));
    }
}
*/

package tests;

import base.BaseTest;
import listeners.TestListener;
import org.openqa.selenium.By;
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

public class LoginTest extends BaseTest {

    @Test
    @Parameters("csvFilePath")
    public void validLogin(String csvFilePath) {
        WebDriver driver = getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        List<Map<String, String>> data = ReadDataFormCSV.read(csvFilePath);

        for (Map<String, String> row : data) {
            String url = row.get("url");
            String username = row.get("username");
            String password = row.get("password");

            try {
                // Navigate to URL
                driver.get(url);
                TestListener.getTest().info("Navigated to URL: " + url);

                // Enter username
                WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
                usernameInput.clear();
                usernameInput.sendKeys(username);
                TestListener.getTest().info("Entered username: " + username);

                // Enter password
                WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
                passwordInput.clear();
                passwordInput.sendKeys(password);
                TestListener.getTest().info("Entered password: " + password);

                // Click login
                WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("login-button")));
                loginBtn.click();
                TestListener.getTest().info("Clicked on Login button");

                // Validate login
                WebElement successMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Products']")));
                Assert.assertTrue(successMsg.getText().contains("Products"));
                TestListener.getTest().pass("Login successful for user: " + username);

                // Optional: logout if app supports it
                // WebElement logoutBtn = driver.findElement(By.id("logout-button"));
                // logoutBtn.click();
                // TestListener.getTest().info("Logged out user: " + username);

            } catch (Exception e) {
                TestListener.getTest().fail("Login failed for user: " + username + " - " + e.getMessage());
                Assert.fail("Login failed for user: " + username, e);
            }
        }
    }
}
