/*package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;
import listeners.TestListener;
import utils.ConfigReader;

import java.net.MalformedURLException;
import java.net.URL;

@Listeners(TestListener.class)
public class BaseTest1 {

    public WebDriver driver;

    @Parameters({"browser", "headless"})
    @BeforeMethod
    public void setUp(@Optional("") String browserParam,
                      @Optional("") String headlessParam) {

        // 1️⃣ Read from TestNG or fallback to config.properties
        String browserName = browserParam.isEmpty() ? ConfigReader.get("browser") : browserParam;
        String headless = headlessParam.isEmpty() ? ConfigReader.get("headless") : headlessParam;

        boolean isHeadless = "true".equalsIgnoreCase(headless);

        // 2️⃣ Read Grid settings from config.properties
        boolean useGrid = "true".equalsIgnoreCase(ConfigReader.get("grid"));
        String gridUrl = ConfigReader.get("grid.url");

        System.out.println("[INFO] Browser: " + browserName + " | Headless: " + isHeadless +
                " | Grid: " + useGrid + " | Grid URL: " + gridUrl);

        try {
            if (useGrid) {
                // ===== Remote WebDriver =====
                switch (browserName.toLowerCase()) {
                    case "chrome":
                        ChromeOptions chromeOptions = new ChromeOptions();
                        if (isHeadless) {
                            chromeOptions.addArguments("--headless=new");
                            chromeOptions.addArguments("--disable-gpu");
                            chromeOptions.addArguments("--window-size=1920,1080");
                        }
                        driver = new RemoteWebDriver(new URL(gridUrl), chromeOptions);
                        break;

                    case "firefox":
                        FirefoxOptions firefoxOptions = new FirefoxOptions();
                        if (isHeadless) {
                            firefoxOptions.addArguments("--headless");
                            firefoxOptions.addArguments("--width=1920");
                            firefoxOptions.addArguments("--height=1080");
                        }
                        driver = new RemoteWebDriver(new URL(gridUrl), firefoxOptions);
                        break;

                    case "edge":
                        EdgeOptions edgeOptions = new EdgeOptions();
                        driver = new RemoteWebDriver(new URL(gridUrl), edgeOptions);
                        break;

                    default:
                        throw new IllegalArgumentException("Browser not supported for Grid: " + browserName);
                }

            } else {
                // ===== Local WebDriver =====
                switch (browserName.toLowerCase()) {
                    case "chrome":
                        WebDriverManager.chromedriver().setup();
                        ChromeOptions chromeOptions = new ChromeOptions();
                        if (isHeadless) {
                            chromeOptions.addArguments("--headless=new");
                            chromeOptions.addArguments("--disable-gpu");
                            chromeOptions.addArguments("--window-size=1920,1080");
                        }
                        driver = new org.openqa.selenium.chrome.ChromeDriver(chromeOptions);
                        break;

                    case "firefox":
                        WebDriverManager.firefoxdriver().setup();
                        FirefoxOptions firefoxOptions = new FirefoxOptions();
                        if (isHeadless) {
                            firefoxOptions.addArguments("--headless");
                            firefoxOptions.addArguments("--width=1920");
                            firefoxOptions.addArguments("--height=1080");
                        }
                        driver = new org.openqa.selenium.firefox.FirefoxDriver(firefoxOptions);
                        break;

                    case "edge":
                        WebDriverManager.edgedriver().setup();
                        driver = new org.openqa.selenium.edge.EdgeDriver();
                        break;

                    default:
                        throw new IllegalArgumentException("Browser not supported locally: " + browserName);
                }
            }

            // Only maximize if NOT headless
            if (!isHeadless) {
                driver.manage().window().maximize();
            }

            // Debug: print capabilities
            System.out.println("[INFO] Final Capabilities: " + ((RemoteWebDriver) driver).getCapabilities());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
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
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;
import listeners.TestListener;
import utils.ConfigReader;

import java.net.MalformedURLException;
import java.net.URL;

@Listeners(TestListener.class)
public class BaseTest1 {

    private static ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<>();

    public WebDriver getDriver() {
        return driver.get();
    }

    @Parameters({"browser", "headless"})
    @BeforeMethod
    public void setUp(@Optional("") String browserParam,
                      @Optional("") String headlessParam) throws MalformedURLException {

        String browserName = browserParam.isEmpty() ? ConfigReader.get("browser") : browserParam;
        boolean isHeadless = "true".equalsIgnoreCase(
                headlessParam.isEmpty() ? ConfigReader.get("headless") : headlessParam
        );

        boolean useGrid = "true".equalsIgnoreCase(ConfigReader.get("grid"));
        String gridUrl = ConfigReader.get("grid.url");

        System.out.println("[INFO] Browser: " + browserName +
                " | Headless: " + isHeadless +
                " | Grid: " + useGrid +
                " | Grid URL: " + gridUrl);

        if (useGrid) {
            switch (browserName.toLowerCase()) {
                case "chrome":
                    ChromeOptions chromeOptions = new ChromeOptions();
                    if (isHeadless) {
                        chromeOptions.addArguments("--headless=new");
                        chromeOptions.addArguments("--disable-gpu");
                        chromeOptions.addArguments("--window-size=1920,1080");
                    }
                    driver.set(new RemoteWebDriver(new URL(gridUrl), chromeOptions));
                    break;

                case "firefox":
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    if (isHeadless) {
                        firefoxOptions.addArguments("--headless");
                        firefoxOptions.addArguments("--width=1920");
                        firefoxOptions.addArguments("--height=1080");
                    }
                    driver.set(new RemoteWebDriver(new URL(gridUrl), firefoxOptions));
                    break;

                case "edge":
                    EdgeOptions edgeOptions = new EdgeOptions();
                    if (isHeadless) {
                        edgeOptions.addArguments("--headless");
                        edgeOptions.addArguments("--disable-gpu");
                        edgeOptions.addArguments("--window-size=1920,1080");
                    }
                    driver.set(new RemoteWebDriver(new URL(gridUrl), edgeOptions));
                    break;

                default:
                    throw new IllegalArgumentException("Browser not supported for Grid: " + browserName);
            }
        } else {
            throw new RuntimeException("Local execution not implemented in this example.");
        }

        if (!isHeadless) {
            getDriver().manage().window().maximize();
        }

        System.out.println("[INFO] Final Capabilities: " + ((RemoteWebDriver) getDriver()).getCapabilities());

    }

    @AfterMethod
    public void tearDown() {
        if (getDriver() != null) {
            getDriver().quit();
        }
    }
}
