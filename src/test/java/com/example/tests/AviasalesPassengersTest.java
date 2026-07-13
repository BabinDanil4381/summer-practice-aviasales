package com.example.tests;

import com.example.core.BaseTest;
import com.example.pages.AviasalesSearchPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Тест №4: Изменение количества пассажиров на Aviasales. (Захаренко)
 */
public class AviasalesPassengersTest extends BaseTest {

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
}