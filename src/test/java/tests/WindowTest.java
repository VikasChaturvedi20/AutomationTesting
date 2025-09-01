package tests;

import base.BaseTest;
import listeners.TestListener;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import utils.ReadDataFormCSV;

import java.util.List;
import java.util.Set;

public class WindowTest extends BaseTest {

    @Test
    public void testWindowHandling() {
        List<String[]> data = ReadDataFormCSV.read("src/main/resources/testdata.csv");

        for (String[] row : data) {
            String url = row[0];
            try {
                if (url.contains("Windows.html")) {
                    driver.get(url);
                    TestListener.getTest().info("Navigated to Window URL: " + url);

                    String parentWindow = driver.getWindowHandle();

                    driver.findElement(By.cssSelector("button.btn.btn-info")).click();
                    TestListener.getTest().info("Clicked on button to open new window");

                    Set<String> handles = driver.getWindowHandles();
                    for (String handle : handles) {
                        if (!handle.equals(parentWindow)) {
                            driver.switchTo().window(handle);
                            TestListener.getTest().info("Switched to new window with title: " + driver.getTitle());
                            driver.close();
                            TestListener.getTest().info("Closed the new window");
                        }
                    }

                    driver.switchTo().window(parentWindow);
                    TestListener.getTest().info("Switched back to parent window");
                }
            } catch (Exception e) {
                TestListener.getTest().fail("Window test failed due to: " + e.getMessage());
            }
        }
    }
}
