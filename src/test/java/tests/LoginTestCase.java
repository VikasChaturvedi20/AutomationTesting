package tests;

import base.BaseTest;
import listeners.TestListener;

import org.openqa.selenium.Alert;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.ReadDataFormCSV;
import com.aventstack.extentreports.ExtentTest;

import java.util.List;

public class LoginTestCase extends BaseTest {

	@Test
	public void validLogin() {
		ExtentTest test = TestListener.getTest();

		// Read data from CSV
		List<String[]> data = ReadDataFormCSV.read("src/main/resources/testdata.csv");

		for (String[] row : data) {
			String url = row[0];
			String username = row[1];
			String password = row[2];

			try {
				if (url.contains("saucedemo.com")) {
					driver.get(url);
					test.info("Navigated to URL: " + url);

					LoginPage loginPage = new LoginPage(driver);

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
