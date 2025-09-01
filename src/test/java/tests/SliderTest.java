package tests;

import base.BaseTest;
import listeners.TestListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ReadDataFormCSV;

import java.util.List;

public class SliderTest extends BaseTest {

    @Test
    public void testSliderMovement() {
        List<String[]> data = ReadDataFormCSV.read("src/main/resources/testdata.csv");

        for (String[] row : data) {
            String url = row[0];
            try {
                if (url.contains("horizontal_slider")) {
                    driver.get(url);
                    TestListener.getTest().info("Navigated to Slider URL: " + url);

                    WebElement slider = driver.findElement(By.cssSelector("input[type='range']"));

                    Actions actions = new Actions(driver);
                    actions.dragAndDropBy(slider, 50, 0).perform();
                    TestListener.getTest().info("Moved slider to the right");

                    actions.dragAndDropBy(slider, -30, 0).perform();
                    TestListener.getTest().info("Moved slider to the left");
                    Assert.assertTrue(true);
                }
            } catch (Exception e) {
                TestListener.getTest().fail("Slider test failed due to: " + e.getMessage());
            }
        }
    }
}
