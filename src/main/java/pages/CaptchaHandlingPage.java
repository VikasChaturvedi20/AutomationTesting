package pages;

import locators.AllLocators;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CaptchaHandlingPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private AllLocators locators;

    // Constructor
    public CaptchaHandlingPage(WebDriver driver, AllLocators locators) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.locators = locators;
    }

    // ✅ Get sitekey for reCAPTCHA
    public String getSiteKey() {
        return wait.until(ExpectedConditions.visibilityOf(locators.recaptchaBox))
                   .getAttribute("data-sitekey");
    }

    // ✅ Inject token into the hidden field
    public void injectCaptchaToken(String token) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementById('g-recaptcha-response').innerHTML = arguments[0];", token);
    }

    // ✅ Click Submit
    public void clickSubmit() {
        wait.until(ExpectedConditions.elementToBeClickable(locators.submitButton)).click();
    }

    // ✅ Get heading or body text
    public String getBodyText() {
        return driver.findElement(org.openqa.selenium.By.tagName("body")).getText();
    }
}
