package drivers.real;

import config.RealDeviceConfig;
import helpers.ApkInstaller;
import io.appium.java_client.android.AndroidDriver;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.MutableCapabilities;

import java.net.URL;

public class RealDeviceDriver {

    public static AndroidDriver create() {
        try {
            RealDeviceConfig cfg = ConfigFactory.create(RealDeviceConfig.class, System.getProperties());

            if (cfg.reinstallApp()) {
                ApkInstaller.reinstall(cfg.udid(), cfg.appPackage(), cfg.app());
            }

            MutableCapabilities caps = new MutableCapabilities();
            caps.setCapability("platformName", cfg.platformName());
            caps.setCapability("appium:automationName", cfg.automationName());
            caps.setCapability("appium:udid", cfg.udid());
            caps.setCapability("appium:deviceName", "Android Device");

            caps.setCapability("appium:appPackage", cfg.appPackage());
            caps.setCapability("appium:appActivity", cfg.appActivity());
            caps.setCapability("appium:appWaitActivity", "org.wikipedia.*");

            caps.setCapability("appium:noReset", true);
            caps.setCapability("appium:fullReset", false);
            caps.setCapability("appium:autoGrantPermissions", true);

            return new AndroidDriver(new URL(cfg.appiumUrl()), caps);

        } catch (Exception e) {
            throw new RuntimeException("Failed to create real device driver", e);
        }
    }
}







