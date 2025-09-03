package tests;

import base.BaseTest;
import listeners.TestListener;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.ReadDataFormCSV;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.Map;

public class LoginTestCase extends BaseTest {

    @Test
    @Parameters("csvFilePath")
    public void validLogin(String csvFilePath) {
        WebDriver driver = getDriver();
        ExtentTest test = TestListener.getTest();

        List<Map<String, String>> data = ReadDataFormCSV.read(csvFilePath);

        for (Map<String, String> row : data) {
            String url = row.get("url");
            String username = row.get("username");
            String password = row.get("password");

            try {
                if (url.contains("saucedemo.com")) {
                    driver.get(url);
                    test.info("Navigated to URL: " + url);

                    LoginPage loginPage = new LoginPage(driver);

                    // Perform login
                    loginPage.login(username, password);
                    test.info("Login attempted with username: " + username);

                    // Validate login
                    Assert.assertTrue(loginPage.isLoginSuccessful(), "Login failed!");
                    test.pass("Login successful for user: " + username);

                    // Logout for next iteration
                    loginPage.logout();
                    test.info("Logged out user: " + username);
                }
            } catch (Exception e) {
                test.fail("Login failed for user: " + username + " - " + e.getMessage());
                Assert.fail("Login failed for user: " + username, e);
            }
        }
    }
}
