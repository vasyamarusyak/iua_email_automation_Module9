package tests;

import static org.assertj.core.api.Assertions.assertThat;
import org.testng.annotations.Test;
import pages.LoginPage;

public class MailTest extends BaseTest {

    private static final String EMAIL = "";
    private static final String PASSWORD = "";
    private static final String RECEIVER = "automation" + System.currentTimeMillis() + "@i.ua";
    private static final String SUBJECT = "Test Subject " + System.currentTimeMillis();
    private static final String BODY = "Test Body";

    @Test
    public void mailTest_01() {
        var inboxPage = new LoginPage().login(EMAIL, PASSWORD);

        assertThat(inboxPage.isLoginSuccessful())
                .as("Login failed")
                .isTrue();

        inboxPage
                .clickCreateLetter()
                .fillEmail(RECEIVER, SUBJECT, BODY)
                .saveEmailInDrafts()
                .openDraftsFolder();

        assertThat(inboxPage.isDraftPresent(SUBJECT))
                .as("Draft email is not present in Drafts folder")
                .isTrue();

        inboxPage
                .openDraftBySubject(SUBJECT)
                .sendEmail();

        assertThat(inboxPage.isEmailSent())
                .as("Email is not send successfully")
                .isTrue();

        inboxPage.openDraftsFolder();

        assertThat(inboxPage.isDraftPresent(SUBJECT))
                .as("Draft email shouldn't be present in Draft folder after sending")
                .isFalse();;

        inboxPage.openSentFolder();

        assertThat(inboxPage.isSentEmailPresent(SUBJECT))
                .as("Email is not present in Sent folder")
                .isTrue();

        var loginPage = inboxPage.logOut();

        assertThat(loginPage.isLoginMenuPresent())
                .as("Login menu is not present on ligin page")
                .isTrue();

    }
}
