package tests;

import base.BaseTest;
import listeners.TestListener;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.FileUploadPage;
import locators.AllLocators;
import utils.ReadDataFormCSV;
import utils.ScreenshotUtils;
import utils.RetryAnalyzer;
import utils.LoggerUtil;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.List;
import java.util.Map;

public class FileUploadTest1 extends BaseTest {

    private static final Logger log = LoggerUtil.getLogger(FileUploadTest1.class);

    @Test(groups = {"smoke"}, retryAnalyzer = RetryAnalyzer.class)
    @Parameters("csvFilePath")
    public void testFileUploadSmoke(String csvFilePath) {
        WebDriver driver = getDriver();
        log.info("Starting smoke test for file upload using data from: {}", csvFilePath);

        AllLocators locators = initPage(AllLocators.class);
        FileUploadPage uploadPage = new FileUploadPage(driver, locators);

        List<Map<String, String>> data = ReadDataFormCSV.read(csvFilePath);

        for (Map<String, String> row : data) {
            String url = row.get("url");
            if (url.contains("upload")) {
                driver.get(url);
                log.info("Navigated to URL: {}", url);
                TestListener.getTest().info("Navigated to: " + url);

                File file = new File("src/main/resources/sample.txt");
                uploadPage.uploadFile(file.getAbsolutePath());
                TestListener.getTest().info("Selected file: " + file.getName());
                log.info("Selected file: {}", file.getName());

                uploadPage.clickUploadButton();
                TestListener.getTest().info("Clicked upload button");
                log.info("Clicked upload button.");

                String uploadedFile = uploadPage.getUploadedFileName();
                log.info("Verifying uploaded file name: expected [{}], actual [{}]", file.getName(), uploadedFile);
                Assert.assertEquals(uploadedFile, file.getName(), "Uploaded file name mismatch!");
                TestListener.getTest().pass("File uploaded successfully: " + uploadedFile);

                String viewportPath = ScreenshotUtils.captureViewportScreenshot(driver, "FileUpload_Viewport");
                TestListener.getTest().info("Viewport Screenshot").addScreenCaptureFromPath(viewportPath);
                log.info("Captured viewport screenshot at path: {}", viewportPath);
            }
        }
    }

    @Test(groups = {"regression"}, dependsOnMethods = "testFileUploadSmoke")
    @Parameters("csvFilePath")
    public void verifyUploadDependsOnSmoke(String csvFilePath) {
        WebDriver driver = getDriver();
        log.info("Starting regression test for file upload (depends on smoke). Data file: {}", csvFilePath);

        AllLocators locators = initPage(AllLocators.class);
        FileUploadPage uploadPage = new FileUploadPage(driver, locators);

        List<Map<String, String>> data = ReadDataFormCSV.read(csvFilePath);

        for (Map<String, String> row : data) {
            String url = row.get("url");
            if (url.contains("upload")) {
                driver.get(url);
                log.info("Navigated to URL for dependency check: {}", url);
                TestListener.getTest().info("Navigated for dependency check: " + url);

                String heading = uploadPage.getHeadingText();
                log.info("Validating page heading [{}] contains 'File Uploader'", heading);
                Assert.assertTrue(heading.contains("File Uploader"),
                        "Heading check failed for: " + url + " | Found heading: " + heading);

                TestListener.getTest().pass("Dependency test executed successfully on: " + url);
                log.info("Dependency validation successful on: {}", url);
            }
        }
    }
}
