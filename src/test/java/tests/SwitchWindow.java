package tests;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.util.TimeUtils;

import base.BaseTest;
import reports.ExtentManager;

public class SwitchWindow extends BaseTest {
	@Test
	public void Switch_to_window() {
		try {
			driver.get("https://rahulshettyacademy.com/AutomationPractice/");
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

			WebElement ClickOnNewWindow_btn = driver.findElement(By.id("openwindow"));
			ClickOnNewWindow_btn.click();

			String currentWindow = driver.getWindowHandle();
			for (String handle : driver.getWindowHandles())
			{
				if (!handle.equals(currentWindow)) 
				{
					driver.switchTo().window(handle);
					break;
				}

			}
			Thread.sleep(5000);
			boolean successMsg = driver.findElement(By.xpath("(//li/a[text()='Home'])[1]")).isDisplayed();
	        Assert.assertTrue(successMsg,"Home");


		} 
		catch (Exception e) 
		{
			// TODO: handle exception
			System.out.println("Exception Occured during New Window Switch:"+e.getMessage());
		}

	}

}
