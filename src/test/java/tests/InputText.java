package tests;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import base.BaseTest;

public class InputText extends BaseTest {

    @Test(groups = {"smoke"})
    public void enterTextIn_InputBox() {
        WebDriver driver = getDriver(); // ✅ ThreadLocal driver
        driver.get("https://demoqa.com/text-box");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Full Name
        WebElement fullName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userName")));
        fullName.sendKeys("Vikas");

        // Email
        WebElement emailId = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userEmail")));
        emailId.sendKeys("text@gmail.com");

        //Current Address
        WebElement currentAddress = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("currentAddress")));
        currentAddress.sendKeys("xyz complex");

        // Permanent Address
        WebElement permanentAddress = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("permanentAddress")));
        permanentAddress.sendKeys("Xyz Complex");

        // Submit Button
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("submit")));
        submitButton.click();

        // Validation
        WebElement successMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
        Assert.assertTrue(successMsg.getText().contains("Vikas"), 
                "Validation failed! Expected name not found in output.");
    }
}
