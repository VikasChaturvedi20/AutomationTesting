package tests;

import base.BaseTest;
import listeners.TestListener;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.CaptchaHandlingPage;
import locators.AllLocators;
import utils.CaptchaSolverUtil;
import utils.LoggerUtil;
import utils.ReadDataFormCSV;
import utils.ScreenshotUtils;
import utils.RetryAnalyzer;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

public class CaptchaHandlingTest extends BaseTest {

    private static final Logger log = LoggerUtil.getLogger(CaptchaHandlingTest.class);

    @Test(groups = {"captcha"}, retryAnalyzer = RetryAnalyzer.class)
    @Parameters("csvFilePath")
    public void testCaptchaSolving(String csvFilePath) {
        WebDriver driver = getDriver();
        log.info("Starting CAPTCHA solving test using data file: {}", csvFilePath);

        AllLocators locators = initPage(AllLocators.class);
        CaptchaHandlingPage captchaPage = new CaptchaHandlingPage(driver, locators);

        List<Map<String, String>> data = ReadDataFormCSV.read(csvFilePath);

        for (Map<String, String> row : data) {
            String url = row.get("url");
            if (url.contains("captcha")) { // 🔹 Ensure only captcha-related URLs are used
                try {
                    driver.get(url);
                    log.info("Navigated to CAPTCHA URL: {}", url);
                    TestListener.getTest().info("Navigated to CAPTCHA page: " + url);

                    // Step 1: Get site key
                    String siteKey = captchaPage.getSiteKey();
                    log.info("Extracted site key: {}", siteKey);

                    // Step 2: Solve via 2Captcha API
                    String token = CaptchaSolverUtil.solveCaptcha(siteKey, url);
                    Assert.assertNotNull(token, "Failed to get CAPTCHA token from solver!");
                    log.info("Received CAPTCHA token successfully.");

                    // Step 3: Inject token
                    captchaPage.injectCaptchaToken(token);
                    TestListener.getTest().info("Injected CAPTCHA token");

                    // Step 4: Submit form
                    captchaPage.clickSubmit();
                    TestListener.getTest().info("Clicked Submit button");

                    // Step 5: Verify result
                    String bodyText = captchaPage.getBodyText();
                    Assert.assertTrue(bodyText.contains("verified"), "CAPTCHA not solved successfully!");
                    TestListener.getTest().pass("CAPTCHA solved successfully on: " + url);
                    log.info("CAPTCHA solved successfully for URL: {}", url);

                    // Step 6: Screenshot for reporting
                    String screenshotPath = ScreenshotUtils.captureViewportScreenshot(driver, "CaptchaSolved");
                    TestListener.getTest().info("Captcha Solved Screenshot").addScreenCaptureFromPath(screenshotPath);
                    log.info("Captured screenshot at path: {}", screenshotPath);

                } catch (Exception e) {
                    log.error("Error occurred while solving CAPTCHA at URL: {}", url, e);
                    TestListener.getTest().fail("Error while solving CAPTCHA: " + e.getMessage());
                    Assert.fail("Test failed due to exception: " + e.getMessage(), e);
                }
            }
        }
    }
}
