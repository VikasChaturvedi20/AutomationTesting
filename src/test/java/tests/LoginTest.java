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
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ReadDataFormCSV;
import utils.ExtentManager;
import com.aventstack.extentreports.ExtentTest;

import java.util.List;

public class LoginTest extends BaseTest {

    @Test
    public void validLogin() {
        // Get ExtentTest instance
        ExtentTest test = ExtentManager.getExtentTest();

        // Read data from CSV
        List<String[]> data = ReadDataFormCSV.read("src/main/resources/testdata.csv");

        for (String[] row : data) {
            String url = row[0];
            String username = row[1];
            String password = row[2];

            try {
                // Navigate to URL
                driver.get(url);
                test.info("Navigated to URL: " + url);

                // Enter username
                driver.findElement(By.id("user-name")).clear();
                driver.findElement(By.id("user-name")).sendKeys(username);
                test.info("Entered username: " + username);
                 Thread.sleep(5000);
                // Enter password
                driver.findElement(By.id("password")).clear();
                driver.findElement(By.id("password")).sendKeys(password);
                test.info("Entered password: " + password);
                Thread.sleep(5000);
                // Click login
                driver.findElement(By.id("login-button")).click();
                test.info("Clicked on Login button");
                Thread.sleep(5000);
                // Validate login
                WebElement successMsg = driver.findElement(By.xpath("//span[text()='Products']"));
                Assert.assertTrue(successMsg.getText().contains("Products"));
                test.pass("Login successful for user: " + username);

                // Optional: logout for next iteration if app supports it
                // driver.findElement(By.id("logout-button")).click();
                // test.info("Logged out user: " + username);

            } catch (Exception e) {
                test.fail("Login failed for user: " + username + " - " + e.getMessage());
                Assert.fail("Login failed for user: " + username, e);
            }
        }
    }
}
