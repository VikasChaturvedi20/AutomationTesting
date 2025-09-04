/*package utils;

import locators.AllLocators;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.lang.reflect.Field;

public class KeywordEngine {
    private WebDriver driver;
    private AllLocators locators;

    public KeywordEngine(WebDriver driver, AllLocators locators) {
        this.driver = driver;
        this.locators = locators;
    }

    private WebElement getElement(String elementName) {
        try {
            if (elementName == null || elementName.trim().isEmpty()) {
                return null;
            }
            Field field = locators.getClass().getDeclaredField(elementName);
            field.setAccessible(true);
            return (WebElement) field.get(locators);
        } catch (Exception e) {
            throw new RuntimeException("❌ Element not found in AllLocators: " + elementName, e);
        }
    }

    public void executeStep(String keyword, String elementName, String testData) {
        WebElement element = getElement(elementName);

        switch (keyword.toLowerCase()) {
            case "openurl":
                driver.get(testData);
                break;

            case "type":
                if (element != null) {
                    element.clear();
                    element.sendKeys(testData);
                }
                break;

            case "click":
                if (element != null) {
                    element.click();
                }
                break;

            case "uploadfile":
                if (element != null) {
                    // ✅ Convert relative to absolute path
                    File file = new File(testData);
                    String absolutePath = file.getAbsolutePath();
                    element.sendKeys(absolutePath);
                }
                break;

            case "verifytext":
                if (element != null) {
                    String actual = element.getText().trim();
                    if (!actual.contains(testData)) {
                        throw new AssertionError(
                                "❌ Text verification failed. Expected: " + testData + ", Actual: " + actual
                        );
                    }
                }
                break;

            default:
                throw new UnsupportedOperationException("❌ Unknown keyword: " + keyword);
        }
    }
}
*/

package utils;

import locators.AllLocators;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Field;

public class KeywordEngine {
    private WebDriver driver;
    private AllLocators locators;
    private KeywordUtils utils;  // ✅ Use the new utils class

    public KeywordEngine(WebDriver driver, AllLocators locators) {
        this.driver = driver;
        this.locators = locators;
        this.utils = new KeywordUtils(driver); // initialize
    }

    private WebElement getElement(String elementName) {
        try {
            if (elementName == null || elementName.trim().isEmpty()) {
                return null;
            }
            Field field = locators.getClass().getDeclaredField(elementName);
            field.setAccessible(true);
            return (WebElement) field.get(locators);
        } catch (Exception e) {
            throw new RuntimeException("❌ Element not found in AllLocators: " + elementName, e);
        }
    }

    public void executeStep(String keyword, String elementName, String testData) {
        WebElement element = getElement(elementName);

        switch (keyword.toLowerCase()) {
            case "openurl":
                utils.openUrl(testData);
                break;

            case "type":
                if (element != null) utils.type(element, testData);
                break;

            case "click":
                if (element != null) utils.click(element);
                break;

            case "uploadfile":
                if (element != null) utils.uploadFile(element, testData);
                break;

            case "verifytext":
                if (element != null) utils.verifyText(element, testData);
                break;

            default:
                throw new UnsupportedOperationException("❌ Unknown keyword: " + keyword);
        }
    }
}
