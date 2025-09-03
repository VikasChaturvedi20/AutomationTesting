package tests;

import base.BaseTest;
import base.BaseTest1;
import listeners.TestListener;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.FileUploadPage;
import utils.ReadDataFormCSV;
import utils.ScreenshotUtils;
import utils.RetryAnalyzer;

import java.io.File;
import java.util.List;
import java.util.Map;

public class FileUploadTest1 extends BaseTest {

	@Test(groups = {"smoke"}, retryAnalyzer = RetryAnalyzer.class)
	@Parameters("csvFilePath")
	public void testFileUploadSmoke(String csvFilePath) {
	    WebDriver driver = getDriver();
	    FileUploadPage uploadPage = new FileUploadPage(driver);

	    List<Map<String, String>> data = ReadDataFormCSV.read(csvFilePath);

	    for (Map<String, String> row : data) {
	        String url = row.get("url");   // using header name instead of index
	        if (url.contains("upload")) {
	            driver.get(url);
	            TestListener.getTest().info("Navigated to: " + url);

	            File file = new File("src/main/resources/sample.txt");
	            uploadPage.uploadFile(file.getAbsolutePath());
	            TestListener.getTest().info("Selected file: " + file.getName());

	            uploadPage.clickUploadButton();
	            TestListener.getTest().info("Clicked upload button");

	            String uploadedFile = uploadPage.getUploadedFileName();
	            Assert.assertEquals(uploadedFile, file.getName(), "Uploaded file name mismatch!");
	            TestListener.getTest().pass("File uploaded successfully: " + uploadedFile);

	            // Screenshot
	            String viewportPath = ScreenshotUtils.captureViewportScreenshot(driver, "FileUpload_Viewport");
	            TestListener.getTest().info("Viewport Screenshot").addScreenCaptureFromPath(viewportPath);
	        }
	    }
	}

	@Test(groups = {"regression"}, dependsOnMethods = "testFileUploadSmoke")
	@Parameters("csvFilePath")
	public void verifyUploadDependsOnSmoke(String csvFilePath) {
	    WebDriver driver = getDriver();
	    List<Map<String, String>> data = ReadDataFormCSV.read(csvFilePath);

	    for (Map<String, String> row : data) {
	        String url = row.get("url");   // header name from CSV
	        if (url.contains("upload")) {
	            driver.get(url);
	            TestListener.getTest().info("Navigated for dependency check: " + url);

	            Assert.assertTrue(driver.getTitle().contains("Upload"), "Title check failed for: " + url);
	            TestListener.getTest().pass("Dependency test executed successfully on: " + url);
	        }
	    }
	}
}
