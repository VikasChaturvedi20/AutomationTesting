package tests;

import base.BaseTest;
import listeners.TestListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.ReadDataFormCSV;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public class JQueryUIDatepickerTest extends BaseTest {

    @Test
    @Parameters("csvFilePath")
    public void selectDateInDatepicker(String csvFilePath) {
        WebDriver driver = getDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        List<Map<String, String>> data = ReadDataFormCSV.read(csvFilePath);

        for (Map<String, String> row : data) {
            String url = row.get("url");
            String targetYear = row.get("year");
            String targetMonth = row.get("month");
            String targetDay = row.get("day");

            try {
                if (url.contains("datepicker")) {
                    driver.get(url);
                    TestListener.getTest().info("Navigated to URL: " + url);

                    // Switch to iframe
                    WebElement iframe = wait.until(ExpectedConditions.presenceOfElementLocated(
                            By.cssSelector(".demo-frame")));
                    driver.switchTo().frame(iframe);

                    // Open the datepicker input
                    WebElement dateInput = wait.until(
                            ExpectedConditions.elementToBeClickable(By.id("datepicker")));
                    dateInput.click();

                    // Navigate until we reach target month/year
                    while (true) {
                        WebElement monthElement = driver.findElement(By.className("ui-datepicker-month"));
                        WebElement yearElement = driver.findElement(By.className("ui-datepicker-year"));

                        String currentMonth = monthElement.getText();
                        String currentYear = yearElement.getText();

                        if (currentMonth.equals(targetMonth) && currentYear.equals(targetYear)) {
                            break;
                        }

                        int currYear = Integer.parseInt(currentYear);
                        int targYear = Integer.parseInt(targetYear);

                        if (currYear > targYear ||
                                (currYear == targYear &&
                                        getMonthNumber(currentMonth) > getMonthNumber(targetMonth))) {
                            // Go back in time
                            driver.findElement(By.cssSelector(".ui-datepicker-prev")).click();
                        } else {
                            // Go forward in time
                            driver.findElement(By.cssSelector(".ui-datepicker-next")).click();
                        }

                        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ui-datepicker-title")));
                    }

                    // Select target day
                    WebElement dayCell = driver.findElement(By.xpath(
                            "//td[not(contains(@class,'ui-datepicker-other-month'))]/a[text()='" + targetDay + "']"));
                    dayCell.click();

                    // Exit iframe
                    driver.switchTo().defaultContent();

                    // Re-locate input outside iframe
                    WebElement dateInputOutside = driver.findElement(By.id("datepicker"));
                    String pickedDate = dateInputOutside.getAttribute("value");

                    TestListener.getTest().info("Picked Date: " + pickedDate);
                    Assert.assertTrue(pickedDate.contains(targetDay),
                            "Incorrect date selected! Expected: " + targetDay + " but got: " + pickedDate);

                    TestListener.getTest().pass("Date successfully selected: " + pickedDate);
                }
            } catch (Exception e) {
                TestListener.getTest().fail("Test failed for URL: " + url + " due to: " + e.getMessage());
                Assert.fail("Exception occurred for URL: " + url, e);
            }
        }
    }

    // Helper: convert month name to number
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
