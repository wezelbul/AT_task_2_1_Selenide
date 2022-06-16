package ru.yandex.market;

import base.BaseTest;

import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pages.ru.yandex.YandexMainPage;
import pages.ru.yandex.services.market.YandexMarketPage;
import static custom.properties.TestData.*;

public class Tests extends BaseTest {

    @Feature("Проверка результатов поиска на Яндекс.Маркете")
    @DisplayName("Проверка результатов поиска на Яндекс.Маркете с помощью Selenide PageObject")
    @ParameterizedTest(name = "Проверка, что все результаты поиска в категории {2} содержат только модель {5}" +
            " производителя {4}")
    @MethodSource("helpers.DataProvider#provideData")
    public void shouldHaveResultsOnlyOneProductModel(String serviceName, String categoryName, String subcategoryName,
                                                     String filterTypeName, String manufacturerName, String modelName) {
        YandexMainPage yandexMainPage = new YandexMainPage();
        yandexMainPage.openPageByUrl(propsUrl.urlRuYandexMain(), YandexMainPage.class)
                .goToService(serviceName, YandexMarketPage.class)
                .clickCategoryMenu(categoryName)
                .clickSubcategoryMenu(subcategoryName)
                .clickCheckBoxWithShowAll(filterTypeName, manufacturerName)
                .waitSearchResults()
                .checkAmountElementsOnPage(propsRuYandexMarket.amountElementsOnPage())
                .clickShowMoreButton()
                .checkAmountElementsOnPage(propsRuYandexMarket.amountElementsOnPage()*2)
                .checkSearchResultsContainsString(modelName);
    }
}
