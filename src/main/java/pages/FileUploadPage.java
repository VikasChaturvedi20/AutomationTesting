package pages;

import locators.AllLocators;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class FileUploadPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private AllLocators locators;

    // Constructor
    public FileUploadPage(WebDriver driver, AllLocators locators) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.locators = locators;
    }

    // Actions
    public void uploadFile(String filePath) {
        wait.until(ExpectedConditions.visibilityOf(locators.fileUploadInput)).sendKeys(filePath);
    }

    public void clickUploadButton() {
        wait.until(ExpectedConditions.elementToBeClickable(locators.uploadButton)).click();
    }

    public String getUploadedFileName() {
        return wait.until(ExpectedConditions.visibilityOf(locators.uploadedFilesText)).getText();
    }
    
    public String getHeadingText() {
        return wait.until(ExpectedConditions.visibilityOf(locators.fileUploadHeading)).getText();
    }

}
