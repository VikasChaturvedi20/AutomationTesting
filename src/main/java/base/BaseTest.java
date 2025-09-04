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

/*package base;

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
*/

/*package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;
import listeners.TestListener;
import utils.ConfigReader;

@Listeners(TestListener.class)
public class BaseTest {
    public WebDriver driver;

    @BeforeMethod
    public void setUp() {
        String browserName = ConfigReader.get("browser");   // e.g. chrome, firefox
        String headless = ConfigReader.get("headless");     // true / false
        
        System.out.println("Browser: " + browserName + " | Headless flag: " + headless);

        boolean isHeadless = "true".equalsIgnoreCase(headless);

        switch (browserName.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();

                if (isHeadless) {
                    chromeOptions.addArguments("--headless=new"); // modern headless
                    chromeOptions.addArguments("--disable-gpu");
                    chromeOptions.addArguments("--window-size=1920,1080");
                }

                driver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();

                if (isHeadless) {
                    firefoxOptions.addArguments("--headless");
                    firefoxOptions.addArguments("--width=1920");
                    firefoxOptions.addArguments("--height=1080");
                }

                driver = new FirefoxDriver(firefoxOptions);
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

        // Only maximize window if NOT headless
        if (!isHeadless) {
            driver.manage().window().maximize();
        }

        // Debug: print final capabilities
        System.out.println("Final Capabilities: " + ((RemoteWebDriver) driver).getCapabilities());
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

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;
import listeners.TestListener;
import utils.ConfigReader;

@Listeners(TestListener.class)
public class BaseTest {
    public WebDriver driver;

    @Parameters({"browser", "headless"})
    @BeforeMethod
    public void setUp(@Optional("") String browserParam,
                      @Optional("") String headlessParam) {
        // Read from TestNG first, fallback to config.properties
        String browserName = browserParam.isEmpty() ? ConfigReader.get("browser") : browserParam;
        String headless = headlessParam.isEmpty() ? ConfigReader.get("headless") : headlessParam;

        System.out.println("Browser: " + browserName + " | Headless flag: " + headless);

        boolean isHeadless = "true".equalsIgnoreCase(headless);

        switch (browserName.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();

                if (isHeadless) {
                    chromeOptions.addArguments("--headless=new"); // modern headless
                    chromeOptions.addArguments("--disable-gpu");
                    chromeOptions.addArguments("--window-size=1920,1080");
                }

                driver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();

                if (isHeadless) {
                    firefoxOptions.addArguments("--headless");
                    firefoxOptions.addArguments("--width=1920");
                    firefoxOptions.addArguments("--height=1080");
                }

                driver = new FirefoxDriver(firefoxOptions);
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

        // Only maximize window if NOT headless
        if (!isHeadless) {
            driver.manage().window().maximize();
        }

        // Debug: print final capabilities
        System.out.println("Final Capabilities: " + ((RemoteWebDriver) driver).getCapabilities());
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

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;
import listeners.TestListener;
import utils.ConfigReader;

@Listeners(TestListener.class)
public class BaseTest {

    // Thread-safe driver
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return driver.get();
    }

    @Parameters({"browser", "headless"})
    @BeforeMethod
    public void setUp(@Optional("") String browserParam,
                      @Optional("") String headlessParam) {

        String browserName = browserParam.isEmpty() ? ConfigReader.get("browser") : browserParam;
        String headless = headlessParam.isEmpty() ? ConfigReader.get("headless") : headlessParam;

        System.out.println("Browser: " + browserName + " | Headless flag: " + headless);

        boolean isHeadless = "true".equalsIgnoreCase(headless);
        WebDriver localDriver;

        switch (browserName.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                if (isHeadless) {
                    chromeOptions.addArguments("--headless=new", "--disable-gpu", "--window-size=1920,1080");
                }
                localDriver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (isHeadless) {
                    firefoxOptions.addArguments("--headless", "--width=1920", "--height=1080");
                }
                localDriver = new FirefoxDriver(firefoxOptions);
                break;

            case "ie":
                WebDriverManager.iedriver().setup();
                localDriver = new InternetExplorerDriver();
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                localDriver = new EdgeDriver();
                break;

            default:
                throw new IllegalArgumentException("Browser not supported: " + browserName);
        }

        driver.set(localDriver);

        if (!isHeadless) {
            getDriver().manage().window().maximize();
        }

        System.out.println("Final Capabilities: " + ((RemoteWebDriver) getDriver()).getCapabilities());
    }

    @AfterMethod
    public void tearDown() {
        if (getDriver() != null) {
            getDriver().quit();
            driver.remove();
        }
    }
}
*/

/*package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;
import listeners.TestListener;
import utils.ConfigReader;

@Listeners(TestListener.class)
public class BaseTest {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void setDriver(WebDriver driverInstance) {
        driver.set(driverInstance);
    }

    public static void removeDriver() {
        driver.remove();
    }

    @Parameters({"browser", "headless"})
    @BeforeMethod(alwaysRun = true)
    public void setUp(@Optional("") String browserParam,
                      @Optional("") String headlessParam) {
        String browserName = browserParam.isEmpty() ? ConfigReader.get("browser") : browserParam;
        String headless = headlessParam.isEmpty() ? ConfigReader.get("headless") : headlessParam;

        System.out.println("Browser: " + browserName + " | Headless flag: " + headless);

        boolean isHeadless = "true".equalsIgnoreCase(headless);
        WebDriver localDriver;

        switch (browserName.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                if (isHeadless) {
                    chromeOptions.addArguments("--headless=new", "--disable-gpu", "--window-size=1920,1080");
                }
                localDriver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (isHeadless) {
                    firefoxOptions.addArguments("--headless", "--width=1920", "--height=1080");
                }
                localDriver = new FirefoxDriver(firefoxOptions);
                break;

            case "ie":
                WebDriverManager.iedriver().setup();
                localDriver = new InternetExplorerDriver();
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                localDriver = new EdgeDriver();
                break;

            default:
                throw new IllegalArgumentException("Browser not supported: " + browserName);
        }

        setDriver(localDriver);

        if (!isHeadless) {
            getDriver().manage().window().maximize();
        }

        System.out.println("Final Capabilities: " + ((RemoteWebDriver) getDriver()).getCapabilities());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (getDriver() != null) {
            getDriver().quit();
            removeDriver();
        }
    }
} */

package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.*;
import listeners.TestListener;
import utils.ConfigReader;

import java.time.Duration;

@Listeners(TestListener.class)
public class BaseTest {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void setDriver(WebDriver driverInstance) {
        driver.set(driverInstance);
    }

    public static void removeDriver() {
        driver.remove();
    }

    @Parameters({"browser", "headless"})
    @BeforeMethod(alwaysRun = true)
    public void setUp(@Optional("") String browserParam,
                      @Optional("") String headlessParam) {
        String browserName = browserParam.isEmpty() ? ConfigReader.get("browser") : browserParam;
        String headless = headlessParam.isEmpty() ? ConfigReader.get("headless") : headlessParam;

        System.out.println("Browser: " + browserName + " | Headless flag: " + headless);

        boolean isHeadless = "true".equalsIgnoreCase(headless);
        WebDriver localDriver;

        switch (browserName.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();

                // Linux stability flags
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--remote-allow-origins=*");

                // Page load strategy fix
                chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);

                if (isHeadless) {
                    chromeOptions.addArguments("--headless=new", "--disable-gpu", "--window-size=1920,1080");
                }

                localDriver = new ChromeDriver(chromeOptions);
                localDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();

                firefoxOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);

                if (isHeadless) {
                    firefoxOptions.addArguments("--headless", "--width=1920", "--height=1080");
                }

                localDriver = new FirefoxDriver(firefoxOptions);
                localDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
                break;

            case "ie":
                WebDriverManager.iedriver().setup();
                localDriver = new InternetExplorerDriver();
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                localDriver = new EdgeDriver();
                break;

            default:
                throw new IllegalArgumentException("Browser not supported: " + browserName);
        }

        setDriver(localDriver);

        if (!isHeadless) {
            getDriver().manage().window().maximize();
        }

        System.out.println("Final Capabilities: " + ((RemoteWebDriver) getDriver()).getCapabilities());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (getDriver() != null) {
            getDriver().quit();
            removeDriver();
        }
    }

    // Global PageFactory initializer
    public <T> T initPage(Class<T> pageClass) {
        return PageFactory.initElements(getDriver(), pageClass);
    }
}

