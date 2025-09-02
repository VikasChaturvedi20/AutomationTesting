package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        // Initialize explicit wait with 10 seconds timeout
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Locators
    private By usernameField = By.id("user-name");
    private By passwordField = By.id("password");
    private By loginButton = By.id("login-button");
    private By successMessage = By.xpath("//span[text()='Products']");
    private By menuButton = By.id("react-burger-menu-btn");
    private By logoutLink = By.id("logout_sidebar_link");

    // Actions
    public void enterUsername(String username) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
        element.clear();
        element.sendKeys(username);
    }

    public void enterPassword(String password) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        element.clear();
        element.sendKeys(password);
    }

    public void clickLogin() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        button.click();
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
        // Wait until login success message is visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
    }

    public boolean isLoginSuccessful() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void logout() {
        try {
            WebElement menuBtn = wait.until(ExpectedConditions.elementToBeClickable(menuButton));
            menuBtn.click();

            WebElement logoutBtn = wait.until(ExpectedConditions.elementToBeClickable(logoutLink));
            logoutBtn.click();
        } catch (Exception e) {
            System.out.println("Logout failed: " + e.getMessage());
        }
    }
}
