package custom.properties.ru.yandex.market;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "system:env",
        "file:properties/ru/yandex//market/xpath.properties",
        "file:properties/ru/yandex//market/data.properties"
})

public interface PropsMarket extends Config{

    @Key("area.catalog.xpath")
    String areaCatalogXPath();

    @Key("button.catalog.xpath")
    String buttonCatalogXPath();

    @Key("tag.categories.xpath")
    String tagCategoriesXPath();

    @Key("headers.search.results.xpath")
    String headersSearchResultsXPath();

    @Key("button.pager.more.xpath")
    String buttonPagerMoreXPath();

    @Key("div.search.pager.xpath")
    String divSearchPagerXPath();

    @Key("div.next.page.button.xpath")
    String divNextPageButtonXPath();

    @Key("amount.elements.on.page")
    int amountElementsOnPage();
}
