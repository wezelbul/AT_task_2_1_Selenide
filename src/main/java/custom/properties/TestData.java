package custom.properties;


import custom.properties.ru.yandex.PropsYandex;
import custom.properties.ru.yandex.market.PropsMarket;
import org.aeonbits.owner.ConfigFactory;

public class TestData {
    public static PropsDriver propsDriver = ConfigFactory.create(PropsDriver.class);
    public static PropsUrl propsUrl = ConfigFactory.create(PropsUrl.class);
    public static PropsYandex propsRuYandex =
            ConfigFactory.create(PropsYandex.class);
    public static PropsMarket propsRuYandexMarket =
            ConfigFactory.create(PropsMarket.class);
}