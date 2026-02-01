package config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "classpath:config/browserstack.properties"
})
public interface BrowserstackConfig extends Config {

    @Key("bs.user") @DefaultValue("") String user();
    @Key("bs.key")  @DefaultValue("") String key();

    @Key("bs.url") String url();
    @Key("bs.app") String app();

    @Key("bs.project") String project();
    @Key("bs.build") String build();
    @Key("bs.name") String name();

    @Key("device") String device();
    @Key("osVersion") String osVersion();
    @Key("platformName") @DefaultValue("Android") String platformName();
    @Key("automationName") @DefaultValue("UiAutomator2") String automationName();
}
