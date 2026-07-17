package com.example.tests;

import com.example.core.BaseTest;
import com.example.pages.AviasalesSearchPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Тесты поиска авиабилетов с изменением параметров на Aviasales.
 * Содержит тесты: выбор бизнес-класса, изменение количества пассажиров, изменение валюты. (4, 5, 6)
 */
public class AviasalesSearchOptionsTest extends BaseTest {

    @Test
    public void shouldSelectBusinessClass() {
        log.info("--- Запуск теста: выбор бизнес-класса ---");

        AviasalesSearchPage searchPage = openPage(AviasalesSearchPage.class);
        searchPage = searchPage.waitFieldsVisible();

        searchPage = searchPage.openPassengers();
        searchPage = searchPage.selectBusinessClass();
        searchPage = searchPage.applyPassengers();

        searchPage = searchPage.clickOriginField();
        searchPage = searchPage.enterOriginQuery("Москва");

        searchPage = searchPage.clickDestinationField();
        searchPage = searchPage.enterDestinationQuery("Сочи");

        searchPage = searchPage.clickDatePickerStartDate();
        searchPage.waitFieldsVisible();
        searchPage = searchPage.selectTomorrowDate();

        String classText = searchPage.getTripClassText();
        log.info("Текст с классом: '{}'", classText);
        assertTrue(classText.contains("Бизнес") || classText.contains("Business"),
                "Должен отображаться выбранный класс: Бизнес. Фактически: " + classText);

        log.info("--- Тест успешно пройден ---");
    }

    @Test
    public void shouldChangePassengersCount() {
        log.info("--- Запуск теста: изменение количества пассажиров ---");

        AviasalesSearchPage searchPage = openPage(AviasalesSearchPage.class);
        searchPage = searchPage.waitFieldsVisible();

        searchPage = searchPage.openPassengers();
        searchPage = searchPage.setAdults(2);
        searchPage = searchPage.applyPassengers();

        searchPage = searchPage.clickOriginField();
        searchPage = searchPage.enterOriginQuery("Москва");

        searchPage = searchPage.clickDestinationField();
        searchPage = searchPage.enterDestinationQuery("Екатеринбург");

        searchPage = searchPage.clickDatePickerStartDate();
        searchPage.waitFieldsVisible();
        searchPage = searchPage.selectTomorrowDate();

        String passengersText = searchPage.getPassengersText();
        log.info("Текст с пассажирами: '{}'", passengersText);
        assertTrue(passengersText.contains("2"),
                "Должно отображаться количество пассажиров: 2. Фактически: " + passengersText);

        log.info("--- Тест успешно пройден ---");
    }

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