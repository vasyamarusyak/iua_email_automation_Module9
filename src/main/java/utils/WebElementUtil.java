package utils;

import com.codeborne.selenide.SelenideElement;
import lombok.experimental.UtilityClass;
import java.time.Duration;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Configuration.timeout;
import static java.time.Duration.ofMillis;

public class WebElementUtil {
    public static boolean isDisplayed(SelenideElement element) {
        return isDisplayed(element, ofMillis(timeout));
    }

    public static boolean isDisplayed(SelenideElement element, Duration timeout) {
        try {
            return element
                    .shouldBe(visible, timeout)
                    .isDisplayed();
        } catch (AssertionError error) {
            return false;
        }
    }
}