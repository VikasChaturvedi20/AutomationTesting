package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CheckboxTest extends BaseTest {

    @Test(groups = {"smoke"})
    public void selectCheckbox() {
        WebDriver driver = getDriver(); // ✅ Use ThreadLocal driver

        driver.get("https://demoqa.com/elements");

        // Click on the "Check Box" menu option
        By checkboxOption = By.id("item-1");
        driver.findElement(checkboxOption).click();

        // Click the first checkbox
        By checkboxSpan = By.xpath("//span[@class='rct-checkbox']");
        driver.findElement(checkboxSpan).click();

        // ✅ Get the hidden input inside the checkbox wrapper
        WebElement checkboxInput = driver.findElement(By.cssSelector("input[type='checkbox']"));

        // Validate checkbox is selected
        Assert.assertTrue(checkboxInput.isSelected(), "Checkbox should be selected after clicking!");
    }
}
