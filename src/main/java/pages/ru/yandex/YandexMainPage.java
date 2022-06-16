package pages.ru.yandex;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import pages.base.BasePage;
import pages.ru.yandex.services.YandexServiceBasePage;

import static com.codeborne.selenide.Selenide.*;
import static custom.properties.TestData.*;

public class YandexMainPage extends BasePage {

    /**
     * Метод ищет сервис Яндекса сперва на главной странице, в случае неудачи - в расширенном списке по кнопке "Ещё",
     *  если не находит и там - переходит на страницу со всеми сервисами и ищет среди них. В случае успеха
     *  переходит на страницу сервиса, в случае неудачи уведомляет об отсутствии сервиса и проваливает тест
     * @param serviceName наименование искомого сервиса
     * @param typeServicePage класс PageObject'а для страницы сервиса
     * @return PageObject экземпляр переданного класса, наследующего YandexServiceBasePage
     * */
    @Step ("Перейти в Яндекс.{serviceName}")
    public <T extends YandexServiceBasePage> T goToService(String serviceName, Class<T> typeServicePage) {
        String serviceXPath = propsRuYandex.tagServiceXPath() + "[contains(., '" + serviceName + "')]";

        if ($x(propsRuYandex.panelServices() + serviceXPath).exists()) {
            $x(propsRuYandex.panelServices() + serviceXPath).click();
        } else {
            if ($x(propsRuYandex.panelServices()
                    + propsRuYandex.buttonMoreServicesXPath()).exists()) {
                $x(propsRuYandex.panelServices()
                        + propsRuYandex.buttonMoreServicesXPath()).click();
                if ($x(propsRuYandex.boxMoreServicesXPath() + serviceXPath).exists()) {
                    $x(propsRuYandex.boxMoreServicesXPath() + serviceXPath).click();
                } else {
                    open(propsUrl.ruYandexAllServices());
                    if ($x(serviceXPath).exists()) {
                        $x(serviceXPath).click();
                    } else {
                        Assertions.fail("Сервис '" + serviceName + "' не найден.");
                    }
                }
            }
        }

        goToLastOpenedPage();
        return typeServicePage.cast(page(typeServicePage));
    }

}
