package tests;

import base.BaseTest;
import listeners.TestListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.ReadDataFormCSV;

import java.util.List;
import java.util.Map;

public class DynamicPaginationTableTest extends BaseTest {

    @Test
    @Parameters("csvFilePath")
    public void verifyPaginationTable(String csvFilePath) throws InterruptedException {
        WebDriver driver = getDriver();
        List<Map<String, String>> data = ReadDataFormCSV.read(csvFilePath);

        for (Map<String, String> row : data) {
            String url = row.get("url");   // now using header name

            try {
                if (url.contains("dynamic-pagination-table")) {
                    driver.get(url);
                    TestListener.getTest().info("Navigated to URL: " + url);

                    String targetName = "Airi Satou";   // example person to search
                    String expectedOffice = "Tokyo";    // expected value

                    boolean recordFound = false;

                    // Loop through pagination
                    while (true) {
                        WebElement table = driver.findElement(By.cssSelector("table#example"));
                        List<WebElement> rows = table.findElements(By.cssSelector("tbody tr"));

                        for (WebElement r : rows) {
                            List<WebElement> cols = r.findElements(By.cssSelector("td"));
                            if (cols.size() > 0 && cols.get(0).getText().equalsIgnoreCase(targetName)) {
                                String officeValue = cols.get(2).getText();
                                TestListener.getTest().pass("Found " + targetName + " with office: " + officeValue);
                                Assert.assertEquals(officeValue, expectedOffice, "Office location mismatch!");
                                recordFound = true;
                                break;
                            }
                        }

                        if (recordFound) break;

                        // Move to next page if available
                        WebElement nextBtn = driver.findElement(By.cssSelector("#example_next"));
                        if (nextBtn.getAttribute("class").contains("disabled")) {
                            TestListener.getTest().fail("Record not found after checking all pages");
                            break; // no more pages
                        }
                        nextBtn.click();
                        Thread.sleep(1000); // small wait for page reload
                    }

                    Assert.assertTrue(recordFound, "Target record not found in paginated table");
                }
            } catch (Exception e) {
                TestListener.getTest().fail("Exception occurred: " + e.getMessage());
                Assert.fail("Test failed for URL: " + url, e);
            }
        }
    }
}
