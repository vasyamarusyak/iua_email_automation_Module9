package config;

import models.UserCredentials;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Locale;
import java.util.Properties;

public final class FrameworkConfig {
    private static final String DEFAULT_ENV = "qa";
    private static final String ENV_SYSTEM_PROPERTY = "env";
    private static final String BROWSER_SYSTEM_PROPERTY = "browser";
    private static final String HEADLESS_SYSTEM_PROPERTY = "headless";
    private static final String DEFAULT_TIMEOUT_KEY = "default.timeout.seconds";
    private static final Properties PROPERTIES = loadEnvironmentProperties(resolveEnvironment());

    private FrameworkConfig() {
    }

    public static String getEnvironment() {
        return resolveEnvironment();
    }

    public static String getBaseUrl() {
        return getRequiredProperty("base.url");
    }

    public static UserCredentials getUserCredentials() {
        return new UserCredentials(
                getRequiredProperty("user.email"),
                getRequiredProperty("user.password")
        );
    }

    public static String getDefaultRecipient() {
        return getRequiredProperty("mail.recipient");
    }

    public static String getBrowserName() {
        String browserFromSystem = getRequiredProperty("browser.default");
       // String browserFromSystem = System.getProperty(BROWSER_SYSTEM_PROPERTY);
        if (hasText(browserFromSystem)) {
            return browserFromSystem;
        }
        return getProperty("browser.default", "CHROME");
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(System.getProperty(
                HEADLESS_SYSTEM_PROPERTY,
                getProperty("headless", "false")
        ));
    }

    public static Duration getDefaultTimeout() {
        long timeoutInSeconds = Long.parseLong(getProperty(DEFAULT_TIMEOUT_KEY, "10"));
        return Duration.ofSeconds(timeoutInSeconds);
    }

    private static String resolveEnvironment() {
        return System.getProperty(ENV_SYSTEM_PROPERTY, DEFAULT_ENV)
                .trim()
                .toLowerCase(Locale.ROOT);
    }

    private static Properties loadEnvironmentProperties(String environment) {
        String resourcePath = "env/" + environment + ".properties";
        try (InputStream inputStream = FrameworkConfig.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Environment config was not found: " + resourcePath);
            }
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties;
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to load environment config: " + resourcePath, exception);
        }
    }

    private static String getRequiredProperty(String key) {
        String value = PROPERTIES.getProperty(key);
        if (!hasText(value)) {
            throw new IllegalStateException("Required property is missing: " + key);
        }
        return value.trim();
    }

    private static String getProperty(String key, String defaultValue) {
        return PROPERTIES.getProperty(key, defaultValue).trim();
    }

    private static boolean hasText(String value) {
        return value != null && !value.isBlank();
    }
}
