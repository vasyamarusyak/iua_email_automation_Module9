package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import config.FrameworkConfig;
import io.qameta.allure.selenide.AllureSelenide;
import io.qameta.allure.testng.AllureTestNg;
import listeners.TestExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import static com.codeborne.selenide.Configuration.browser;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static java.time.Duration.ofSeconds;

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
    public void setUpBrowser(ITestContext context) {
        browser = FrameworkConfig.getBrowserName();
        Configuration.timeout = ofSeconds(30).toMillis();

        LOGGER.info(
                "[ACTION] Preparing driver for browser='{}', env='{}', headless={}",
                browser,
                FrameworkConfig.getEnvironment(),
                FrameworkConfig.isHeadless());

       open(BASE_URL);
       LOGGER.debug("Opened base URL {}", BASE_URL);

        getWebDriver()
                .manage()
                .window()
                .maximize();
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        WebDriverRunner.closeWebDriver();
        LOGGER.info("[ACTION] Closing browser session");
    }
}
