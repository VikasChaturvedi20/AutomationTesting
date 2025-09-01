/*package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import listeners.TestListener;

@Listeners(TestListener.class)
public class BaseTest {
    public WebDriver driver;

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

*/

/*package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import utils.ExtentManager;

@Listeners(listeners.TestListener.class)
public class BaseTest {
    public WebDriver driver;

    @BeforeSuite
    public void beforeSuite() {
        ExtentManager.initReports();
    }

    @Parameters({"browser"})
    @BeforeMethod
    public void setup(@Optional("chrome") String browser, java.lang.reflect.Method method) {
        // Initialize ExtentTest for this test method
        ExtentManager.createTest(method.getName());

        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
        ExtentManager.flushReports();  // flush after each test
    }
} */

/*package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import com.aventstack.extentreports.ExtentTest;
import utils.ExtentManager;

import java.lang.reflect.Method;
import java.time.Duration;

@Listeners(listeners.TestListener.class)
public class BaseTest {

    //protected WebDriver driver;
	public WebDriver driver;

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        ExtentManager.initReports(); // Initialize report once
    }

    @BeforeMethod(alwaysRun = true)
    public void setup(Method method) {
        // Initialize WebDriver
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        // Create Extent test with the method name
        ExtentManager.createTest(method.getName());
        ExtentManager.getExtentTest().info("Starting test: " + method.getName());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
       ExtentTest test = ExtentManager.getExtentTest();
    	//ExtentTest test = ExtentManager.getTest();
        // Log test result
        if (result.getStatus() == ITestResult.FAILURE) {
            test.fail("Test FAILED: " + result.getThrowable().getMessage());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.pass("Test PASSED");
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.skip("Test SKIPPED: " + result.getThrowable().getMessage());
        }

        // Quit driver
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        ExtentManager.flushReports(); // Write all logs to HTML
    }
}  */

/*package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import utils.ExtentManager;

import java.lang.reflect.Method;

@Listeners(listeners.TestListener.class)
public class BaseTest {
    public WebDriver driver;

    @BeforeSuite
    public void beforeSuite() {
        ExtentManager.initReports();
    }

    @BeforeMethod
    public void setUp(Method method) {
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        // Create a test node in ExtentReport for each test method
        ExtentManager.createTest(method.getName());
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterSuite
    public void afterSuite() {
        ExtentManager.flushReports();
    }
}


*/

/*package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

@Listeners(listeners.TestListener.class)
public class BaseTest {
    public WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
*/

package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.*;
import listeners.TestListener;
import utils.ConfigReader;

@Listeners(TestListener.class)
public class BaseTest {
    public WebDriver driver;

    @BeforeMethod
    public void setUp() {
        String browserName = ConfigReader.get("browser"); // get browser from config

        switch (browserName.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;

            case "ie":
                WebDriverManager.iedriver().setup();
                driver = new InternetExplorerDriver();
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;

            default:
                throw new IllegalArgumentException("Browser not supported: " + browserName);
        }

        driver.manage().window().maximize();
        //driver.get(ConfigReader.get("url")); // launch URL from config
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
