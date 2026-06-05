package config;

import lombok.experimental.UtilityClass;
import models.UserCredentials;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

@UtilityClass
public final class FrameworkConfig {
    private static final String DEFAULT_ENV = "qa";
    private static final String ENV_SYSTEM_PROPERTY = "env";
    private static final String BROWSER_SYSTEM_PROPERTY = "browser";
    private static final String DEFAULT_BROWSER = "chrome";
    private static final String BROWSER_VERSION_SYSTEM_PROPERTY = "browser.version";
    private static final String DEFAULT_BROWSER_VERSION = "148.0.7778.217";
    private static final String HEADLESS_SYSTEM_PROPERTY = "headless";
    private static final Properties PROPERTIES = loadPropertyFile(resolveEnvironment(DEFAULT_ENV));

    public static String getEnvironment() {
        return resolveEnvironment(DEFAULT_ENV);
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

    public static String getRecipient() {
        return getRequiredProperty("mail.recipient");
    }

    public static String getBrowserName() {
        String browserFromSystem = System.getProperty(
                BROWSER_SYSTEM_PROPERTY,
                PROPERTIES.getProperty(BROWSER_SYSTEM_PROPERTY)
        );
        if (hasText(browserFromSystem)) {
            return browserFromSystem;
        }
        return getProperty(BROWSER_SYSTEM_PROPERTY, DEFAULT_BROWSER);
    }

    public static String getBrowserVersion() {
        String browserVersionFromSystem = System.getProperty(
                BROWSER_VERSION_SYSTEM_PROPERTY,
                PROPERTIES.getProperty(BROWSER_VERSION_SYSTEM_PROPERTY)
        );
        if (hasText(browserVersionFromSystem)) {
            return browserVersionFromSystem;
        }
        return getProperty(BROWSER_VERSION_SYSTEM_PROPERTY, DEFAULT_BROWSER_VERSION);
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(System.getProperty(
                HEADLESS_SYSTEM_PROPERTY,
                getProperty("headless", "true")
        ));
    }

    private static String resolveEnvironment(String environment) {
        return System.getProperty(ENV_SYSTEM_PROPERTY, environment)
                .trim()
                .toLowerCase(Locale.ROOT);
    }

    private static Properties loadPropertyFile(String propertyFile) {
        String resourcePath = "env/" + propertyFile + ".properties";
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
