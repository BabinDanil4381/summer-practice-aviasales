package com.example.elements;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.example.core.BaseElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class CurrencySelector extends BaseElement {

    private static final Logger log = LoggerFactory.getLogger(CurrencySelector.class);

    // Кнопка открытия меню (глобус)
    private static final String LOCALE_BUTTON = "//button[@aria-label='Страна, Язык, Валюта']";

    // Вкладка "Валюта" в меню (это label с текстом "Валюта")
    private static final String CURRENCY_TAB = "//span[@data-test-id='text']//p[@data-test-id='text' and text()='Валюта']/ancestor::label";

    // Контейнер валюты (появляется после клика на вкладку)
    private static final String CURRENCY_SECTION = "//div[@id='currency']";

    // Кнопка USD
    private static final String USD_BUTTON = "//button[@role='menuitem']//p[@data-test-id='text' and text()='USD']/ancestor::button";

    public CurrencySelector() {
        super(LOCALE_BUTTON, "");
    }

    public CurrencySelector openMenu() {
        log.info("Открытие меню выбора локали");
        baseElement.shouldBe(Condition.visible, Duration.ofSeconds(5)).click();
        Selenide.sleep(500);
        return this;
    }

    public CurrencySelector clickCurrencyTab() {
        log.info("Нажатие на вкладку 'Валюта' в меню");
        $x(CURRENCY_TAB).shouldBe(Condition.visible, Duration.ofSeconds(5)).click();
        Selenide.sleep(500);
        return this;
    }

    public CurrencySelector selectUSD() {
        log.info("Выбор валюты USD");
        $x(USD_BUTTON).shouldBe(Condition.visible, Duration.ofSeconds(5)).click();
        Selenide.sleep(500);
        return this;
    }

    public String getCurrencySymbol() {
        log.info("Получение символа валюты");
        try {
            String price = $x("//span[contains(text(), '$')]").getText();
            log.info("Символ валюты: $");
            return "$";
        } catch (Exception e) {
            log.warn("Символ валюты не найден");
            return "";
        }
    }
}