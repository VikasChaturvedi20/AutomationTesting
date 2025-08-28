package tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;

public class SwitchTab extends BaseTest
{
	@Test
	public void Switch_to_Tab() {
		try {
			driver.get("https://rahulshettyacademy.com/AutomationPractice/");
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			
			String ParentTab= driver.getWindowHandle();

			WebElement ClickOnNewTab_btn = driver.findElement(By.id("opentab"));
			ClickOnNewTab_btn.click();

			for (String handle : driver.getWindowHandles()) {
		        if (!handle.equals(ParentTab)) {
		            driver.switchTo().window(handle);
		            break;
		        }
		    }
             Thread.sleep(7000);
		    // Now you are in new tab
		    System.out.println("New Tab Title: " + driver.getTitle());

		    // Do assertions in the new tab
		    Assert.assertTrue(driver.getTitle().contains("QAClick Academy - A Testing Academy to Learn, Earn and Shine"), "QAClick Academy - A Testing Academy to Learn, Earn and Shine");


		} 
		catch (Exception e) 
		{
			// TODO: handle exception
			System.out.println("Exception Occured during New Window Switch:"+e.getMessage());
		}

	}

}
