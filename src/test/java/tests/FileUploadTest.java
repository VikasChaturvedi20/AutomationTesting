/*package tests;

import base.BaseTest;
import base.BaseTest1;
import listeners.TestListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ReadDataFormCSV;

import java.io.File;
import java.time.Duration;
import java.util.List;

public class FileUploadTest extends BaseTest {

    @Test
    public void testFileUpload() {
        List<String[]> data = ReadDataFormCSV.read("src/main/resources/testdata.csv");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        for (String[] row : data) {
            String url = row[0];

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
                }
            } catch (Exception e) {
                TestListener.getTest().fail("File upload test failed: " + e.getMessage());
            }
        }
    }
}   */

package tests;

import base.BaseTest;
import base.BaseTest1;
import listeners.TestListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ReadDataFormCSV;
import utils.ScreenshotUtils;

import java.io.File;
import java.time.Duration;
import java.util.List;

public class FileUploadTest extends BaseTest {

    @Test
    public void testFileUpload() {
       // WebDriver driver = getDriver();  // Use ThreadLocal driver

        List<String[]> data = ReadDataFormCSV.read("src/main/resources/testdata.csv");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        for (String[] row : data) {
            String url = row[0];

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
                    
                 // ----------------------------
                    // Capture Screenshots
                    // ----------------------------
                    String viewportPath = ScreenshotUtils.captureViewportScreenshot(driver, "FileUpload_Viewport");
                    TestListener.getTest().info("Viewport Screenshot").addScreenCaptureFromPath(viewportPath);

                    String fullPagePath = ScreenshotUtils.captureFullPageScreenshot(driver, "FileUpload_FullPage");
                    TestListener.getTest().info("Full Page Screenshot").addScreenCaptureFromPath(fullPagePath);
                }
            } catch (Exception e) {
                TestListener.getTest().fail("File upload test failed: " + e.getMessage());
                String viewportPath = ScreenshotUtils.captureViewportScreenshot(driver, "FileUpload_Failure_Viewport");
                TestListener.getTest().info("Viewport Screenshot on Failure").addScreenCaptureFromPath(viewportPath);

                String fullPagePath = ScreenshotUtils.captureFullPageScreenshot(driver, "FileUpload_Failure_FullPage");
                TestListener.getTest().info("Full Page Screenshot on Failure").addScreenCaptureFromPath(fullPagePath);
            }
        }
    }
}


