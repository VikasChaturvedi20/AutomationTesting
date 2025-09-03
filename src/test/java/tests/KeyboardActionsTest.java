package tests;

import base.BaseTest;
import listeners.TestListener;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
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

public class KeyboardActionsTest extends BaseTest {

    @Test
    @Parameters("csvFilePath")
    public void testKeyboardActions(String csvFilePath) throws InterruptedException {
        WebDriver driver = getDriver();
        List<Map<String, String>> data = ReadDataFormCSV.read(csvFilePath);

        for (Map<String, String> row : data) {
            String url = row.get("url");
            String inputText = row.get("input_text");

            try {
                driver.get(url);
                TestListener.getTest().info("Navigated to URL: " + url);

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                Actions actions = new Actions(driver);

                // 1. Keyboard: ENTER / BACKSPACE / TAB
                if (url.contains("key_presses")) {
                    WebElement body = driver.findElement(By.tagName("body"));
                    body.sendKeys(Keys.ENTER);
                    TestListener.getTest().info("Pressed ENTER key");
                    body.sendKeys(Keys.BACK_SPACE);
                    TestListener.getTest().info("Pressed BACKSPACE key");
                    body.sendKeys(Keys.TAB);
                    TestListener.getTest().info("Pressed TAB key");
                }

                // 2. Keyboard: SHIFT typing (uppercase)
                else if (url.contains("text-box") && row.get("action").equalsIgnoreCase("shift")) {
                    WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userName")));
                    actions.keyDown(Keys.SHIFT).sendKeys(input, inputText).keyUp(Keys.SHIFT).perform();
                    TestListener.getTest().info("Typed '" + inputText.toUpperCase() + "' in uppercase using SHIFT key");
                }

                // 3. Keyboard: CTRL+A / CTRL+C / CTRL+V
                else if (url.contains("text-box") && row.get("action").equalsIgnoreCase("copy-paste")) {
                    WebElement input1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userName")));
                    WebElement input2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userEmail")));

                    input1.sendKeys(inputText);
                    TestListener.getTest().info("Entered text in InputBox1");

                    // CTRL+A
                    actions.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).perform();
                    TestListener.getTest().info("Performed CTRL+A (Select All)");

                    // CTRL+C
                    actions.keyDown(Keys.CONTROL).sendKeys("c").keyUp(Keys.CONTROL).perform();
                    TestListener.getTest().info("Performed CTRL+C (Copy)");

                    // Move to second box and paste
                    input2.click();
                    actions.keyDown(Keys.CONTROL).sendKeys("v").keyUp(Keys.CONTROL).perform();
                    TestListener.getTest().info("Performed CTRL+V (Paste)");

                    Assert.assertEquals(input2.getAttribute("value"), inputText.toLowerCase(),
                            "Copied text is matching!");
                }

            } catch (Exception e) {
                TestListener.getTest().fail("Keyboard actions failed for URL: " + url + " due to: " + e.getMessage());
                Assert.fail("Exception occurred: " + e.getMessage(), e);
            }
        }
    }

    private boolean isAlertPresent(WebDriver driver) {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }
}
