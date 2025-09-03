package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class FileUploadPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By fileUploadInput = By.cssSelector("#file-upload");
    private By uploadButton = By.cssSelector("#file-submit");
    private By uploadedFilesText = By.cssSelector("#uploaded-files");

    // Constructor
    public FileUploadPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Actions
    public void uploadFile(String filePath) {
        WebElement uploadInput = wait.until(ExpectedConditions.presenceOfElementLocated(fileUploadInput));
        uploadInput.sendKeys(filePath);
    }

    public void clickUploadButton() {
        WebElement uploadBtn = wait.until(ExpectedConditions.elementToBeClickable(uploadButton));
        uploadBtn.click();
    }

    public String getUploadedFileName() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(uploadedFilesText)).getText();
    }
}
