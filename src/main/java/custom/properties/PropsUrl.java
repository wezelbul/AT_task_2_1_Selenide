package custom.properties;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "system:env",
        "file:properties/url.properties"
})
public interface PropsUrl extends Config {

    @Key("url.ru.yandex.main")
    String urlRuYandexMain();

    @Key("url.ru.yandex.all.services")
    String ruYandexAllServices();
}
