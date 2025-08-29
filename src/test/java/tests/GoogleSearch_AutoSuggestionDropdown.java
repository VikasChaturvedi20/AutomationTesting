/*package tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import base.BaseTest;
import utils.ExtentManager;
import utils.ReadDataFormCSV;

public class GoogleSearch_AutoSuggestionDropdown extends BaseTest {
	@Test
	public void AutoSuggestion_DropDown() {
		// Get ExtentTest instance
		ExtentTest test = ExtentManager.getExtentTest();

		// Read data from CSV
		List<String[]> data = ReadDataFormCSV.read("src/main/resources/testdata.csv");

		for (String[] row : data) {
			String url = row[0];
			String input_text = row[3];
			

			try 
			{
				if(url.contains("playwright.dev"))
				{	
				  driver.get(url);
                  test.info("Navigated to URL: " + url);
                //test.info("Nagvigate to Google Search");
				}
				
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

                    driver.findElement(By.cssSelector(".DocSearch-Button-Placeholder")).click();
                    wait.until(ExpectedConditions.elementToBeClickable(By.id("docsearch-input"))).click();
                    driver.findElement(By.id("docsearch-input")).sendKeys(input_text);
                    test.info("Entered search text: " + input_text);
                //test.info("Searched the Text");
                
                List <WebElement> list= driver.findElements(By.xpath("//ul[@role='listbox']//li[@role='option']"));
                
                System.out.println(list.size());
                test.info("List of elements in Searchbox"+list.size());
                
                for(int i=0;i<list.size();i++)
                {
                	System.out.println(list.get(i).getText());
                	if(list.get(i).getText().equals(input_text))
                	{
                		list.get(i).click();
                		test.info("Clicking on selected item");
                		break;
                		
                	}
                }
                
                WebElement firstResult = wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.cssSelector("h1:nth-of-type(1)")));
					Assert.assertTrue(firstResult.getText().contains("Parameterize tests"));
					test.pass("Search result displayed successfully for: " + input_text);
               // WebElement successMsg = driver.findElement(By.xpath("//h3[text()='Selenium']"));
                //Assert.assertTrue(successMsg.getText().contains("Selenium"));
               // test.pass("Selected Search item is successfully Displayed: " + input_text);
                	
			} 
			catch (Exception e) 
			{
				// TODO: handle exception
				test.fail("Failed to Search item from Auto Suggestion box: " + input_text + " - " + e.getMessage());
                Assert.fail("User is failed to Search the Item: " + input_text, e);
			}
		}
	}

}  */

/*package tests;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import base.BaseTest;
import utils.ExtentManager;
import utils.ReadDataFormCSV;

import java.time.Duration;

public class GoogleSearch_AutoSuggestionDropdown extends BaseTest {

	@Test
	public void AutoSuggestion_DropDown() {
		ExtentTest test = ExtentManager.getExtentTest();

		List<String[]> data = ReadDataFormCSV.read("src/main/resources/testdata.csv");

		for (String[] row : data) {
			String url = row[0];
			String input_text = row[3];

			try {
				if (url.contains("playwright.dev")) {
					driver.get(url);
					test.info("Navigated to URL: " + url);
					
					driver.findElement(By.cssSelector(".DocSearch-Button-Placeholder")).click();
					Thread.sleep(5000);
					driver.findElement(By.id("docsearch-input")).click();
					Thread.sleep(5000);
					driver.findElement(By.id("docsearch-input")).sendKeys(input_text);
					//driver.findElement(By.name("q")).sendKeys(input_text);
					test.info("Entered search text: " + input_text);

					// ✅ Use WebDriverWait instead of Thread.sleep
					//WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
					//List<WebElement> suggestions = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
					//		By.xpath("//ul[@role='listbox']//li//div[@role='option']")));
					
					WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
					List<WebElement> suggestions = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
							By.xpath("//ul[@role='listbox']//li[@role='option']")));

					test.info("Number of suggestions: " + suggestions.size());

					boolean clicked = false;
					for (WebElement suggestion : suggestions) {
						String suggestionText = suggestion.getText();
						System.out.println(suggestionText);

						// Contains instead of equals
						if (suggestionText.toLowerCase().contains("Parameterize tests")) {
							suggestion.click();
							test.info("Clicked on suggestion: " + suggestionText);
							clicked = true;
							break;
						}
					}

					//Assert.assertTrue(clicked, "No matching suggestion found for: " + input_text);

					//  Looser assertion on results page
					WebElement firstResult = wait.until(ExpectedConditions
							.visibilityOfElementLocated(By.cssSelector("h1:nth-of-type(1)")));
					Assert.assertTrue(firstResult.getText().contains("Parameterize tests"));
					test.pass("Search result displayed successfully for: " + input_text);

				}

			} catch (Exception e) {
				test.fail("Failed to search for: " + input_text + " - " + e.getMessage());
				Assert.fail("User failed to search for: " + input_text, e);
			}
		}
	}
}
 */

package tests;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import base.BaseTest;
import utils.ExtentManager;
import utils.ReadDataFormCSV;

import java.time.Duration;

public class GoogleSearch_AutoSuggestionDropdown extends BaseTest {

    @Test
    public void AutoSuggestion_DropDown() {
        ExtentTest test = ExtentManager.getExtentTest();

        List<String[]> data = ReadDataFormCSV.read("src/main/resources/testdata.csv");

        for (String[] row : data) {
            String url = row[0];
            String input_text = row[3];

            try {
                if (url.contains("playwright.dev")) {
                    driver.get(url);
                    test.info("Navigated to URL: " + url);

                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

                    driver.findElement(By.cssSelector(".DocSearch-Button-Placeholder")).click();
                    wait.until(ExpectedConditions.elementToBeClickable(By.id("docsearch-input"))).click();
                    driver.findElement(By.id("docsearch-input")).sendKeys(input_text);
                    test.info("Entered search text: " + input_text);

                    // Wait until first suggestion appears
                    wait.until(ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//ul[@role='listbox']//li[@role='option']")));

                    boolean clicked = false;

                    // Retry loop to avoid stale element issues
                    for (int retry = 0; retry < 3; retry++) {
                        List<WebElement> suggestions = driver.findElements(
                                By.xpath("//ul[@role='listbox']//li[@role='option']"));

                        for (WebElement suggestion : suggestions) {
                            String suggestionText = suggestion.getText().trim();
                            System.out.println("Suggestion: " + suggestionText);

                            if (suggestionText.equalsIgnoreCase("Parameterize tests")) {
                                wait.until(ExpectedConditions.elementToBeClickable(suggestion)).click();
                                test.info("Clicked on suggestion: " + suggestionText);
                                clicked = true;
                                break;
                            }
                        }

                        if (clicked) break;
                    }

                    Assert.assertTrue(clicked, "No matching suggestion found for: " + input_text);

                    // Looser assertion on results page
                    WebElement firstResult = wait.until(ExpectedConditions
                            .visibilityOfElementLocated(By.cssSelector("h1")));
                    Assert.assertTrue(firstResult.getText().contains("Parameterize tests"),
                            "Search result page heading mismatch!");

                    test.pass("Search result displayed successfully for: " + input_text);
                }

            } catch (Exception e) {
                test.fail("Failed to search for: " + input_text + " - " + e.getMessage());
                Assert.fail("User failed to search for: " + input_text, e);
            }
        }
    }
}
 
