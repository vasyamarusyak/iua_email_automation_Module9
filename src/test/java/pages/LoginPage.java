package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.clickable;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static java.time.Duration.ofSeconds;
import static utils.WebElementUtil.isDisplayed;

public class LoginPage {
    private final SelenideElement loginInput = $x("//input[@name='login']");
    private final SelenideElement passwordInput = $x("//input[@name='pass']");
    private final SelenideElement loginButton = $x("//input[@type='submit' and @value= 'Увійти']");

    public InboxPage login(String username, String password) {
        loginInput.shouldBe(visible).sendKeys(username);
        passwordInput.shouldBe(visible).sendKeys(password);
        loginButton.shouldBe(clickable).click();

        return new InboxPage();
    }

    public boolean isLoginMenuPresent() {
        return isDisplayed(loginInput, ofSeconds(10));
    }



}
