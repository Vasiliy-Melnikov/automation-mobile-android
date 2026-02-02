package config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "classpath:config/emulation.properties"
})
public interface EmulationConfig extends Config {

    @Key("appium.url")
    @DefaultValue("http://127.0.0.1:4723/wd/hub")
    String appiumUrl();

    @Key("platformName")
    @DefaultValue("Android")
    String platformName();

    @Key("automationName")
    @DefaultValue("UiAutomator2")
    String automationName();

    @Key("app")
    @DefaultValue("apps/wikipedia.apk")
    String app();

    @Key("appPackage")
    @DefaultValue("org.wikipedia.alpha")
    String appPackage();

    @Key("appActivity")
    @DefaultValue("org.wikipedia.DefaultIcon")
    String appActivity();

    @Key("deviceName")
    @DefaultValue("emulator-5554")
    String deviceName();

    @Key("platformVersion")
    @DefaultValue("14")
    String platformVersion();
}


