/*package reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            String reportPath = System.getProperty("user.dir") + "/extent-reports/extent-report.html";
            ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
            reporter.config().setReportName("Web Elements Automation Report");
            reporter.config().setDocumentTitle("Automation Practice");

            extent = new ExtentReports();
            extent.attachReporter(reporter);
            extent.setSystemInfo("Tester", "Vikas Chaturvedi");
            extent.setSystemInfo("Browser", "Chrome");
            extent.setSystemInfo("Testing Environment", "QA");  // change to Dev/Prod if needed
            extent.setSystemInfo("Operating System", System.getProperty("os.name"));
            extent.setSystemInfo("OS Version", System.getProperty("os.version"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        }
        return extent;
    }
} */

package reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import utils.ConfigReader;

public class ExtentManager {
    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            String reportPath = System.getProperty("user.dir") + "/extent-reports/extent-report.html";
            ExtentSparkReporter reporter = new ExtentSparkReporter(reportPath);
            reporter.config().setReportName("Web Elements Automation Report");
            reporter.config().setDocumentTitle("Automation Practice");

            extent = new ExtentReports();
            extent.attachReporter(reporter);

            // System Info from config.properties
            extent.setSystemInfo("Tester", ConfigReader.get("tester"));
            extent.setSystemInfo("Browser", ConfigReader.get("browser"));
            extent.setSystemInfo("Testing Environment", ConfigReader.get("environment"));
            extent.setSystemInfo("Operating System", ConfigReader.get("os"));
            extent.setSystemInfo("Java Version", ConfigReader.get("java.version"));
        }
        return extent;
    }
}

