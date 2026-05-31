package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.clickable;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static java.lang.String.format;
import static java.time.Duration.ofSeconds;
import static utils.WebElementUtil.isDisplayed;

public class InboxPage {
    private static final String DRAFT_BY_SUBJECT_XPATH = "//span[@class='sbj' and @title = '%s']";
    private static final String SENT_BY_SUBJECT_XPATH = "//a[contains(@href,'/sent-mail')]//span[text()= '%s']";
    private final SelenideElement letterSuccesfullySentLabel = $x("//div[@class='content clear' and text()='Лист успішно відправлено адресатам']");
    private final SelenideElement draftsFolder = $x("//a[contains(@href,'drafts')]");
    private final SelenideElement sentFolder = $x("//a[contains(@href,'sent')]");
    private final SelenideElement createLetterButton = $("p.make_message > a");

    public boolean isLoginSuccessful() {
        return isDisplayed($x("//img[@class= 'avatar-s']"), ofSeconds(5));
    }

    public InboxPage openDraftsFolder() {
        draftsFolder
                .shouldBe(clickable)
                .click();

        return this;
    }

    public InboxPage openSentFolder() {
        sentFolder
                .shouldBe(clickable)
                .click();

        return this;
    }

    public CreateEmailPage clickCreateLetter() {
        createLetterButton
                .shouldBe(clickable)
                .click();

        return new CreateEmailPage();
    }

    public CreateEmailPage openDraftBySubject(String subject) {
        $x(format(DRAFT_BY_SUBJECT_XPATH, subject))
                .shouldBe(clickable)
                .click();

        return new CreateEmailPage();
    }

    public boolean isDraftPresent(String subject) {
        return isDisplayed($x(format(DRAFT_BY_SUBJECT_XPATH, subject)), ofSeconds(5));
    }

    public boolean isEmailSent() {
        return isDisplayed(letterSuccesfullySentLabel, ofSeconds(10));
    }

    public boolean isSentEmailPresent(String subject) {
        return isDisplayed($x(format(SENT_BY_SUBJECT_XPATH, subject)), ofSeconds(5));
    }

    public LoginPage logOut() {
        $x("//span[@title = 'Налаштування']").click();
        $x("//a[contains(@href,'logout')]").shouldBe(clickable).click();

        return new LoginPage();
    }
}
