/*package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ScreenshotUtils {

    public static String captureScreenshot(WebDriver driver, String testName) {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String destPath = System.getProperty("user.dir") + "/extent-reports/screenshots/" + testName + ".png";
        try {
            Files.createDirectories(Paths.get(System.getProperty("user.dir") + "/extent-reports/screenshots/"));
            //Files.copy(srcFile.toPath(), Paths.get(destPath));
            Files.copy(srcFile.toPath(), Paths.get(destPath), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return destPath;
    }
}
*/

/*package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ScreenshotUtils {

    private static final String REPORT_FOLDER = "target/extent-reports/screenshots/";

    public static String captureScreenshot(WebDriver driver, String fileName) {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String destPath = REPORT_FOLDER + fileName + ".png";

        try {
            Files.createDirectories(Paths.get(REPORT_FOLDER));
            Files.copy(srcFile.toPath(), Paths.get(destPath), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return relative path for Extent report
        return "./screenshots/" + fileName + ".png";
    }
}
  */

package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ScreenshotUtils {

    // Screenshots folder under extent-reports
    private static final String REPORT_FOLDER = "extent-reports/screenshots/";

    /**
     * Captures a screenshot and overwrites if already exists.
     * @param driver WebDriver instance
     * @param fileName screenshot file name (without extension)
     * @return relative path for Extent Report
     */
    public static String captureScreenshot(WebDriver driver, String fileName) {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        String destPath = System.getProperty("user.dir") + "/" + REPORT_FOLDER + fileName + ".png";

        try {
            Files.createDirectories(Paths.get(System.getProperty("user.dir") + "/" + REPORT_FOLDER));
            // Always overwrite existing screenshot
            Files.copy(srcFile.toPath(), Paths.get(destPath), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return relative path for report linking
        return "./screenshots/" + fileName + ".png";
    }
}

