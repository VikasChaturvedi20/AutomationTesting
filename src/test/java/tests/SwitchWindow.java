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
import listeners.TestListener;

public class SwitchWindow extends BaseTest {

    @Test
    public void switchToNewWindow() {
        WebDriver driver = getDriver(); // ✅ ThreadLocal driver
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        try {
            driver.get("https://rahulshettyacademy.com/AutomationPractice/");
            TestListener.getTest().info("Navigated to Automation Practice URL");

            WebElement newWindowBtn = wait.until(
                    ExpectedConditions.elementToBeClickable(By.id("openwindow")));
            newWindowBtn.click();
            TestListener.getTest().info("Clicked on 'Open Window' button");

            String parentWindow = driver.getWindowHandle();

            // Switch to new window
            for (String handle : driver.getWindowHandles()) {
                if (!handle.equals(parentWindow)) {
                    driver.switchTo().window(handle);
                    break;
                }
            }

            // Wait until an element in the new window is visible
            WebElement homeLink = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("(//li/a[text()='Home'])[1]")));
            TestListener.getTest().info("Switched to new window: " + driver.getTitle());

            // Validation
            Assert.assertTrue(homeLink.isDisplayed(), "Home link is not visible in the new window");
            TestListener.getTest().pass("New window opened and validated successfully");

            // Switch back to parent window if needed
            driver.switchTo().window(parentWindow);
            TestListener.getTest().info("Switched back to parent window");

        } catch (Exception e) {
            TestListener.getTest().fail("Exception occurred while switching windows: " + e.getMessage());
            Assert.fail("Switch window test failed", e);
        }
    }
}
