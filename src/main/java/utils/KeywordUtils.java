package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;

public class KeywordUtils {
    private WebDriver driver;
    private WebDriverWait wait;

    public KeywordUtils(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private WebElement waitForVisibility(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    private WebElement waitForClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void openUrl(String url) {
        driver.get(url);
    }

    public void type(WebElement element, String text) {
        waitForVisibility(element).clear();
        element.sendKeys(text);
    }

    public void click(WebElement element) {
        waitForClickable(element).click();
    }

    public void uploadFile(WebElement element, String filePath) {
        File file = new File(filePath);
        element.sendKeys(file.getAbsolutePath());
    }

    public void verifyText(WebElement element, String expected) {
        String actual = waitForVisibility(element).getText().trim();
        if (!actual.contains(expected)) {
            throw new AssertionError(
                    "❌ Text verification failed. Expected: " + expected + ", Actual: " + actual
            );
        }
    }
}
