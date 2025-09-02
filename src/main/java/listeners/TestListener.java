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
