package screens;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class FoundOnDeviceScreen {

    private final AppiumDriver driver;

    private final By secondaryTextView = AppiumBy.id("org.wikipedia.alpha:id/secondaryTextView");
    private final By nextButton        = AppiumBy.id("org.wikipedia.alpha:id/fragment_onboarding_forward_button");

    public FoundOnDeviceScreen(AppiumDriver driver) {
        this.driver = driver;
    }

    public boolean isShown() {
        String secondary = normalize(tryText(secondaryTextView));
        return secondary.contains("weve found") && secondary.contains("device");
    }

    public void tapNext() {
        driver.findElement(nextButton).click();
    }

    private String tryText(By by) {
        try {
            List<WebElement> els = driver.findElements(by);
            if (els.isEmpty()) return "";
            return els.get(0).getText();
        } catch (Exception e) {
            return "";
        }
    }

    private String normalize(String s) {
        if (s == null) return "";
        String t = s.toLowerCase();
        t = t.replace("\u2019", "'").replace("’", "'");
        t = t.replace("weтve", "we've");
        t = t.replace("'", "");

        t = t.replace('\u00A0', ' ');
        t = t.replaceAll("\\s+", " ").trim();
        t = t.replaceAll("[^\\p{L}\\p{N}\\s]+", "");
        t = t.replaceAll("\\s+", " ").trim();
        return t;
    }
}
