package pages;

import locators.AllLocators;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.LoggerUtil;
import org.apache.logging.log4j.Logger;

import java.time.Duration;

public class FakeFormPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private AllLocators locators;
    private static final Logger log = LoggerUtil.getLogger(FakeFormPage.class);

    public FakeFormPage(WebDriver driver, AllLocators locators) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.locators = locators;
        log.info("FakeFormPage initialized with driver and locators.");
    }

    public void enterName(String name) {
        wait.until(ExpectedConditions.visibilityOf(locators.formNameInput)).sendKeys(name);
        log.info("Entered name: {}", name);
    }

    public void enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOf(locators.formEmailInput)).sendKeys(email);
        log.info("Entered email: {}", email);
    }

    public void enterPhone(String phone) {
        wait.until(ExpectedConditions.visibilityOf(locators.formPhoneInput)).sendKeys(phone);
        log.info("Entered phone: {}", phone);
    }

    public void enterAddress(String address) {
        wait.until(ExpectedConditions.visibilityOf(locators.formAddressInput)).sendKeys(address);
        log.info("Entered address: {}", address);
    }

    public void submitForm() {
        wait.until(ExpectedConditions.elementToBeClickable(locators.formSubmitButton)).click();
        log.info("Clicked on submit button.");
    }

    public String getConfirmationMessage() {
        String msg = wait.until(ExpectedConditions.visibilityOf(locators.formConfirmationText)).getText();
        log.info("Captured confirmation message: {}", msg);
        return msg;
    }
}
