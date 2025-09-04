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

public class Radiobuttom extends BaseTest {

    @Test(groups = {"smoke"})
    public void selectRadioButton() {
        WebDriver driver = getDriver(); // ✅ ThreadLocal driver
        driver.get("https://demoqa.com/elements");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Click on Radio Button menu
        WebElement radioMenu = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[text()='Radio Button']")));
        radioMenu.click();

        // Click on "Yes" radio button
        WebElement yesRadio = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//label[@for='yesRadio']")));
        yesRadio.click();

        // Validation
        WebElement successMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//p[contains(text(),'You have selected')]")));
        Assert.assertTrue(successMsg.getText().contains("You have selected"), 
                "Validation failed! Expected success message not found.");
    }
}
