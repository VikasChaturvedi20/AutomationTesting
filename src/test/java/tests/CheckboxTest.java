package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CheckboxTest extends BaseTest {
    @Test
    public void selectCheckbox() {
        driver.get("https://demoqa.com/elements");
        By chekboxoption=By.id("item-1");
        driver.findElement(chekboxoption).click();
        By checkbox1 = By.xpath("//span[@class='rct-checkbox']");
        driver.findElement(checkbox1).click();
        Assert.assertTrue(driver.findElement(checkbox1).isSelected());
    }
}
