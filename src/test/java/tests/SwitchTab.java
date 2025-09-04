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

public class SwitchTab extends BaseTest {

    @Test
    public void switchToNewTab() {
        WebDriver driver = getDriver(); // ✅ ThreadLocal driver
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        try {
            driver.get("https://rahulshettyacademy.com/AutomationPractice/");
            TestListener.getTest().info("Navigated to Automation Practice URL");

            String parentTab = driver.getWindowHandle();

            WebElement newTabButton = wait.until(
                    ExpectedConditions.elementToBeClickable(By.id("opentab")));
            newTabButton.click();
            TestListener.getTest().info("Clicked on 'Open Tab' button");

            // Switch to new tab
            for (String handle : driver.getWindowHandles()) {
                if (!handle.equals(parentTab)) {
                    driver.switchTo().window(handle);
                    break;
                }
            }

            // Optional wait to ensure page loads
            wait.until(ExpectedConditions.titleContains("QAClick Academy"));
            TestListener.getTest().info("Switched to new tab: " + driver.getTitle());

            // Validation
            Assert.assertTrue(driver.getTitle().contains(
                    "QAClick Academy - A Testing Academy to Learn, Earn and Shine"),
                    "Title mismatch in new tab");
            TestListener.getTest().pass("New tab opened and validated successfully");

            // Switch back to parent tab if needed
            driver.switchTo().window(parentTab);
            TestListener.getTest().info("Switched back to parent tab");

        } catch (Exception e) {
            TestListener.getTest().fail("Exception occurred while switching tabs: " + e.getMessage());
            Assert.fail("Switch tab test failed", e);
        }
    }
}
