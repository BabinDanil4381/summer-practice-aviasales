package com.example.elements;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.example.core.BaseElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class CurrencySelector extends BaseElement {

    private static final Logger log = LoggerFactory.getLogger(CurrencySelector.class);

    private static final String LOCALE_BUTTON = "//button[@aria-label='Страна, Язык, Валюта']";
    private static final String CURRENCY_TAB = "//span[@data-test-id='text']//p[@data-test-id='text' and text()='Валюта']/ancestor::label";
    private static final String CURRENCY_SECTION = "//div[@id='currency']";
    private static final String USD_BUTTON = "//button[@role='menuitem']//p[@data-test-id='text' and text()='USD']/ancestor::button";
    private static final String USD_SYMBOL = "$";

    public CurrencySelector() {
        super(LOCALE_BUTTON, "");
    }

    public void openMenu() {
        log.info("Открытие меню выбора локали");
        clickElement(visibleElement(Duration.ofSeconds(WAIT_SECONDS)));
    }

    public void clickCurrencyTab() {
        log.info("Нажатие на вкладку 'Валюта' в меню");
        clickElement($x(CURRENCY_TAB).shouldBe(Condition.visible, Duration.ofSeconds(WAIT_SECONDS)));
    }

    public void selectUSD() {
        log.info("Выбор валюты USD");
        clickElement($x(USD_BUTTON).shouldBe(Condition.visible, Duration.ofSeconds(WAIT_SECONDS)));
    }

    public String getCurrencySymbol() {
        log.info("Получение символа валюты");
        try {
            SelenideElement currencyElement = $x("//span[contains(text(), '$')]");
            if (currencyElement.exists() && currencyElement.isDisplayed()) {
                log.info("Символ валюты: {}", USD_SYMBOL);
                return USD_SYMBOL;
            }
            SelenideElement currencyText = $x("//div[contains(@data-test-id, 'text') and contains(., '$')]");
            if (currencyText.exists() && currencyText.isDisplayed()) {
                log.info("Символ валюты: {}", USD_SYMBOL);
                return USD_SYMBOL;
            }
        } catch (Exception e) {
            log.warn("Символ валюты не найден: {}", e.getMessage());
        }

        log.warn("Символ валюты не найден");
        return "";
    }
}