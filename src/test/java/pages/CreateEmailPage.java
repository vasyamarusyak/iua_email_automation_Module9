package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.clickable;
import static com.codeborne.selenide.Selenide.$x;

public class CreateEmailPage {
    private final SelenideElement receiverField = $x("//textarea[@id='to']");
    private final SelenideElement subjectField = $x("//*[@name='subject']");
    private final SelenideElement bodyField = $x("//*[@id='text']");
    private final SelenideElement sendButton = $x("//input[@name='send']");
    private final SelenideElement saveInDraftsButton = $x("//input[@name='save_in_drafts']");

    public CreateEmailPage fillEmail(String receiver, String subject, String body) {
        receiverField.shouldBe(clickable);
        receiverField.click();
        receiverField.sendKeys(receiver);
        receiverField.pressTab();

        subjectField.shouldBe(clickable);
        subjectField.click();
        subjectField.clear();
        subjectField.sendKeys(subject);
        receiverField.pressTab();

        bodyField
                .shouldBe(clickable)
                .click();
        bodyField.sendKeys(body);

        return this;
    }

    public InboxPage sendEmail() {
        sendButton
                .shouldBe(clickable)
                .click();

        return new InboxPage();
    }

    public InboxPage saveEmailInDrafts() {
        saveInDraftsButton
                .shouldBe(clickable)
                .click();

        return new InboxPage();
    }
}

