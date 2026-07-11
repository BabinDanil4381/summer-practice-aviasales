package com.example.tests;

import com.example.core.BaseTest;
import com.example.pages.AviasalesSearchPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Тест вставки в поле откуда на Aviasales.
 * <p>
 * Тест-кейс:
 * 1. Открыть страницу Aviasales
 * 2. Нажать на поле откуда
 * 3. Ввести текст
 * 4. Проверить, что введённый текст соответствует ожидаемому
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

        searchPage = searchPage.clickDatePickerStartDate(); // Нажатие на поле 'когда'
        searchPage = searchPage.selectTodayDate(); // Выбор сегодняшней даты в календаре
        searchPage = searchPage.clickDatePickerEndDate(); // Нажатие на поле 'когда'
        searchPage = searchPage.selectTomorrowDate(); // Выбор завтрашней даты в календаре
        

        // Шаг 6: Проверить, что текст в полях совпадает с введённым
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
