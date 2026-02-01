package tests;

import flows.OnboardingFlow;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

@Tag("android")
public class AndroidWikipediaOnboardingTests extends TestBase {

    @Test
    void wikipediaOnboarding4ScreensTest() {
        new OnboardingFlow(driver).complete(List.of(
                new OnboardingFlow.ScreenSpec(new String[]{"free"}, new String[]{"encyclopedia"}),
                new OnboardingFlow.ScreenSpec(new String[]{"new ways"}, new String[]{""}),
                new OnboardingFlow.ScreenSpec(new String[]{"reading"}, new String[]{"lists"}),
                new OnboardingFlow.ScreenSpec(new String[]{"data"}, new String[]{"privacy"})
        ));
    }
}



