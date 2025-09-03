package tests;

import base.BaseTest;
import listeners.TestListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DropdownTest extends BaseTest {

    @Test
    public void selectDropdownOption() {
        try {
            getDriver().get("https://the-internet.herokuapp.com/dropdown");
            TestListener.getTest().info("Navigated to dropdown page");

            WebElement dropdown = getDriver().findElement(By.id("dropdown"));
            Select select = new Select(dropdown);

            select.selectByVisibleText("Option 2");
            String selectedOption = select.getFirstSelectedOption().getText();

            Assert.assertEquals(selectedOption, "Option 2", "Dropdown selection mismatch!");
            TestListener.getTest().pass("Successfully selected Option 2 from dropdown");

        } catch (Exception e) {
            TestListener.getTest().fail("Dropdown test failed - " + e.getMessage());
            Assert.fail("Dropdown test failed", e);
        }
    }
}
