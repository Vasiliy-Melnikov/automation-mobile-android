package flows;

import io.appium.java_client.AppiumDriver;
import io.qameta.allure.Step;
import screens.FoundOnDeviceScreen;
import screens.OnboardingScreen;
import utils.TextNormalizer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OnboardingFlow {

    public static class ScreenSpec {
        public final String[] primaryAny;
        public final String[] secondaryAny;

        public ScreenSpec(String[] primaryAny, String[] secondaryAny) {
            this.primaryAny = primaryAny;
            this.secondaryAny = secondaryAny;
        }
    }

    private final OnboardingScreen onboarding;
    private final FoundOnDeviceScreen foundOnDevice;

    public OnboardingFlow(AppiumDriver driver) {
        this.onboarding = new OnboardingScreen(driver);
        this.foundOnDevice = new FoundOnDeviceScreen(driver);
    }

    @Step("Complete onboarding (order-independent)")
    public void complete(List<ScreenSpec> expectedScreens) {
        Set<Integer> seen = new HashSet<>();
        int safety = 15;

        while (safety-- > 0) {

            if (foundOnDevice.isShown()) {
                foundOnDevice.tapNext();
                continue;
            }

            if (onboarding.isGetStartedVisible()) {
                onboarding.tapGetStarted();
                return;
            }

            String primaryRaw = onboarding.primaryRaw();
            String secondaryRaw = onboarding.secondaryRaw();

            String primary = TextNormalizer.normalize(primaryRaw);
            String secondary = TextNormalizer.normalize(secondaryRaw);

            int matchedIndex = findMatchingScreen(expectedScreens, primary, secondary);

            assertTrue(matchedIndex != -1,
                    "Unknown onboarding screen.\n" +
                            "Primary(raw): " + primaryRaw + "\n" +
                            "Secondary(raw): " + secondaryRaw + "\n" +
                            "Primary(norm): " + primary + "\n" +
                            "Secondary(norm): " + secondary);

            seen.add(matchedIndex);
            onboarding.tapNextOrDone();
            if (seen.size() >= expectedScreens.size() && onboarding.isGetStartedVisible()) {
                onboarding.tapGetStarted();
                return;
            }
        }

        throw new AssertionError("Onboarding was not completed within safety steps. Possibly stuck.");
    }

    private int findMatchingScreen(List<ScreenSpec> specs, String primaryNorm, String secondaryNorm) {
        for (int i = 0; i < specs.size(); i++) {
            ScreenSpec s = specs.get(i);

            boolean primaryOk = containsAny(primaryNorm, s.primaryAny);
            boolean secondaryOk = (s.secondaryAny == null || s.secondaryAny.length == 0 || allEmpty(s.secondaryAny))
                    || containsAny(secondaryNorm, s.secondaryAny);

            if (primaryOk && secondaryOk) return i;
        }
        return -1;
    }

    private boolean containsAny(String actualNormalized, String[] expectedAnyRaw) {
        if (expectedAnyRaw == null || expectedAnyRaw.length == 0) return true;

        for (String exp : expectedAnyRaw) {
            String e = TextNormalizer.normalize(exp);
            if (e.isEmpty()) continue;
            if (actualNormalized.contains(e)) return true;
        }
        return false;
    }

    private boolean allEmpty(String[] arr) {
        for (String s : arr) {
            if (!TextNormalizer.normalize(s).isEmpty()) return false;
        }
        return true;
    }
}



