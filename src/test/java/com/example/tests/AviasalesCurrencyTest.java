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

        // 1) Открыть страницу
        AviasalesSearchPage searchPage = AviasalesSearchPage.openPage();
        searchPage = searchPage.waitFieldsVisible();

        // 2) Сменить валюту на USD
        searchPage = searchPage.changeCurrencyToUSD();

        // 3) Заполнить поле "откуда" — Москва
        searchPage = searchPage.clickOriginField();
        searchPage = searchPage.enterOriginQuery("Москва");

        // 4) Заполнить поле "куда" — Санкт-Петербург
        searchPage = searchPage.clickDestinationField();
        searchPage = searchPage.enterDestinationQuery("Санкт-Петербург");

        // 5) Выбрать дату "завтра"
        searchPage = searchPage.clickDatePickerStartDate();
        searchPage = searchPage.selectTomorrowDate();

        // 6) Нажать кнопку "Найти билеты"
        searchPage = searchPage.clickSearchButton();

        // 7) Проверить, что цены отображаются в долларах
        String symbol = searchPage.getCurrencySymbol();
        log.info("Символ валюты: '{}'", symbol);
        assertTrue(symbol.contains("$"), "Должен отображаться символ $, а не " + symbol);

        log.info("--- Тест успешно пройден ---");
    }

    // прошел
}