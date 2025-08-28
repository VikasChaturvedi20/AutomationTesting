package tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;

public class InputText extends BaseTest 
{
	
	@Test
	public void EnterTextIn_InputBox() throws Exception
	{
	   	driver.get("https://demoqa.com/text-box");
	   	
	   	Thread.sleep(10000);
	   	
	   	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	   	WebElement FullName= wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userName")));
	   	FullName.sendKeys("Vikas");
	   	
	   	WebElement Emailid= wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userEmail")));
	   	Emailid.sendKeys("text@gmail.com");
	   	
		WebElement CurrectAddress= wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("currentAddress")));
		CurrectAddress.sendKeys("xyz complex");
	   	
	   	
		WebElement permanentAddress= wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("permanentAddress")));
		permanentAddress.sendKeys("Xyz Complex");
		
		WebElement SubmitButton= wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("submit")));
		SubmitButton.click();
		
		WebElement successMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
        Assert.assertTrue(successMsg.getText().contains("Vikas"));
        Thread.sleep(20000);
	}

}
