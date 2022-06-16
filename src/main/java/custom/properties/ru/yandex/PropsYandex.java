package custom.properties.ru.yandex;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "system:env",
        "file:properties/ru/yandex/xpath.properties"
})

public interface PropsYandex extends Config{

    @Key("tag.service.xpath")
    String tagServiceXPath();

    @Key("panel.services")
    String panelServices();

    @Key("button.more.services.xpath")
    String buttonMoreServicesXPath();

    @Key("box.more.services.xpath")
    String boxMoreServicesXPath();

}
