package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class GoogleSearchPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By searchButton = By.cssSelector(".DocSearch-Button-Placeholder");
    private By searchInput = By.id("docsearch-input");
    private By suggestionList = By.xpath("//ul[@role='listbox']//li[@role='option']");
    private By heading = By.cssSelector("h1");

    public GoogleSearchPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openSearchBox() {
        driver.findElement(searchButton).click();
        wait.until(ExpectedConditions.elementToBeClickable(searchInput)).click();
    }

    public void enterSearchText(String text) {
        driver.findElement(searchInput).sendKeys(text);
    }

    public boolean selectSuggestion(String expectedText) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(suggestionList));

        for (int retry = 0; retry < 3; retry++) {
            List<WebElement> suggestions = driver.findElements(suggestionList);
            for (WebElement suggestion : suggestions) {
                if (suggestion.getText().trim().equalsIgnoreCase(expectedText)) {
                    wait.until(ExpectedConditions.elementToBeClickable(suggestion)).click();
                    return true;
                }
            }
        }
        return false;
    }

    public String getHeadingText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(heading)).getText();
    }
}
