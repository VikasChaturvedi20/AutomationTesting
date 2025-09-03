package tests;

import base.BaseTest;
import listeners.TestListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.ReadDataFormCSV;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public class MouseHoverTest extends BaseTest {

    @Test
    @Parameters("csvFilePath")
    public void testMouseHoverMenu(String csvFilePath) {
        WebDriver driver = getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Actions actions = new Actions(driver);

        List<Map<String, String>> data = ReadDataFormCSV.read(csvFilePath);

        for (Map<String, String> row : data) {
            String url = row.get("url");

            try {
                if (url.contains("menu")) {
                    driver.get(url);
                    TestListener.getTest().info("Navigated to URL: " + url);

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
                Assert.fail("Exception occurred: " + e.getMessage(), e);
            }
        }
    }
}
