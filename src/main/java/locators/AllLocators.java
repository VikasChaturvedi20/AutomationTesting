package locators;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AllLocators {

    // ================= File Upload Page Locators =================
    @FindBy(css = "#file-upload")
    public WebElement fileUploadInput;

    @FindBy(css = "#file-submit")
    public WebElement uploadButton;

    @FindBy(css = "#uploaded-files")
    public WebElement uploadedFilesText;
    
    @FindBy(tagName = "h3")
    public WebElement fileUploadHeading;
    
    @FindBy(id = "flash")   // Adjust locator as per your AUT
    public WebElement loginSuccessMsg;

    // ================= Login Page Locators (future example) =================
    @FindBy(id = "username")
    public WebElement usernameInput;

    @FindBy(id = "password")
    public WebElement passwordInput;

    @FindBy(css = "button[type='submit']")
    public WebElement loginButton;

    // ================= Dashboard Page Locators (future example) =================
    @FindBy(xpath = "//h1[contains(text(),'Dashboard')]")
    public WebElement dashboardTitle;
    
   //============================Handling Captcha=============================== 
    @FindBy(css = "div.g-recaptcha")
    public WebElement recaptchaBox;

    @FindBy(css = "button[type='submit']")
    public WebElement submitButton;
    
   //====================== Fake form locators ================================
    @FindBy(id = "name")
    public WebElement formNameInput;

    @FindBy(id = "email")
    public WebElement formEmailInput;

    @FindBy(id = "phone")
    public WebElement formPhoneInput;

    @FindBy(id = "address")
    public WebElement formAddressInput;

    @FindBy(id = "submit")
    public WebElement formSubmitButton;

    @FindBy(id = "confirmation")
    public WebElement formConfirmationText;
}
