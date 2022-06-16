package custom.properties;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "system:env",
        "file:properties/driver.properties"
})

public interface PropsDriver extends Config {

    @Key("default.timeout")
    int defaultTimeout();

    @Key("name.default.driver")
    String nameDefaultDriver();

}
