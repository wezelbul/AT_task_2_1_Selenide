package base;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import custom.allure.selenide.CustomAllureSelenide;
import custom.properties.TestData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class BaseTest {

    @BeforeAll
    public static void setup() {
        SelenideLogger.addListener("AllureSelenide", new CustomAllureSelenide()
                .screenshots(true)
                .savePageSource(true));
    }

    @BeforeEach
    public void option() {
        Configuration.timeout = TestData.propsDriver.defaultTimeout();
        Configuration.browser = TestData.propsDriver.nameDefaultDriver();
        Selenide.open();
        WebDriverRunner.getWebDriver().manage().window().maximize();
    }

}
