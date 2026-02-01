package screens;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class OnboardingScreen {

    private final AppiumDriver driver;

    private final By primaryTextView   = AppiumBy.id("org.wikipedia.alpha:id/primaryTextView");
    private final By secondaryTextView = AppiumBy.id("org.wikipedia.alpha:id/secondaryTextView");
    private final By nextButton        = AppiumBy.id("org.wikipedia.alpha:id/fragment_onboarding_forward_button");
    private final By getStartedButton  = AppiumBy.id("org.wikipedia.alpha:id/fragment_onboarding_done_button");

    public OnboardingScreen(AppiumDriver driver) {
        this.driver = driver;
    }

    @Step("Read primary text")
    public String primaryRaw() {
        return getText(primaryTextView);
    }

    @Step("Read secondary text")
    public String secondaryRaw() {
        return getText(secondaryTextView);
    }

    @Step("Get Started button is visible")
    public boolean isGetStartedVisible() {
        return isDisplayed(getStartedButton);
    }

    @Step("Tap Next or Done")
    public void tapNextOrDone() {
        if (isDisplayed(nextButton)) {
            driver.findElement(nextButton).click();
            return;
        }
        if (isDisplayed(getStartedButton)) {
            driver.findElement(getStartedButton).click();
            return;
        }
        throw new AssertionError("Neither NEXT nor GET STARTED is visible");
    }

    @Step("Tap Get Started")
    public void tapGetStarted() {
        driver.findElement(getStartedButton).click();
    }

    private boolean isDisplayed(By by) {
        try {
            List<WebElement> els = driver.findElements(by);
            return !els.isEmpty() && els.get(0).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    private String getText(By by) {
        try {
            List<WebElement> els = driver.findElements(by);
            if (els.isEmpty()) return "";
            return els.get(0).getText();
        } catch (Exception e) {
            return "";
        }
    }
}


