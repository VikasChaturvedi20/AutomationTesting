package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;

public class Radiobuttom extends BaseTest
{
	@Test
     public void SelectRadioButton()
     {
    	 driver.get("https://demoqa.com/elements");
    	 By RadiobtnOptions=By.xpath("//span[text()='Radio Button']");
    	 driver.findElement(RadiobtnOptions).click();
    	 
    	 By ClickOnRadioButton=By.xpath("//label[@for='yesRadio']");
    	 driver.findElement(ClickOnRadioButton).click();
    	 
    	 WebElement successMsg = driver.findElement(By.xpath("//p[text()='You have selected ']"));
    	 Assert.assertTrue(successMsg.getText().contains("You have selected"));
    	 
    	 
     }
}
