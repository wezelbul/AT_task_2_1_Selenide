package pages.ru.yandex.services.market;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.NoSuchElementException;
import pages.ru.yandex.services.YandexServiceBasePage;

import java.util.List;

import static custom.properties.TestData.propsRuYandexMarket;
import static com.codeborne.selenide.Selenide.*;

public class YandexMarketPage extends YandexServiceBasePage {

    /**
     * Нажимает кнопку "Каталог" и кликает на заданную категорию товаров. Если категория не найдена -
     * тест "падает" с соответствующей ошибкой
     * @param categoryMenu искомая категория товаров
     * @return PageObject экземпляр класса YandexMarketPage
     */
    @Step("Выбрать раздел {categoryMenu}")
    public YandexMarketPage clickCategoryMenu(String categoryMenu) {
        String categoryMenuPointXPath = propsRuYandexMarket.areaCatalogXPath()
                + propsRuYandexMarket.tagCategoriesXPath() + "[.='" + categoryMenu + "']";
        clickElement(propsRuYandexMarket.buttonCatalogXPath(), "Каталог");
        $x(propsRuYandexMarket.areaCatalogXPath() + propsRuYandexMarket.tagCategoriesXPath())
                .shouldBe(Condition.exist);
        clickElement(categoryMenuPointXPath, categoryMenu);
        return page(YandexMarketPage.class);
    }

    /**
     * Кликает на заданную подкатегорию товаров (со страницы категории). Если подкатегория не найдена -
     * тест "падает" с соответствующей ошибкой
     * @param subcategoryMenu искомая подкатегория товаров
     * @return PageObject экземпляр класса YandexMarketPage
     */
    @Step("Выбрать раздел {subcategoryMenu}")
    public YandexMarketPage clickSubcategoryMenu(String subcategoryMenu) {
        boolean isDone = false;
        String subcategoryBlocksXpath = "//div[contains(@data-apiary-widget-name,'NavigationTree')]//div[div/ul]";
        List<SelenideElement> subcategoryBlocks = $$x(subcategoryBlocksXpath);
        for (SelenideElement subcategoryBlock : subcategoryBlocks) {
            if (subcategoryBlock.$x("./div[contains(@data-zone-data, 'more_button')]/div").exists()) {
                subcategoryBlock.$x("./div[contains(@data-zone-data, 'more_button')]/div").click();
            }
            if (subcategoryBlock.$x(".//ul").getText().contains(subcategoryMenu)) {
                subcategoryBlock.$x(".//ul/li//a[text()='" + subcategoryMenu + "']").click();
                isDone = true;
                break;
            }
        }
        Assertions.assertTrue(isDone, "Подкатегория '" + subcategoryMenu + "' не найдена");
        return page(YandexMarketPage.class);
    }

    /**
     * Кликает по чекбоксу. Если у поля с параметром есть кнопка "Показать всё" - сначала кликает на неё, затем
     * листает (если поле параметра является пролистываемым) до чекбокса и кликает по нему.
     * @param param искомый параметр фильтра поиска
     * @param value искомое значение
     * @return PageObject экземпляр класса YandexMarketPage
     */
    @Step("Задать параметр {param} - {value}")
    public YandexMarketPage clickCheckBoxWithShowAll(String param, String value) {

        String checkBoxXPath = "//fieldset[legend[.='" + param + "']]//label[.='" + value + "']";
        String waitFieldXPath = "//fieldset[legend[.='" + param + "']]/div/div";
        String showAllButton = "//fieldset[legend[.='" + param + "']]//span[@role='button']//span";

        if ($x(showAllButton).exists()) {
            $x(showAllButton).click();
            $x(waitFieldXPath).shouldBe(Condition.exist);
        }
        try {
            $x(checkBoxXPath).scrollIntoView(true).click();
        } catch (NoSuchElementException noSuchElementException) {
            Assertions.fail("Элемент " + param + " - " + value + " не найден");
        }
        if ($x(showAllButton).exists()) {
            $x(showAllButton).click();
        }
        return page(YandexMarketPage.class);
    }

    /**
     *Ожидание обновления результатов поиска. Метод-обёртка для отображения степа.
     * @return PageObject экземпляр класса YandexMarketPage
     */
    @Step ("Дождаться обновления результатов поиска")
    public YandexMarketPage waitSearchResults() {
        waitSearchResult();
        return page(YandexMarketPage.class);
    }

    /**
     *Ожидание обновления результатов поиска.
     */
    private void waitSearchResult() {
        $x("//div[@data-grabber='SearchSerp']//div[span[@role='progressbar']]")
                .shouldBe(Condition.exist)
                .shouldNotBe(Condition.exist);
    }

    /**
     * Метод для нажатия кнопки "Показать ещё" и увеличения количества поисковых выдач на странице
     * @return PageObject экземпляр класса YandexMarketPage
     */
    @Step ("Нажать кнопку 'Показать ещё'")
    public YandexMarketPage clickShowMoreButton() {
        clickElement(propsRuYandexMarket.divSearchPagerXPath() + propsRuYandexMarket.buttonPagerMoreXPath(),
                "Показать ещё");
        waitSearchResult();
        return page(YandexMarketPage.class);
    }

    /**
     * Метод проверяет, что количество элементов на странице равно переданному значению, или, если элементов меньше -
     * что количество элементов равно общему числу результатов поиска
     * @param elementsOnPage искомое количество поисковых выдач на странице
     * @return PageObject экземпляр класса YandexMarketPage
     */
    @Step ("Проверить, что поисковая выдача содержит {elementsOnPage} элементов")
    public YandexMarketPage checkAmountElementsOnPage(int elementsOnPage) {
        String amountElementsOnPageXPath = "//div[text()='Вы посмотрели ']";
        $x(propsRuYandexMarket.divSearchPagerXPath()).hover();
        int sizeResults = $$x(propsRuYandexMarket.headersSearchResultsXPath()).size();
        int amount = Integer.parseInt(
                $x(propsRuYandexMarket.divSearchPagerXPath() + amountElementsOnPageXPath)
                        .getText()
                        .split(" ")[2]);
        int total = Integer.parseInt(
                $x(propsRuYandexMarket.divSearchPagerXPath() + amountElementsOnPageXPath)
                        .getText()
                        .split(" ")[4]);
        Assertions.assertTrue((amount == elementsOnPage && elementsOnPage == sizeResults)
                        || (amount == total && total == sizeResults),
                "Количество элементов на странице не соответствует заявленным: "
                        + elementsOnPage + "\n"
                        + "На странице представлено " + sizeResults + ", отображается на счетчике " + amount
                        + ", всего в поисковой выдаче " + total);
        return page(YandexMarketPage.class);
    }

    /**
     * Метод проверяет, что все результаты поиска в заголовке содержат искомое слово. Если страница не одна -
     * проверяет все.
     * @param value искомое слово
     * @return PageObject экземпляр класса YandexMarketPage
     */
    @Step("Убедиться, что в выборку попали только {value}")
    public YandexMarketPage checkSearchResultsContainsString(String value) {
        String nextPageButton = propsRuYandexMarket.divSearchPagerXPath() + propsRuYandexMarket.divNextPageButtonXPath();
        do {
            $x(propsRuYandexMarket.divSearchPagerXPath()).hover();
            List<String> headerList = $$x(propsRuYandexMarket.headersSearchResultsXPath()).texts();
            for (String header : headerList) {
                Assertions.assertTrue(header.toLowerCase().contains(value.toLowerCase()),
                        "Заголовок товара '" + header + "' не содержит '" + value + "'");
            }
            if ($x(nextPageButton).exists()) {
                $x(nextPageButton).click();
                waitSearchResult();
            } else {
                break;
            }
        } while (true);
        return page(YandexMarketPage.class);
    }
}
