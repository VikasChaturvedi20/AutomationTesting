package tests;

import base.BaseTest;
import listeners.TestListener;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.ReadDataFormCSV;

import java.time.Duration;
import java.util.List;

public class ShadowDomTest extends BaseTest {

    @Test
    public void testShadowDom() {
        List<String[]> data = ReadDataFormCSV.read("src/main/resources/testdata.csv");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        for (String[] row : data) {
            String url = row[0];

            try {
                if (url.contains("shadow")) {
                    driver.get(url);
                    TestListener.getTest().info("Navigated to: " + url);

                    WebElement host = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("book-app")));
                    SearchContext shadowRoot = (SearchContext) ((JavascriptExecutor) driver)
                            .executeScript("return arguments[0].shadowRoot", host);

                    WebElement searchBox = shadowRoot.findElement(By.cssSelector("input#input"));
                    searchBox.sendKeys("Selenium WebDriver");

                    TestListener.getTest().pass("Entered text into Shadow DOM input");

                    Assert.assertEquals(searchBox.getAttribute("value"), "Selenium WebDriver",
                            "Shadow DOM input should contain entered text");
                }
            } catch (Exception e) {
                TestListener.getTest().fail("Shadow DOM error at: " + url + " | " + e.getMessage());
                Assert.fail("Shadow DOM handling failed: " + e.getMessage());
            }
        }
    }
}
