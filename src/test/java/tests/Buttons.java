package tests;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import base.BaseTest;

public class Buttons extends BaseTest {

    @Test
    public void clickon_Btn() {
        driver.get("https://demoqa.com/buttons");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        Actions actions = new Actions(driver);

        // Single click
        WebElement clickon_btn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Click Me']")));
        clickon_btn.click();
        WebElement successMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dynamicClickMessage")));
        Assert.assertTrue(successMsg.getText().contains("You have done a dynamic click"));

        // Right Click
        WebElement clickon_btn2 = wait.until(ExpectedConditions.elementToBeClickable(By.id("rightClickBtn")));
        actions.contextClick(clickon_btn2).perform();
        WebElement successMsg1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("rightClickMessage")));
        Assert.assertTrue(successMsg1.getText().contains("You have done a right click"));

        // Double Click
        WebElement clickon_btn3 = wait.until(ExpectedConditions.elementToBeClickable(By.id("doubleClickBtn")));
        actions.doubleClick(clickon_btn3).perform();
        WebElement successMsg2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("doubleClickMessage")));
        Assert.assertTrue(successMsg2.getText().contains("You have done a double click"));
    }
}
