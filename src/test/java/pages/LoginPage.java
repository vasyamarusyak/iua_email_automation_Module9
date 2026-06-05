package pages;

import com.codeborne.selenide.SelenideElement;
import models.UserCredentials;

import static com.codeborne.selenide.Selenide.$x;
import static java.time.Duration.ofSeconds;
import static utils.WebElementUtil.*;

public class LoginPage extends BasePage {
    private final SelenideElement loginInput = $x("//input[@name='login']");
    private final SelenideElement passwordInput = $x("//input[@name='pass']");
    private final SelenideElement loginButton = $x("//input[@type='submit' and @value= 'Увійти']");

    public InboxPage login(UserCredentials userCredentials) {
        highlightElementAndSendKeys(loginInput, userCredentials.getUsername());
        highlightElementAndSendKeys(passwordInput, userCredentials.getPassword());
        highlightElementAndClick(loginButton);
        logger.info("[ACTION] The user is logged in");

        return new InboxPage();
    }

    public boolean isLoginMenuPresent() {
        return isDisplayed(loginInput, ofSeconds(10));
    }
}
