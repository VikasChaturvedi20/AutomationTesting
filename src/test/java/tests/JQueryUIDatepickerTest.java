package tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import utils.ExtentManager;
import utils.ReadDataFormCSV;

public class JQueryUIDatepickerTest extends BaseTest {

    @Test
    public void selectDateInDatepicker() {
        List<String[]> data = ReadDataFormCSV.read("src/main/resources/testdata.csv");

        for (String[] row : data) {
            String url = row[0];
            String targetYear = row[4];
            String targetMonth = row[5];
            String targetDay = row[6];

            try {
                if (url.contains("datepicker")) {
                    driver.get(url);
                    ExtentManager.getExtentTest().info("Navigated to URL: " + url);

                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

                    // Switch to iframe
                    WebElement iframe = driver.findElement(By.cssSelector(".demo-frame"));
                    driver.switchTo().frame(iframe);

                    // Open the datepicker input
                    WebElement dateInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("datepicker")));
                    dateInput.click();

                    // Navigate to target month & year (forward or backward)
                    while (true) {
                        WebElement monthElement = driver.findElement(By.className("ui-datepicker-month"));
                        WebElement yearElement = driver.findElement(By.className("ui-datepicker-year"));

                        String currentMonth = monthElement.getText();
                        String currentYear = yearElement.getText();

                        if (currentMonth.equals(targetMonth) && currentYear.equals(targetYear)) {
                            break;
                        }

                        // Compare years & months
                        int currYear = Integer.parseInt(currentYear);
                        int targYear = Integer.parseInt(targetYear);

                        if (currYear > targYear || 
                           (currYear == targYear && getMonthNumber(currentMonth) > getMonthNumber(targetMonth))) {
                            // Go back in time
                            driver.findElement(By.cssSelector(".ui-datepicker-prev")).click();
                        } else {
                            // Go forward in time
                            driver.findElement(By.cssSelector(".ui-datepicker-next")).click();
                        }

                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ui-datepicker-title")));
                    }

                    // Select the day
                    WebElement dayCell = driver.findElement(By.xpath(
                        "//td[not(contains(@class,'ui-datepicker-other-month'))]/a[text()='" + targetDay + "']"
                    ));
                   /* dayCell.click();

                    // Exit iframe & validate
                    driver.switchTo().defaultContent();
                    String pickedDate = dateInput.getAttribute("value");

                    ExtentManager.getExtentTest().info("Picked Date: " + pickedDate);
                    System.out.println("Picked Date: " + pickedDate);

                    Assert.assertTrue(pickedDate.contains(targetDay),
                            "Incorrect date selected!"); */
                 // After selecting the day
                    dayCell.click();

                    // Exit iframe & validate
                    driver.switchTo().defaultContent();

                    // Re-locate the input again
                    WebElement dateInputOutside = driver.findElement(By.id("datepicker"));
                    String pickedDate = dateInputOutside.getAttribute("value");

                    ExtentManager.getExtentTest().info("Picked Date: " + pickedDate);
                    System.out.println("Picked Date: " + pickedDate);

                    Assert.assertTrue(pickedDate.contains(targetDay),
                            "Incorrect date selected! Expected: " + targetDay + " but got: " + pickedDate);

                }
            } catch (Exception e) {
                ExtentManager.getExtentTest().fail("Test failed for URL: " + url + " due to: " + e.getMessage());
                Assert.fail("Exception occurred: " + e.getMessage());
            }
        }
    }

    // Helper to convert month name → number
    private int getMonthNumber(String monthName) {
        switch (monthName) {
            case "January": return 1;
            case "February": return 2;
            case "March": return 3;
            case "April": return 4;
            case "May": return 5;
            case "June": return 6;
            case "July": return 7;
            case "August": return 8;
            case "September": return 9;
            case "October": return 10;
            case "November": return 11;
            case "December": return 12;
            default: throw new IllegalArgumentException("Invalid month: " + monthName);
        }
    }
}
