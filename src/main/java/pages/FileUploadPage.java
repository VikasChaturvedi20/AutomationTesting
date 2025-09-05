package pages;

import locators.AllLocators;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.LoggerUtil;
import org.apache.logging.log4j.Logger;

import java.time.Duration;

public class FileUploadPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private AllLocators locators;
    private static final Logger log = LoggerUtil.getLogger(FileUploadPage.class);

    // Constructor
    public FileUploadPage(WebDriver driver, AllLocators locators) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.locators = locators;
        log.info("FileUploadPage initialized with driver and locators.");
    }

    // Actions
    public void uploadFile(String filePath) {
        wait.until(ExpectedConditions.visibilityOf(locators.fileUploadInput)).sendKeys(filePath);
        log.info("Uploaded file path: {}", filePath);
    }

    public void clickUploadButton() {
        wait.until(ExpectedConditions.elementToBeClickable(locators.uploadButton)).click();
        log.info("Clicked on upload button.");
    }

    public String getUploadedFileName() {
        String uploadedFileName = wait.until(ExpectedConditions.visibilityOf(locators.uploadedFilesText)).getText();
        log.info("Uploaded file displayed on page: {}", uploadedFileName);
        return uploadedFileName;
    }
    
    public String getHeadingText() {
        String heading = wait.until(ExpectedConditions.visibilityOf(locators.fileUploadHeading)).getText();
        log.info("Heading text captured: {}", heading);
        return heading;
    }
}
