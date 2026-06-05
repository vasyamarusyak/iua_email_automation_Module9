package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.clickable;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static java.lang.String.format;
import static java.time.Duration.ofSeconds;
import static utils.WebElementUtil.highlightElementAndClick;
import static utils.WebElementUtil.isDisplayed;

public class InboxPage extends BasePage {
    private static final String DRAFT_BY_SUBJECT_XPATH = "//span[@class='sbj' and @title = '%s']";
    private static final String SENT_BY_SUBJECT_XPATH = "//a[contains(@href,'/sent-mail')]//span[text()= '%s']";
    private final SelenideElement letterSuccesfullySentLabel = $x("//div[@class='content clear' and text()='Лист успішно відправлено адресатам']");
    private final SelenideElement draftsFolder = $x("//a[contains(@href,'drafts')]");
    private final SelenideElement sentFolder = $x("//a[contains(@href,'sent')]");
    private final SelenideElement createLetterButton = $("p.make_message > a");
    private final SelenideElement gearButton = $x("//span[@title = 'Налаштування']");
    private final SelenideElement logoutButton = $x("//a[contains(@href,'logout')]");

    public boolean isLoginSuccessful() {
        return isDisplayed($x("//img[@class= 'avatar-s']"), ofSeconds(5));
    }

    public InboxPage openDraftsFolder() {
        logger.info("[ACTION] Open draft folder");
        highlightElementAndClick(draftsFolder);

        return this;
    }

    public InboxPage openSentFolder() {
        highlightElementAndClick(sentFolder);

        return this;
    }

    public CreateEmailPage clickCreateLetter() {
        logger.info("[ACTION] Clicked create letter");
        highlightElementAndClick(createLetterButton);

        return new CreateEmailPage();
    }

    public CreateEmailPage openDraftBySubject(String subject) {
        logger.info("[ACTION] Open draft by subject '{}'", subject);
        $x(format(DRAFT_BY_SUBJECT_XPATH, subject))
                .shouldBe(clickable)
                .click();

        return new CreateEmailPage();
    }

    public boolean isDraftPresent(String subject) {
        logger.debug("Checking that draft '{}' is present", subject);
        return isDisplayed($x(format(DRAFT_BY_SUBJECT_XPATH, subject)), ofSeconds(5));
    }

    public boolean isEmailSent() {
        logger.debug("Checking that email is sent");
        return isDisplayed(letterSuccesfullySentLabel, ofSeconds(10));
    }

    public boolean isSentEmailPresent(String subject) {
        logger.debug("Checking that sent email '{}' is present", subject);
        return isDisplayed($x(format(SENT_BY_SUBJECT_XPATH, subject)), ofSeconds(5));
    }

    public LoginPage logOut() {
        highlightElementAndClick(gearButton);
        highlightElementAndClick(logoutButton);
        logger.info("[ACTION] User is logged out");

        return new LoginPage();
    }
}
