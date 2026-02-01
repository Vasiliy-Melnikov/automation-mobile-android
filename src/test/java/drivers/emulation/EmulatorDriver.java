package drivers.emulation;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.MutableCapabilities;

import java.net.URL;

public class EmulatorDriver {

    public static AndroidDriver create() {
        try {
            MutableCapabilities caps = new MutableCapabilities();
            caps.setCapability("platformName", "Android");
            caps.setCapability("appium:automationName", "UiAutomator2");
            caps.setCapability("appium:udid", System.getProperty("udid", "emulator-5554"));
            caps.setCapability("appium:deviceName", "Android Emulator");
            caps.setCapability("appium:appPackage", "org.wikipedia.alpha");
            caps.setCapability("appium:appActivity", "org.wikipedia.main.MainActivity");
            caps.setCapability("appium:appWaitActivity", "org.wikipedia.*");
            caps.setCapability("appium:noReset", false);
            caps.setCapability("appium:ignoreHiddenApiPolicyError", true);

            return new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), caps);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create emulator driver", e);
        }
    }
}

