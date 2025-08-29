package tests;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import base.BaseTest;
import utils.ExtentManager;
import utils.ReadDataFormCSV;

public class DynamicPaginationTableTest extends BaseTest {

    @Test
    public void verifyPaginationTable() throws InterruptedException {
        List<String[]> data = ReadDataFormCSV.read("src/main/resources/testdata.csv");

        for (String[] row : data) {
            String url = row[0];

            try {
                if (url.contains("dynamic-pagination-table")) {
                    driver.get(url);
                    ExtentManager.getExtentTest().info("Navigated to URL: " + url);

                    String targetName = "Airi Satou";   // example person to search in table
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
                                ExtentManager.getExtentTest().pass("Found " + targetName + " with office: " + officeValue);
                                Assert.assertEquals(officeValue, expectedOffice, "Office location mismatch!");
                                recordFound = true;
                                break;
                            }
                        }

                        if (recordFound) break;

                        // Move to next page if available
                        WebElement nextBtn = driver.findElement(By.cssSelector("#example_next"));
                        if (nextBtn.getAttribute("class").contains("disabled")) {
                            ExtentManager.getExtentTest().fail("Record not found after checking all pages");
                            break; // no more pages
                        }
                        nextBtn.click();
                        Thread.sleep(1000); // small wait for page reload
                    }

                    Assert.assertTrue(recordFound, "Target record not found in paginated table");
                }
            } catch (Exception e) {
                ExtentManager.getExtentTest().fail("Exception occurred: " + e.getMessage());
            }
        }
    }
}
