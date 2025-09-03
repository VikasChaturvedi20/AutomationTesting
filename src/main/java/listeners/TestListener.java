/*package listeners;

import com.aventstack.extentreports.Status;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import reports.ExtentManager;
import utils.ScreenshotUtils;
import base.BaseTest;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.ExtentReports;

public class TestListener implements ITestListener {

    private static ExtentReports extent = ExtentManager.getInstance();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String screenshotPath = ScreenshotUtils.captureScreenshot(((BaseTest) result.getInstance()).driver, result.getMethod().getMethodName());
        test.get().log(Status.PASS, "Test Passed");
        test.get().addScreenCaptureFromPath(screenshotPath);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String screenshotPath = ScreenshotUtils.captureScreenshot(((BaseTest) result.getInstance()).driver, result.getMethod().getMethodName());
        test.get().log(Status.FAIL, result.getThrowable());
        test.get().addScreenCaptureFromPath(screenshotPath);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String screenshotPath = ScreenshotUtils.captureScreenshot(((BaseTest) result.getInstance()).driver, result.getMethod().getMethodName());
        test.get().log(Status.SKIP, "Test Skipped");
        test.get().addScreenCaptureFromPath(screenshotPath);
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
*/

/*package listeners;

import com.aventstack.extentreports.Status;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import reports.ExtentManager;
import utils.ScreenshotUtils;
import base.BaseTest;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.ExtentReports;

public class TestListener implements ITestListener {

    private static ExtentReports extent = ExtentManager.getInstance();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public static ExtentTest getTest() {
        return test.get();
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String screenshotPath = ScreenshotUtils.captureScreenshot(
                ((BaseTest) result.getInstance()).driver,
                result.getMethod().getMethodName()
        );
        test.get().log(Status.PASS, "Test Passed")
              .addScreenCaptureFromPath(screenshotPath);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String screenshotPath = ScreenshotUtils.captureScreenshot(
                ((BaseTest) result.getInstance()).driver,
                result.getMethod().getMethodName()
        );
        test.get().log(Status.FAIL, result.getThrowable())
              .addScreenCaptureFromPath(screenshotPath);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String screenshotPath = ScreenshotUtils.captureScreenshot(
                ((BaseTest) result.getInstance()).driver,
                result.getMethod().getMethodName()
        );
        test.get().log(Status.SKIP, "Test Skipped")
              .addScreenCaptureFromPath(screenshotPath);
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
*/

/*package listeners;

import com.aventstack.extentreports.Status;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import reports.ExtentManager;
import utils.ScreenshotUtils;
import base.BaseTest;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.ExtentReports;

public class TestListener implements ITestListener {

    private static ExtentReports extent = ExtentManager.getInstance();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public static ExtentTest getTest() {
        return test.get();
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
        test.get().log(Status.INFO, "Starting test: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String screenshotPath = ScreenshotUtils.captureViewportScreenshot(
                ((BaseTest) result.getInstance()).driver,
                result.getMethod().getMethodName()
        );
        test.get().log(Status.PASS, "Test Passed")
                .addScreenCaptureFromPath(screenshotPath);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String screenshotPath = ScreenshotUtils.captureViewportScreenshot(
                ((BaseTest) result.getInstance()).driver,
                result.getMethod().getMethodName()
        );
        test.get().log(Status.FAIL, result.getThrowable())
                .addScreenCaptureFromPath(screenshotPath);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String screenshotPath = ScreenshotUtils.captureViewportScreenshot(
                ((BaseTest) result.getInstance()).driver,
                result.getMethod().getMethodName()
        );
        test.get().log(Status.SKIP, "Test Skipped")
                .addScreenCaptureFromPath(screenshotPath);
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}


*/

/*package listeners;

import com.aventstack.extentreports.Status;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import reports.ExtentManager;
import utils.ScreenshotUtils;
import base.BaseTest;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.ExtentReports;

public class TestListener implements ITestListener {

	private static ExtentReports extent = ExtentManager.getInstance();
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

	public static ExtentTest getTest() {
		return test.get();
	}

	@Override
	public void onTestStart(ITestResult result) {
		ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
		test.set(extentTest);
		test.get().log(Status.INFO, "Starting test: " + result.getMethod().getMethodName());
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		WebDriver driver = ((BaseTest) result.getInstance()).driver;

		// Capture viewport screenshot
		String viewportPath = ScreenshotUtils.captureViewportScreenshot(driver,
				result.getMethod().getMethodName() + "_Viewport");
		test.get().log(Status.PASS, "Test Passed - Viewport Screenshot").addScreenCaptureFromPath(viewportPath);

		// Capture full-page screenshot
		String fullPagePath = ScreenshotUtils.captureFullPageScreenshot(driver,
				result.getMethod().getMethodName() + "_FullPage");
		test.get().log(Status.PASS, "Test Passed - Full Page Screenshot").addScreenCaptureFromPath(fullPagePath);
	}

	@Override
	public void onTestFailure(ITestResult result) {
		WebDriver driver = ((BaseTest) result.getInstance()).driver;

		// Capture viewport screenshot
		String viewportPath = ScreenshotUtils.captureViewportScreenshot(driver,
				result.getMethod().getMethodName() + "_Viewport");
		test.get().log(Status.FAIL, result.getThrowable() + " - Viewport Screenshot")
				.addScreenCaptureFromPath(viewportPath);

		// Capture full-page screenshot
		String fullPagePath = ScreenshotUtils.captureFullPageScreenshot(driver,
				result.getMethod().getMethodName() + "_FullPage");
		test.get().log(Status.FAIL, "Full Page Screenshot").addScreenCaptureFromPath(fullPagePath);
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		WebDriver driver = ((BaseTest) result.getInstance()).driver;

		// Capture viewport screenshot
		String viewportPath = ScreenshotUtils.captureViewportScreenshot(driver,
				result.getMethod().getMethodName() + "_Viewport");
		test.get().log(Status.SKIP, "Test Skipped - Viewport Screenshot").addScreenCaptureFromPath(viewportPath);

		// Capture full-page screenshot
		String fullPagePath = ScreenshotUtils.captureFullPageScreenshot(driver,
				result.getMethod().getMethodName() + "_FullPage");
		test.get().log(Status.SKIP, "Full Page Screenshot").addScreenCaptureFromPath(fullPagePath);
	}

	@Override
	public void onFinish(ITestContext context) {
		extent.flush();
	}
}
*/

/*package listeners;

import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import reports.ExtentManager;
import utils.ScreenshotUtils;
import base.BaseTest;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.ExtentReports;

public class TestListener implements ITestListener {

    private static ExtentReports extent = ExtentManager.getInstance();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public static ExtentTest getTest() {
        return test.get();
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
        test.get().log(Status.INFO, "Starting test: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        WebDriver driver = BaseTest.getDriver();

        String viewportPath = ScreenshotUtils.captureViewportScreenshot(driver,
                result.getMethod().getMethodName() + "_Viewport");
        test.get().log(Status.PASS, "Test Passed - Viewport Screenshot").addScreenCaptureFromPath(viewportPath);

        String fullPagePath = ScreenshotUtils.captureFullPageScreenshot(driver,
                result.getMethod().getMethodName() + "_FullPage");
        test.get().log(Status.PASS, "Test Passed - Full Page Screenshot").addScreenCaptureFromPath(fullPagePath);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        WebDriver driver = BaseTest.getDriver();

        String viewportPath = ScreenshotUtils.captureViewportScreenshot(driver,
                result.getMethod().getMethodName() + "_Viewport");
        test.get().log(Status.FAIL, result.getThrowable() + " - Viewport Screenshot")
                .addScreenCaptureFromPath(viewportPath);

        String fullPagePath = ScreenshotUtils.captureFullPageScreenshot(driver,
                result.getMethod().getMethodName() + "_FullPage");
        test.get().log(Status.FAIL, "Full Page Screenshot").addScreenCaptureFromPath(fullPagePath);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        WebDriver driver = BaseTest.getDriver();

        String viewportPath = ScreenshotUtils.captureViewportScreenshot(driver,
                result.getMethod().getMethodName() + "_Viewport");
        test.get().log(Status.SKIP, "Test Skipped - Viewport Screenshot").addScreenCaptureFromPath(viewportPath);

        String fullPagePath = ScreenshotUtils.captureFullPageScreenshot(driver,
                result.getMethod().getMethodName() + "_FullPage");
        test.get().log(Status.SKIP, "Full Page Screenshot").addScreenCaptureFromPath(fullPagePath);
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
*/


package listeners;

import com.aventstack.extentreports.Status;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import reports.ExtentManager;
import utils.ScreenshotUtils;
import base.BaseTest;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.ExtentReports;

public class TestListener implements ITestListener {

    private static final ExtentReports extent = ExtentManager.getInstance();
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    public static ExtentTest getTest() {
        return test.get();
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
        getTest().log(Status.INFO, "Starting test: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        WebDriver driver = BaseTest.getDriver();
        getTest().log(Status.PASS, "Test Passed ✅");

        attachScreenshots(driver, result, Status.PASS);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        WebDriver driver = BaseTest.getDriver();
        getTest().log(Status.FAIL, "Test Failed ❌");
        getTest().fail(result.getThrowable());

        attachScreenshots(driver, result, Status.FAIL);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        getTest().log(Status.SKIP, "Test Skipped ⚠️ - " + result.getThrowable());
        // Don’t attach screenshots here because driver may not be initialized
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }

    // 🔹 Helper method to attach viewport + full-page screenshots safely
    private void attachScreenshots(WebDriver driver, ITestResult result, Status status) {
        if (driver == null) {
            getTest().log(Status.WARNING, "WebDriver is null, skipping screenshots.");
            return;
        }

        String methodName = result.getMethod().getMethodName();

        String viewportPath = ScreenshotUtils.captureViewportScreenshot(driver, methodName + "_Viewport");
        if (viewportPath != null) {
            getTest().log(status, "Viewport Screenshot").addScreenCaptureFromPath(viewportPath);
        }

        String fullPagePath = ScreenshotUtils.captureFullPageScreenshot(driver, methodName + "_FullPage");
        if (fullPagePath != null) {
            getTest().log(status, "Full Page Screenshot").addScreenCaptureFromPath(fullPagePath);
        }
    }
}
