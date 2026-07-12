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

        // 1) Открыть страницу
        AviasalesSearchPage searchPage = AviasalesSearchPage.openPage();
        searchPage = searchPage.waitFieldsVisible();

        // 2) Открыть окно пассажиров и выбрать 2 взрослых
        searchPage = searchPage.openPassengers();
        searchPage = searchPage.setAdults(2);
        searchPage = searchPage.applyPassengers();

        // 3) Заполнить поле "откуда" — Москва
        searchPage = searchPage.clickOriginField();
        searchPage = searchPage.enterOriginQuery("Москва");

        // 4) Заполнить поле "куда" — Екатеринбург
        searchPage = searchPage.clickDestinationField();
        searchPage = searchPage.enterDestinationQuery("Екатеринбург");

        // 5) Выбрать дату "завтра"
        searchPage = searchPage.clickDatePickerStartDate();
        searchPage.waitFieldsVisible(); // ЖДЕМ ЗАГРУЗКИ КАЛЕНДАРЯ
        searchPage = searchPage.selectTomorrowDate();


        // 6) Нажать кнопку "Найти билеты" (переходит куда не надо. никак не проверить)
        // searchPage = searchPage.clickSearchButton();


        // 6) Проверить, что в параметрах отображается "2 пассажира"
        String passengersText = searchPage.getPassengersText();
        log.info("Текст с пассажирами: '{}'", passengersText);
        assertTrue(passengersText.contains("2"),
                "Должно отображаться количество пассажиров: 2. Фактически: " + passengersText);

        log.info("--- Тест успешно пройден ---");
    }
    // работает, проверено
}