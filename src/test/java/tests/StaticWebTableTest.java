package tests;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import base.BaseTest;
import listeners.TestListener;
import reports.ExtentManager;
import utils.ReadDataFormCSV;

import java.time.Duration;

public class StaticWebTableTest extends BaseTest {

 
	
	@Test
	public void handleStaticWebTable() {
	   
	     ExtentTest test =TestListener.getTest();

	    List<String[]> data = ReadDataFormCSV.read("src/main/resources/testdata.csv");

	    for (String[] row : data) {
	        String url = row[0];  

	        try {
	            if (url.contains("testautomationpractice")) {
	                driver.get(url);
	               TestListener.getTest().info("Navigated to URL: " + url);

	                scrollByPixels(0, 1500);
	                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	                Thread.sleep(5000);  

	                WebElement table = wait.until(ExpectedConditions
	                        .visibilityOfElementLocated(By.cssSelector("table[name='BookTable']")));

	               TestListener.getTest().info("Static Web Table is visible");

	                List<WebElement> headers = table.findElements(By.cssSelector("tr:nth-of-type(1) th"));
	               TestListener.getTest().info("Number of headers found: " + headers.size());

	                for (WebElement header : headers) {
	                   TestListener.getTest().info("Header: " + header.getText());
	                }

	                List<WebElement> rows = table.findElements(By.cssSelector("tr:not(:first-child)"));
	               TestListener.getTest().info("Number of data rows found: " + rows.size());

	                boolean validationPassed = false;

	                for (int i = 0; i < rows.size(); i++) {
	                    List<WebElement> cells = rows.get(i).findElements(By.cssSelector("td"));
	                    String rowData = "";
	                    for (WebElement cell : cells) {
	                        rowData += cell.getText() + " | ";
	                    }
	                   TestListener.getTest().info("Row " + (i + 1) + ": " + rowData);

	                    if (cells.get(0).getText().equalsIgnoreCase("Learn JS") &&
	                            cells.get(2).getText().equalsIgnoreCase("Javascript")) {
	                        validationPassed = true;
	                       TestListener.getTest().pass("Row validation passed for 'Learn JS' with subject 'Javascript'");
	                        break;
	                    }
	                }

	                Assert.assertTrue(validationPassed,
	                        "Validation failed: 'Learn JS' with subject 'Javascript' not found!");

	            }
	        } catch (Exception e) {
	           TestListener.getTest().fail("Failed to validate static web table on: " + url + " - " + e.getMessage());
	            Assert.fail("User failed to handle static web table on: " + url, e);
	        }
	    }
	}

    
    public void scrollByPixels(int x, int y) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(" + x + "," + y + ")");
    }
}
