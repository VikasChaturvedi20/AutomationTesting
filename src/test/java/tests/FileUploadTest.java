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
import utils.ScreenshotUtils;

import java.io.File;
import java.time.Duration;
import java.util.List;
import java.util.Map;

public class FileUploadTest extends BaseTest {

    @Test
    @Parameters("csvFilePath")
    public void testFileUpload(String csvFilePath) {
        WebDriver driver = getDriver();  // Thread-safe driver
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        List<Map<String, String>> data = ReadDataFormCSV.read(csvFilePath);

        for (Map<String, String> row : data) {
            String url = row.get("url");  // Using CSV header instead of index

            try {
                if (url.contains("upload")) {
                    driver.get(url);
                    TestListener.getTest().info("Navigated to: " + url);

                    File file = new File("src/main/resources/sample.txt");

                    WebElement uploadInput = wait.until(
                            ExpectedConditions.presenceOfElementLocated(By.cssSelector("#file-upload"))
                    );
                    uploadInput.sendKeys(file.getAbsolutePath());
                    TestListener.getTest().info("Selected file: " + file.getName());

                    WebElement uploadBtn = wait.until(
                            ExpectedConditions.elementToBeClickable(By.cssSelector("#file-submit"))
                    );
                    uploadBtn.click();
                    TestListener.getTest().info("Clicked upload button");

                    String uploadedFileText = wait.until(
                            ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#uploaded-files"))
                    ).getText();

                    Assert.assertEquals(uploadedFileText, file.getName(), "Uploaded file name mismatch!");
                    TestListener.getTest().pass("File uploaded successfully: " + uploadedFileText);

                    // Screenshots - Success
                    String viewportPath = ScreenshotUtils.captureViewportScreenshot(driver, "FileUpload_Viewport");
                    TestListener.getTest().info("Viewport Screenshot").addScreenCaptureFromPath(viewportPath);

                    String fullPagePath = ScreenshotUtils.captureFullPageScreenshot(driver, "FileUpload_FullPage");
                    TestListener.getTest().info("Full Page Screenshot").addScreenCaptureFromPath(fullPagePath);
                }
            } catch (Exception e) {
                TestListener.getTest().fail("File upload test failed for URL: " + url + " | Error: " + e.getMessage());

                // Screenshots - Failure
                String viewportPath = ScreenshotUtils.captureViewportScreenshot(driver, "FileUpload_Failure_Viewport");
                TestListener.getTest().info("Viewport Screenshot on Failure").addScreenCaptureFromPath(viewportPath);

                String fullPagePath = ScreenshotUtils.captureFullPageScreenshot(driver, "FileUpload_Failure_FullPage");
                TestListener.getTest().info("Full Page Screenshot on Failure").addScreenCaptureFromPath(fullPagePath);

                Assert.fail("File upload failed for URL: " + url, e);
            }
        }
    }
}
