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

/* package utils;

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
 /*   public static String captureScreenshot(WebDriver driver, String fileName) {
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

*/

/*package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtils {

    // Screenshots folder under extent-reports
    private static final String REPORT_FOLDER = "extent-reports/screenshots/";

    
    // * Captures a screenshot with timestamp to avoid overwriting in parallel runs.
    // * @param driver WebDriver instance
     //* @param fileName base screenshot file name (without extension)
     //* @return relative path for Extent Report
     
    public static String captureScreenshot(WebDriver driver, String fileName) {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        // Unique timestamp
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());

        String destPath = System.getProperty("user.dir") + "/" + REPORT_FOLDER + fileName + "_" + timestamp + ".png";

        try {
            Files.createDirectories(Paths.get(System.getProperty("user.dir") + "/" + REPORT_FOLDER));
            Files.copy(srcFile.toPath(), Paths.get(destPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return relative path for report linking
        return "./screenshots/" + fileName + "_" + timestamp + ".png";
    }
}

*/

package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtils {

    private static final String REPORT_FOLDER = "extent-reports/screenshots/";

    /**
     * Captures a normal viewport screenshot (visible part of the page)
     * @param driver WebDriver instance
     * @param fileName base screenshot file name
     * @return relative path for Extent Report
     */
    public static String captureViewportScreenshot(WebDriver driver, String fileName) {
        try {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());
            String destPath = System.getProperty("user.dir") + "/" + REPORT_FOLDER + fileName + "_" + timestamp + ".png";

            Files.createDirectories(Paths.get(System.getProperty("user.dir") + "/" + REPORT_FOLDER));
            Files.copy(srcFile.toPath(), Paths.get(destPath));

            return "./screenshots/" + fileName + "_" + timestamp + ".png";

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Captures a full-page screenshot using AShot (scrolls and stitches)
     * @param driver WebDriver instance
     * @param fileName base screenshot file name
     * @return relative path for Extent Report
     */
    public static String captureFullPageScreenshot(WebDriver driver, String fileName) {
        try {
            Screenshot screenshot = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(1000)) // scroll timeout 1s
                    .takeScreenshot(driver);

            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());
            String destPath = System.getProperty("user.dir") + "/" + REPORT_FOLDER + fileName + "_" + timestamp + ".png";

            Files.createDirectories(Paths.get(System.getProperty("user.dir") + "/" + REPORT_FOLDER));
            ImageIO.write(screenshot.getImage(), "PNG", new File(destPath));

            return "./screenshots/" + fileName + "_" + timestamp + ".png";

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

