package listeners;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.DriverHolder;
import utils.ScreenshotUtils;

import java.nio.file.Path;

public class TestExecutionListener implements ITestListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestExecutionListener.class);

    @Override
    public void onStart(ITestContext context) {
        LOGGER.info("[ACTION] Starting suite '{}'", context.getName());
    }

    @Override
    public void onTestStart(ITestResult result) {
        LOGGER.info("[ACTION] Starting test '{}'", result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LOGGER.info("[ACTION] Test '{}' finished successfully", result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LOGGER.error("Test '{}' failed", result.getMethod().getMethodName(), result.getThrowable());

        WebDriver driver = DriverHolder.getDriver();
        if (driver == null) {
            LOGGER.warn("Screenshot was skipped because no active WebDriver was found");
            return;
        }

        try {
            Path screenshotPath = ScreenshotUtils.captureFailureScreenshot(driver, result.getMethod().getMethodName());
            LOGGER.error("Failure screenshot saved to {}", screenshotPath);
        } catch (RuntimeException exception) {
            LOGGER.error("Failed to capture screenshot for test '{}'", result.getMethod().getMethodName(), exception);
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LOGGER.warn("Test '{}' was skipped", result.getMethod().getMethodName());
    }

    @Override
    public void onFinish(ITestContext context) {
        LOGGER.info("[ACTION] Finished suite '{}'", context.getName());
    }
}
