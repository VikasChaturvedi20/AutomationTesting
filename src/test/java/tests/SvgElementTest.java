package tests;

import base.BaseTest;
import listeners.TestListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ReadDataFormCSV;

import java.time.Duration;
import java.util.List;

public class SvgElementTest extends BaseTest {

    @Test
    public void testSvgElement() {
        List<String[]> data = ReadDataFormCSV.read("src/main/resources/testdata.csv");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        for (String[] row : data) {
            String url = row[0];

            try {
                if (url.contains("svg")) {
                    driver.get(url);
                    TestListener.getTest().info("Navigated to: " + url);

                    // Example: Click USA in SVG
                    WebElement usa = wait.until(ExpectedConditions
                            .visibilityOfElementLocated(By.cssSelector("svg path[title='United States']")));

                    usa.click();
                    TestListener.getTest().pass("Clicked on USA in SVG map");

                    Assert.assertTrue(usa.isDisplayed(), "USA element should be visible in SVG");
                }
            } catch (Exception e) {
                TestListener.getTest().fail("SVG error at: " + url + " | " + e.getMessage());
                Assert.fail("SVG element handling failed: " + e.getMessage());
            }
        }
    }
}
