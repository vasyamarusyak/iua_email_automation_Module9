package tests;

import config.FrameworkConfig;
import models.UserCredentials;
import org.testng.annotations.Test;
import pages.LoginPage;

import static org.assertj.core.api.Assertions.assertThat;

public class UserAccessTest extends BaseTest{
    private final UserCredentials userCredentials = FrameworkConfig.getUserCredentials();

    @Test
    public void userCanLoginToIUAPost() {
        var inboxPage = new LoginPage().login(userCredentials);

        assertThat(inboxPage.isLoginSuccessful())
                .as("Login failed")
                .isTrue();

    }
}
