package pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;
import static utils.WebElementUtil.highlightElementAndClick;
import static utils.WebElementUtil.highlightElementAndSendKeys;

public class CreateEmailPage extends BasePage{
    private final SelenideElement receiverField = $x("//textarea[@id='to']");
    private final SelenideElement subjectField = $x("//*[@name='subject']");
    private final SelenideElement bodyField = $x("//*[@id='text']");
    private final SelenideElement sendButton = $x("//input[@name='send']");
    private final SelenideElement saveInDraftsButton = $x("//input[@name='save_in_drafts']");

    public CreateEmailPage fillEmail(String receiver, String subject, String body) {
        highlightElementAndClick(receiverField);
        highlightElementAndSendKeys(receiverField, receiver);
        receiverField.pressTab();

        highlightElementAndClick(subjectField);
        subjectField.clear();
        highlightElementAndSendKeys(subjectField, subject);
        receiverField.pressTab();

        highlightElementAndClick(bodyField);
        highlightElementAndSendKeys(bodyField, body);

        return this;
    }

    public InboxPage sendEmail() {
        highlightElementAndClick(sendButton);

        return new InboxPage();
    }

    public InboxPage saveEmailInDrafts() {
        highlightElementAndClick(saveInDraftsButton);

        return new InboxPage();
    }
}

