package com.example.tests;

import com.example.core.BaseTest;
import com.example.pages.AviasalesSearchPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Тест №1: Ввод текста в поле поиска на Aviasales.
 */
public class AviasalesSearchTest extends BaseTest {

    @Test
    public void shouldEnterTextInSearchField() {
        log.info("--- Запуск теста: ввод текста в поле откуда ---");

        AviasalesSearchPage searchPage = openPage(AviasalesSearchPage.class);

        searchPage = searchPage.waitFieldsVisible();
        searchPage = searchPage.clickOriginField();

        String expectedOrigin = "Москва";
        searchPage = searchPage.enterOriginQuery(expectedOrigin);

        searchPage = searchPage.clickDestinationField();

        String expectedDestination = "Санкт-Петербург";
        searchPage = searchPage.enterDestinationQuery(expectedDestination);

        searchPage = searchPage.clickDatePickerStartDate();
        searchPage = searchPage.selectTodayDate();

        searchPage = searchPage.clickDatePickerEndDate();
        searchPage = searchPage.selectTomorrowDate();

        String actualOrigin = searchPage.getOriginQuery();
        String actualDestination = searchPage.getdestinationQuery();
        assertEquals(expectedOrigin, actualOrigin,
                "Текст в поле 'откуда' должен совпадать с введённым");
        assertEquals(expectedDestination, actualDestination,
                "Текст в поле 'куда' должен совпадать с введённым");

        log.info("--- Тест успешно пройден ---");
    }
}
