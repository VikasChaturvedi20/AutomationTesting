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

public class ClickLink extends BaseTest {

    @Test(groups = {"smoke"})
    public void clickOnHomeLink() {
        WebDriver driver = getDriver(); // ✅ Use ThreadLocal driver from BaseTest

        try {
            // Navigate to the DEMOQA Links page
            driver.get("https://demoqa.com/links");
            TestListener.getTest().info("Navigated to DEMOQA Links page");

            // Wait for the 'Home' link to be clickable
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement homeLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Home")));
            TestListener.getTest().info("Home link is clickable");

            // Click on the 'Home' link
            homeLink.click();
            TestListener.getTest().info("Clicked on Home link");

            // Verify that the new page contains 'DEMOQA' in its title
            Assert.assertTrue(driver.getTitle().contains("DEMOQA"),
                    "Page title should contain 'DEMOQA' after clicking Home link");
            TestListener.getTest().pass("Verified that page title contains DEMOQA");

        } catch (Exception e) {
            TestListener.getTest().fail("Test failed due to exception: " + e.getMessage());
            Assert.fail("Exception during test execution: " + e.getMessage());
        }
    }
}
