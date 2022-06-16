package pages.base;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.page;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public abstract class BasePage {

    /**
     * Открывает URL, переданный в качестве параметра
     * @param url URL страницы для открытия
     * @return PageObject экземпляр переданного класса, наследующего BasePage
     */
    @Step("Зайти на {url}")
    public <T extends BasePage> T openPageByUrl(String url, Class<T> typeNextPage) {
        Selenide.open(url);
        return typeNextPage.cast(page(typeNextPage));
    }

    /**
     * Проверяет наличие элемента и кликает на него
     * @param xPath xpath-запрос кликабельного элемента
     * @param buttonName наименование кнопки (для отображения в отчёте)
     */
    protected void clickElement(String xPath, String buttonName) {
        if ($x(xPath).exists()) {
            $x(xPath).click();
        } else {
            Assertions.fail("Элемент '" + buttonName + "' не найден");
        }
    }

    /**
     * Осуществляет переход веб-драйвера к последней открытой странице браузера (по порядку открытия,
     * не по порядку расположения в окне)
     */
    protected void goToLastOpenedPage() {
        getWebDriver().getWindowHandles()
                .forEach(tab -> getWebDriver().switchTo().window(tab));
    }

}
