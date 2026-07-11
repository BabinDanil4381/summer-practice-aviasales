package com.example.tests;

import com.example.core.BaseTest;
import com.example.pages.AviasalesSearchPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Тест вставки в поле откуда на Aviasales.
 * Тест-кейс:
 * 1. Открыть страницу Aviasales
 * 2. Нажать на поле откуда
 * 3. Ввести текст
 * 4. нажатие на поле когда и выбор сегодняшней даты
 * 5. нажатие на поле обратно и выбор завтрашней даты
 * 6. Проверить, что введённый текст соответствует ожидаемому
 */
public class AviasalesSearchTest extends BaseTest {

    @Test
    public void shouldEnterTextInSearchField() {
        log.info("--- Запуск теста: ввод текста в поле откуда ---");

        // Шаг 1: Открыть страницу Aviasales
        AviasalesSearchPage searchPage = AviasalesSearchPage.openPage();

        // Шаг 2: Дождаться видимости поля откуда
        searchPage = searchPage.waitFieldsVisible();

        // Шаг 3: Нажатие на поле 'откуда'
        searchPage = searchPage.clickOriginField();

        // Шаг 4: Ввод в поле 'откуда'
        String expectedOrigin = "Москва";
        searchPage = searchPage.enterOriginQuery(expectedOrigin);

        // Шаг 5: Нажатие на поле 'куда'
        searchPage = searchPage.clickDestinationField();

        // Шаг 6: Ввод в поле 'куда'
        String expectedDestination = "Санкт-Петербург";
        searchPage = searchPage.enterDestinationQuery(expectedDestination);
        
        // Шаг 7: Нажатие на поле 'когда' и выбор сегодняшней даты
        searchPage = searchPage.clickDatePickerStartDate();
        searchPage = searchPage.selectTodayDate();

        // Шаг 8: Нажатие на поле 'обратно' и выбор завтрашней даты
        searchPage = searchPage.clickDatePickerEndDate();
        searchPage = searchPage.selectTomorrowDate();
        

        // Шаг 9: Проверить, что текст в полях совпадает с введённым
        // проверка полей даты будет позже
        String actualOrigin = searchPage.getOriginQuery();
        String actualDestination = searchPage.getdestinationQuery();
        //log.info("Ожидаемый текст: '{}'", expectedQuery);
        //log.info("Фактический текст: '{}'", actualQuery);
        assertEquals(expectedOrigin, actualOrigin,
                "Текст в поле 'откуда' должен совпадать с введённым");
        assertEquals(expectedDestination, actualDestination,
                "Текст в поле 'куда' должен совпадать с введённым");

        log.info("--- Тест успешно пройден ---");
    }
}
