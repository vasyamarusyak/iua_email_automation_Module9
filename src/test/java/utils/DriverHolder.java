package utils;

import org.openqa.selenium.WebDriver;

public final class DriverHolder {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverHolder() {
    }

    public static void setDriver(WebDriver driver) {
        DRIVER.set(driver);
    }

    public static WebDriver getDriver() {
        return DRIVER.get();
    }

    public static void removeDriver() {
        DRIVER.remove();
    }
}
