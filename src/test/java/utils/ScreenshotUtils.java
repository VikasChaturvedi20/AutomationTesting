package utils;

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
            Files.copy(srcFile.toPath(), Paths.get(destPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return destPath;
    }
}
