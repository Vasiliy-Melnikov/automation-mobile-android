package drivers.real;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.MutableCapabilities;

import java.net.URL;

public class RealDeviceDriver {

    public static AndroidDriver create() {
        try {
            String udid = System.getProperty("udid");

            MutableCapabilities caps = new MutableCapabilities();

            caps.setCapability("platformName", "Android");
            caps.setCapability("appium:automationName", "UiAutomator2");
            caps.setCapability("appium:udid", udid);
            caps.setCapability("appium:deviceName", "Android Device");
            caps.setCapability("appium:appPackage", "org.wikipedia.alpha");
            caps.setCapability("appium:appActivity", "org.wikipedia.DefaultIcon");
            caps.setCapability("appium:appWaitActivity", "org.wikipedia.*");
            caps.setCapability("appium:noReset", false);
            caps.setCapability("appium:autoGrantPermissions", true);


            return new AndroidDriver(
                    new URL("http://127.0.0.1:4723/wd/hub"),
                    caps
            );

        } catch (Exception e) {
            throw new RuntimeException("Failed to create real device driver", e);
        }
    }
}





