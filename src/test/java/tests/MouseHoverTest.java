package tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import listeners.TestListener;
import reports.ExtentManager;
import utils.ReadDataFormCSV;

public class MouseHoverTest extends BaseTest {

    @Test
    public void testMouseHoverMenu() {
        List<String[]> data = ReadDataFormCSV.read("src/main/resources/testdata.csv");

        for (String[] row : data) {
            String url = row[0];

            try {
                if (url.contains("menu")) {
                    driver.get(url);
                   TestListener.getTest().info("Navigated to URL: " + url);

                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                    Actions actions = new Actions(driver);

                    // Hover on Main Item 2
                    WebElement mainItem2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Main Item 2']")));
                    actions.moveToElement(mainItem2).perform();
                   TestListener.getTest().info("Hovered on Main Item 2");

                    // Hover on SUB SUB LIST »
                    WebElement subSubList = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='SUB SUB LIST »']")));
                    actions.moveToElement(subSubList).perform();
                   TestListener.getTest().info("Hovered on SUB SUB LIST »");

                    // Hover on Sub Sub Item 1
                    WebElement subSubItem1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Sub Sub Item 1']")));
                    actions.moveToElement(subSubItem1).perform();
                   TestListener.getTest().info("Hovered on Sub Sub Item 1");

                    // Validation
                    Assert.assertTrue(subSubItem1.isDisplayed(), "Sub Sub Item 1 is not visible after hover!");
                }
            } catch (Exception e) {
               TestListener.getTest().fail("Mouse hover failed for URL: " + url + " due to: " + e.getMessage());
                Assert.fail("Exception occurred: " + e.getMessage());
            }
        }
    }
}
