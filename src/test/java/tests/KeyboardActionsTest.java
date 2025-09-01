package tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import listeners.TestListener;
import reports.ExtentManager;
import utils.ReadDataFormCSV;

public class KeyboardActionsTest extends BaseTest {

	@Test
	public void testKeyboardActions() {
		List<String[]> data = ReadDataFormCSV.read("src/main/resources/testdata.csv");

		for (String[] row : data) {
			String url = row[0];
			String input_text = row[3];

			try {
				// driver.get(url);
				// TestListener.getTest().info("Navigated to URL: " + url);

				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
				Actions actions = new Actions(driver);

				// 1.Keyboard: ENTER / BACKSPACE / TAB
				if (url.contains("key_presses")) {
					driver.get(url);
					TestListener.getTest().info("Navigated to URL: " + url);
					WebElement body = driver.findElement(By.tagName("body"));
					body.sendKeys(Keys.ENTER);
					TestListener.getTest().info("Pressed ENTER key");
					body.sendKeys(Keys.BACK_SPACE);
					TestListener.getTest().info("Pressed BACKSPACE key");
					body.sendKeys(Keys.TAB);
					TestListener.getTest().info("Pressed TAB key");
				}

				// 2.Keyboard: SHIFT typing (uppercase)
				else if (url.contains("text-box")) {
					driver.get(url);
					TestListener.getTest().info("Navigated to URL: " + url);
					Thread.sleep(5000);
					WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userName")));
					actions.keyDown(Keys.SHIFT).sendKeys(input, input_text).keyUp(Keys.SHIFT).perform();
					Thread.sleep(5000);

					/*if (isAlertPresent()) {
						Alert alert = driver.switchTo().alert();
						System.out.println("Alert text: " + alert.getText());
						alert.accept(); // or alert.dismiss()
						System.out.println("Alert handled");
					} else {
						System.out.println("No alert present, moving to next steps...");
					} */

					TestListener.getTest().info("Typed 'VIKAS' in uppercase using SHIFT key");
				}

				// 3.Keyboard: CTRL+A / CTRL+C / CTRL+V
				else if (url.contains("text-box")) 
				{
					driver.get(url);
					TestListener.getTest().info("Navigated to URL: " + url);
					Thread.sleep(5000);
					/* if (isAlertPresent()) {
	                        Alert alert = driver.switchTo().alert();
	                        System.out.println("Alert text: " + alert.getText());
	                        alert.accept(); // or alert.dismiss()
	                        System.out.println("Alert handled");
	                    } else {
	                        System.out.println("No alert present, moving to next steps...");
	                    }  */
	                    
					WebElement input1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userName")));
					WebElement input2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userEmail")));

					input1.sendKeys(input_text);
					TestListener.getTest().info("Entered text in InputBox1");

					// CTRL+A
					actions.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).perform();
					TestListener.getTest().info("Performed CTRL+A (Select All)");

					// CTRL+C
					actions.keyDown(Keys.CONTROL).sendKeys("c").keyUp(Keys.CONTROL).perform();
					TestListener.getTest().info("Performed CTRL+C (Copy)");

					// Move to second box
					input2.click();

					// CTRL+V
					actions.keyDown(Keys.CONTROL).sendKeys("v").keyUp(Keys.CONTROL).perform();
					TestListener.getTest().info("Performed CTRL+V (Paste)");

					Assert.assertEquals(input2.getAttribute("value"), "vikas",
							"Copied text is matching!");
				}

			} catch (Exception e) {
				TestListener.getTest().fail("Keyboard actions failed for URL: " + url + " due to: " + e.getMessage());
				Assert.fail("Exception occurred: " + e.getMessage());
			}
		}
	}

	private boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}
}
