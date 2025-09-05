package tests;

import base.BaseTest;
import listeners.TestListener;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.FakeFormPage;
import locators.AllLocators;
import utils.FakeDataUtil;
import utils.LoggerUtil;
import utils.ReadDataFormCSV;
import utils.RetryAnalyzer;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

public class FakeDataTest extends BaseTest {

    private static final Logger log = LoggerUtil.getLogger(FakeDataTest.class);

    @Test(groups = {"smoke"}, retryAnalyzer = RetryAnalyzer.class)
    @Parameters("csvFilePath")
    public void testFormWithFakeData(String csvFilePath) {
        WebDriver driver = getDriver();
        log.info("Starting smoke test for fake data forms using data from: {}", csvFilePath);

        AllLocators locators = initPage(AllLocators.class);
        FakeFormPage formPage = new FakeFormPage(driver, locators);

        // Read test data from CSV
        List<Map<String, String>> data = ReadDataFormCSV.read(csvFilePath);

        for (Map<String, String> row : data) {
            String url = row.get("url");

            // Run only for "form" related test URLs
            if (url.contains("form")) {
                driver.get(url);
                log.info("Navigated to test form URL: {}", url);
                TestListener.getTest().info("Opened form page: " + url);

                try {
                    // Generate fake test data
                    String fullName = FakeDataUtil.getFullName();
                    String email = FakeDataUtil.getEmail();
                    String phone = FakeDataUtil.getPhoneNumber();
                    String address = FakeDataUtil.getStreetAddress();

                    log.info("Generated fake data -> Name: {}, Email: {}, Phone: {}, Address: {}",
                            fullName, email, phone, address);
                    TestListener.getTest().info("Generated fake data and filling form");

                    // Fill form via Page Object
                    formPage.enterName(fullName);
                    formPage.enterEmail(email);
                    formPage.enterPhone(phone);
                    formPage.enterAddress(address);
                    formPage.submitForm();

                    // Verify confirmation
                    String confirmation = formPage.getConfirmationMessage();
                    log.info("Captured confirmation: {}", confirmation);

                    Assert.assertTrue(confirmation.contains("Thank you"),
                            "Form submission failed! Found: " + confirmation);

                    TestListener.getTest().pass("Form submitted successfully with fake data");
                    log.info("Form submitted successfully for URL: {}", url);

                } catch (Exception e) {
                    log.error("Form submission failed for URL: {}", url, e);
                    TestListener.getTest().fail("Error in fake data form test for URL " + url + ": " + e.getMessage());
                    Assert.fail("Exception during fake data form submission on " + url, e);
                }
            }
        }
    }
}
