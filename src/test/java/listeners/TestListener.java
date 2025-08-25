package listeners;

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
