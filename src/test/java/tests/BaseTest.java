package tests;
import listeners.TestExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static java.time.Duration.ofSeconds;

@Listeners(TestExecutionListener.class)
public class BaseTest {
    private static final String BASE_URL = "https://www.i.ua/";
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseTest.class);

    @BeforeSuite
    public void configureLogging() {
        java.util.logging.LogManager.getLogManager().reset();
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        LOGGER.debug("SLF4J bridge for java.util.logging is configured");
    }

    @BeforeMethod
    public void setUpBrowser() {
        Configuration.browser = "chrome";
        Configuration.timeout = ofSeconds(90).toMillis();
        Configuration.pageLoadTimeout = 60000;
        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);

        open(BASE_URL);

        getWebDriver()
                .manage()
                .window()
                .maximize();
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        WebDriverRunner.closeWebDriver();
    }
}
