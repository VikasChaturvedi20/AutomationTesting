package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DropdownTest extends BaseTest {
    @Test
    public void selectDropdownOption() {
        driver.get("https://the-internet.herokuapp.com/dropdown");
        WebElement dropdown = driver.findElement(By.id("dropdown"));
        Select select = new Select(dropdown);
        select.selectByVisibleText("Option 2");
        Assert.assertEquals(select.getFirstSelectedOption().getText(), "Option 2");
    }
}
