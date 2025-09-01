package tests;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import listeners.TestListener;
import reports.ExtentManager;
import utils.ReadDataFormCSV;

public class DynamicWebTableTest extends BaseTest {

    @Test
    public void verifyChromeCpuLoad() {
        // Read URLs from CSV
        List<String[]> data = ReadDataFormCSV.read("src/main/resources/testdata.csv");

        for (String[] row : data) {
            String url = row[0];

            try {
                if (url.contains("expandtesting")) {
                    driver.get(url);

                   TestListener.getTest().info("Navigated to URL: " + url);

                    // Locate the dynamic table
                    WebElement table = driver.findElement(By.cssSelector("table.table-striped"));

                    // Identify the index of the "CPU" column dynamically
                    List<WebElement> headers = table.findElements(By.cssSelector("thead tr th"));
                    int cpuColIndex = -1;
                    for (int i = 0; i < headers.size(); i++) {
                        if (headers.get(i).getText().trim().equalsIgnoreCase("CPU")) {
                            cpuColIndex = i;
                            break;
                        }
                    }
                    Assert.assertTrue(cpuColIndex >= 0, "CPU column not found");

                    // Iterate through table rows to find "Chrome"
                    List<WebElement> rows = table.findElements(By.cssSelector("tbody tr"));
                    String cpuValueFromTable = null;

                    for (WebElement r : rows) {
                        List<WebElement> cells = r.findElements(By.cssSelector("td"));
                        if (cells.size() > 0 && cells.get(0).getText().trim().equalsIgnoreCase("Chrome")) {
                            cpuValueFromTable = cells.get(cpuColIndex).getText().trim();
                            break;
                        }
                    }
                    Assert.assertNotNull(cpuValueFromTable, "Chrome row not found");

                    // Read the yellow label displaying CPU
                    WebElement label = driver.findElement(By.xpath("//p[contains(text(),'Chrome CPU:')]"));
                    String labelText = label.getText().trim(); // "Chrome CPU: 7.9%"
                    String cpuValueFromLabel = labelText.split(":")[1].trim();

                    System.out.println("CPU from table  : " + cpuValueFromTable);
                    System.out.println("CPU from label : " + cpuValueFromLabel);

                    // Final validation
                    Assert.assertEquals(cpuValueFromTable, cpuValueFromLabel,
                            "CPU value mismatch between table and label");
                }
            } catch (Exception e) {
               TestListener.getTest().fail("Test failed for URL: " + url + " | Error: " + e.getMessage());
                throw e;
            }
        }
    }
}
