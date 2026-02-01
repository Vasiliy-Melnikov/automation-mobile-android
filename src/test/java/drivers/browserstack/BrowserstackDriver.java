package drivers.browserstack;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.MutableCapabilities;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class BrowserstackDriver {

    public static AndroidDriver create() {
        try {
            MutableCapabilities caps = new MutableCapabilities();
            caps.setCapability("platformName", "Android");
            caps.setCapability("appium:automationName", "UiAutomator2");
            caps.setCapability("appium:app", "bs://ec352a90ed2be8ca764cee4939dea1c359f9dfde");
            caps.setCapability("appium:autoGrantPermissions", true);
            caps.setCapability("appium:noReset", false);
            Map<String, Object> bstackOptions = new HashMap<>();
            bstackOptions.put("userName", System.getProperty("bs.user"));
            bstackOptions.put("accessKey", System.getProperty("bs.key"));
            bstackOptions.put("projectName", System.getProperty("bs.project", "QA-Guru Mobile"));
            bstackOptions.put("buildName", System.getProperty("bs.build", "local-build"));
            bstackOptions.put("sessionName", System.getProperty("bs.name", "Mobile tests"));
            bstackOptions.put("deviceName", "Google Pixel 7");
            bstackOptions.put("osVersion", "13.0");

            caps.setCapability("bstack:options", bstackOptions);

            return new AndroidDriver(
                    new URL("https://hub-cloud.browserstack.com/wd/hub"),
                    caps
            );

        } catch (Exception e) {
            throw new RuntimeException("Failed to create BrowserStack driver", e);
        }
    }
}



