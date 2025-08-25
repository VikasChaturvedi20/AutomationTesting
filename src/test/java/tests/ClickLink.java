package tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.Assert;



import base.BaseTest;

public class ClickLink  extends BaseTest
{
	@Test
    public void clickOnHomeLink() {
        // Set up WebDriver (ensure you have the appropriate driver for your browser)
    

        try {
            // Navigate to the DEMOQA Links page
            driver.get("https://demoqa.com/links");

            // Wait for the 'Home' link to be clickable
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement homeLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Home")));

            // Click on the 'Home' link
            homeLink.click();

            // Verify that the new page contains 'DEMOQA' in its title
            Assert.assertTrue(driver.getTitle().contains("DEMOQA"));

        } finally {
            // Close the browser
            driver.quit();
        }
    }
}


