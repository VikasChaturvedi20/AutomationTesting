package tests;

import base.BaseTest;
import listeners.TestListener;
import locators.AllLocators;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.KeywordEngine;
import utils.ReadDataFormCSV;

import java.util.List;
import java.util.Map;

public class KeywordDrivenTest extends BaseTest {

    @Test(groups = {"keyword"})
    @Parameters("keywordCsvPath")
    public void runKeywordDrivenTests(
            @Optional("src/main/resources/keywords.csv") String keywordCsvPath) {  // ✅ default path

        WebDriver driver = getDriver();

        // ✅ Initialize PageFactory locators
        AllLocators locators = initPage(AllLocators.class);
        KeywordEngine engine = new KeywordEngine(driver, locators);

        // ✅ Read keywords from CSV
        List<Map<String, String>> steps = ReadDataFormCSV.read(keywordCsvPath);

        for (Map<String, String> step : steps) {
            String keyword = step.get("Keyword");
            String element = step.get("Element");
            String testData = step.get("TestData");

            // ✅ Safe logging
            if (TestListener.getTest() != null) {
                TestListener.getTest().info(
                        "Executing step → " + keyword + " | " + element + " | " + testData
                );
            } else {
                System.out.println("[INFO] Executing step → " + keyword + " | " + element + " | " + testData);
            }

            engine.executeStep(keyword, element, testData);
        }
    }
}



