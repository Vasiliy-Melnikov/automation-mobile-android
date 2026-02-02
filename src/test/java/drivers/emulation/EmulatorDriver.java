package drivers.emulation;

import config.EmulationConfig;
import io.appium.java_client.android.AndroidDriver;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.MutableCapabilities;

import java.net.URL;

public class EmulatorDriver {

    public static AndroidDriver create() {
        try {
            EmulationConfig cfg = ConfigFactory.create(EmulationConfig.class, System.getProperties());

            MutableCapabilities caps = new MutableCapabilities();
            caps.setCapability("platformName", cfg.platformName());
            caps.setCapability("appium:automationName", cfg.automationName());
            caps.setCapability("appium:deviceName", cfg.deviceName());
            caps.setCapability("appium:platformVersion", cfg.platformVersion());
            caps.setCapability("appium:appPackage", cfg.appPackage());
            caps.setCapability("appium:appActivity", cfg.appActivity());
            caps.setCapability("appium:appWaitActivity", "org.wikipedia.*");

            caps.setCapability("appium:noReset", false);
            caps.setCapability("appium:fullReset", false);
            caps.setCapability("appium:autoGrantPermissions", true);

            return new AndroidDriver(new URL(cfg.appiumUrl()), caps);

        } catch (Exception e) {
            throw new RuntimeException("Failed to create emulator driver", e);
        }
    }
}


