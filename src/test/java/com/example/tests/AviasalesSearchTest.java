package com.example.tests;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

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

        DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern("d MMMM, E")
            .withLocale(new Locale("ru"));
        String expectedStartDate = LocalDate.now().format(formatter);
        String expectedEndDate = LocalDate.now().plusDays(1).format(formatter);

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
        
        assertEquals(expectedStartDate, searchPage.getStartDate(),
                "Дата в поле 'Когда' должна совпадать с введённой");
        
        assertEquals(expectedEndDate, searchPage.getEndDate(),
                "Дата в поле 'Обратно' должна совпадать с введённой");

        String actualOrigin = searchPage.getOriginQuery();
        String actualDestination = searchPage.getdestinationQuery();
        assertEquals(expectedOrigin, actualOrigin,
                "Текст в поле 'Откуда' должен совпадать с введённым");
        assertEquals(expectedDestination, actualDestination,
                "Текст в поле 'Куда' должен совпадать с введённым");

        log.info("--- Тест успешно пройден ---");
    }
}
