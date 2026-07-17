package com.example.tests;

import com.example.core.BaseTest;
import com.example.pages.AviasalesSearchPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Тест №6: Изменение валюты на Aviasales.
 */
public class AviasalesCurrencyTest extends BaseTest {

    @Test
    public void shouldChangeCurrencyToUSD() {
        log.info("--- Запуск теста: изменение валюты на USD ---");

        AviasalesSearchPage searchPage = openPage(AviasalesSearchPage.class);
        searchPage = searchPage.waitFieldsVisible();

        searchPage = searchPage.changeCurrencyToUSD();

        searchPage = searchPage.clickOriginField();
        searchPage = searchPage.enterOriginQuery("Москва");

        searchPage = searchPage.clickDestinationField();
        searchPage = searchPage.enterDestinationQuery("Санкт-Петербург");

        searchPage = searchPage.clickDatePickerStartDate();
        searchPage = searchPage.selectTomorrowDate();

        searchPage = searchPage.clickSearchButton();

        String symbol = searchPage.getCurrencySymbol();
        log.info("Символ валюты: '{}'", symbol);
        assertTrue(symbol.contains("$"), "Должен отображаться символ $, а не " + symbol);

        log.info("--- Тест успешно пройден ---");
    }
}