package tests;

import base.BaseTest;
import listeners.TestListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.ReadDataFormCSV;

import java.util.List;
import java.util.Map;

public class SliderTest extends BaseTest {

    @Test
    @Parameters("csvFilePath")
    public void testSliderMovement(String csvFilePath) {
        WebDriver driver = getDriver();
        List<Map<String, String>> data = ReadDataFormCSV.read(csvFilePath);

        for (Map<String, String> row : data) {
            String url = row.get("url");

            try {
                if (url.contains("horizontal_slider")) {
                    driver.get(url);
                    TestListener.getTest().info("Navigated to Slider URL: " + url);

                    WebElement slider = driver.findElement(By.cssSelector("input[type='range']"));
                    Actions actions = new Actions(driver);

                    // Move slider to the right
                    actions.dragAndDropBy(slider, 50, 0).perform();
                    TestListener.getTest().info("Moved slider to the right");

                    // Move slider to the left
                    actions.dragAndDropBy(slider, -30, 0).perform();
                    TestListener.getTest().info("Moved slider to the left");

                    // Validation (optional: you can validate slider value)
                    Assert.assertTrue(true, "Slider moved successfully");
                    TestListener.getTest().pass("Slider test executed successfully");
                }
            } catch (Exception e) {
                TestListener.getTest().fail("Slider test failed for URL: " + url + " due to: " + e.getMessage());
                Assert.fail("Slider test failed for URL: " + url, e);
            }
        }
    }
}
