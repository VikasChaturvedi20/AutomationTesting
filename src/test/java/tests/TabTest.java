package tests;

import base.BaseTest;
import listeners.TestListener;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import utils.ReadDataFormCSV;

import java.util.List;
import java.util.Set;

public class TabTest extends BaseTest {

    @Test
    public void testTabHandling() {
        List<String[]> data = ReadDataFormCSV.read("src/main/resources/testdata.csv");

        for (String[] row : data) {
            String url = row[0];
            try {
                if (url.contains("windows")) {
                    driver.get(url);
                    TestListener.getTest().info("Navigated to Tab URL: " + url);

                    String parentWindow = driver.getWindowHandle();

                    driver.findElement(By.linkText("Click Here")).click();
                    TestListener.getTest().info("Clicked on 'Click Here' to open new tab");

                    Set<String> handles = driver.getWindowHandles();
                    for (String handle : handles) {
                        if (!handle.equals(parentWindow)) {
                            driver.switchTo().window(handle);
                            TestListener.getTest().info("Switched to new tab with title: " + driver.getTitle());
                            driver.close();
                            TestListener.getTest().info("Closed the new tab");
                        }
                    }

                    driver.switchTo().window(parentWindow);
                    TestListener.getTest().info("Switched back to parent tab");
                }
            } catch (Exception e) {
                TestListener.getTest().fail("Tab test failed due to: " + e.getMessage());
            }
        }
    }
}
