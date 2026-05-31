package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ScreenshotUtils {

    private static final DateTimeFormatter FILE_NAME_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    private ScreenshotUtils() {
    }

    public static Path captureFailureScreenshot(WebDriver driver, String testName) {
        if (!(driver instanceof TakesScreenshot screenshotDriver)) {
            throw new IllegalStateException("Driver does not support screenshots");
        }

        try {
            Path screenshotDirectory = Path.of(
                    "target",
                    "screenshots",
                    LocalDate.now().toString()
            );
            Files.createDirectories(screenshotDirectory);

            Path targetFile = screenshotDirectory.resolve(
                    sanitize(testName) + "_" + FILE_NAME_FORMAT.format(LocalDateTime.now()) + ".png"
            );

            Files.copy(
                    screenshotDriver.getScreenshotAs(OutputType.FILE).toPath(),
                    targetFile,
                    StandardCopyOption.REPLACE_EXISTING
            );

            return targetFile.toAbsolutePath();
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to save screenshot for test: " + testName, exception);
        }
    }

    private static String sanitize(String value) {
        return value.replaceAll("[^a-zA-Z0-9._-]", "_");
    }
}
