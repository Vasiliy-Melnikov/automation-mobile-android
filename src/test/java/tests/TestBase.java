package tests;

import com.codeborne.selenide.Configuration;
import drivers.MobileDriverFactory;
import helpers.AllureAttachments;
import helpers.BrowserstackApi;
import io.appium.java_client.AppiumDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TestBase {

    protected AppiumDriver driver;
    private static final String APP_PACKAGE = "org.wikipedia.alpha";

    @BeforeAll
    static void setup() {
        Configuration.timeout = 30000;
        Configuration.browserSize = null;
    }

    @BeforeEach
    void setUpDriverAndState() {
        String host = System.getProperty("deviceHost", "browserstack").toLowerCase();
        String udid = System.getProperty("udid", "emulator-5554");
        if ("emulation".equals(host)) {
            adbForceStop(udid, APP_PACKAGE);
            adbClear(udid, APP_PACKAGE);
        } else if ("real".equals(host)) {
            adbForceStop(udid, APP_PACKAGE);
        }

        System.out.println(">>> before createDriver");
        driver = MobileDriverFactory.createDriver();
        System.out.println(">>> after createDriver");
    }

    private void adbForceStop(String udid, String packageName) {
        adbRun(udid, "shell", "am", "force-stop", packageName);
    }

    private void adbClear(String udid, String packageName) {
        adbRun(udid, "shell", "pm", "clear", packageName);
    }

    private void adbRun(String udid, String... args) {
        try {
            String[] cmd = new String[args.length + 3];
            cmd[0] = "adb";
            cmd[1] = "-s";
            cmd[2] = udid;
            System.arraycopy(args, 0, cmd, 3, args.length);

            Process p = new ProcessBuilder(cmd)
                    .redirectErrorStream(true)
                    .start();

            try (BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    System.out.println("[adb] " + line);
                }
            }

            int code = p.waitFor();
            if (code != 0) {
                throw new RuntimeException("adb failed, exitCode=" + code);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to run adb command", e);
        }
    }

    @AfterEach
    void tearDown() {
        if (driver == null) return;

        String host = System.getProperty("deviceHost", "browserstack").toLowerCase();
        String sessionId = driver.getSessionId() != null ? driver.getSessionId().toString() : null;

        AllureAttachments.screenshot(driver);
        AllureAttachments.pageSource(driver);

        driver.quit();

        if ("browserstack".equals(host) && sessionId != null) {
            new BrowserstackApi().attachVideo(sessionId);
        }
    }
}








