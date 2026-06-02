package utils;

import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.clickable;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Configuration.timeout;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static java.time.Duration.ofMillis;

public class WebElementUtil {
    public static boolean isDisplayed(SelenideElement element) {
        return isDisplayed(element, ofMillis(timeout));
    }

    public static boolean isDisplayed(SelenideElement element, Duration timeout) {
        try {
            return element.shouldBe(visible, timeout).isDisplayed();
        } catch (AssertionError error) {
            return false;
        }
    }

    public static void highlightElementAndClick(SelenideElement element) {
        executeJavaScript("arguments[0].style.border='3px solid red'", element);
        element.shouldBe(clickable).click();
    }

    public static void highlightElementAndSendKeys(SelenideElement element, CharSequence... keysToSend) {
        executeJavaScript("arguments[0].style.border='3px solid red'", element);

        element.shouldBe(visible).sendKeys(keysToSend);
    }
}