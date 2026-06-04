package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import config.FrameworkConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.selenide.AllureSelenide;
import io.qameta.allure.testng.AllureTestNg;
import listeners.TestExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import static com.codeborne.selenide.Selenide.open;
import static config.FrameworkConfig.getBrowserName;
import static config.FrameworkConfig.getBrowserVersion;
import static io.vavr.API.*;

@Listeners({TestExecutionListener.class, AllureTestNg.class})
public class BaseTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseTest.class);
    private static final String BASE_URL = FrameworkConfig.getBaseUrl();

    @BeforeSuite
    public void configureLogging() {
        java.util.logging.LogManager.getLogManager().reset();
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        LOGGER.debug("SLF4J bridge for java.util.logging is configured");
    }

    @BeforeSuite
    public void configureAllure() {
        SelenideLogger.addListener(
                "allure",
                new AllureSelenide()
                        .screenshots(true)
                        .savePageSource(true)
        );
        LOGGER.debug("Allure is configured");
    }

    @BeforeMethod
    public void setUpBrowser() {
        Configuration.pageLoadTimeout = 120000; // 120 seconds
        Configuration.browserSize = "1920x1080";

        String browser = getBrowserName();
        Configuration.browser = browser.toLowerCase();

        Match(getBrowserName()).of(
                Case($("chrome"), () -> {
                    WebDriverManager.chromedriver()
                            .driverVersion(getBrowserVersion())
                            .setup();
                    return null;
                }),
                Case($("firefox"), () -> {
                    WebDriverManager.firefoxdriver()
                            .driverVersion(getBrowserVersion())
                            .setup();
                    return null;
                }),
                Case($(), () -> {
                    throw new IllegalArgumentException(
                            "Unsupported browser: " + browser);
                })
        );

        LOGGER.info(
                "[ACTION] Preparing driver for browser='{}', env='{}', headless={}",
                browser,
                FrameworkConfig.getEnvironment(),
                FrameworkConfig.isHeadless());

        open(BASE_URL);
        LOGGER.debug("Opened base URL {}", BASE_URL);
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        WebDriverRunner.closeWebDriver();
        LOGGER.info("[ACTION] Closing browser session");
    }
}
