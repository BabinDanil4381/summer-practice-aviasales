package com.example.tests;

import com.example.core.BaseTest;
import com.example.pages.AviasalesSearchPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Тест: Выбор бизнес-класса на Aviasales.
 */
public class AviasalesBusinessClassTest extends BaseTest {

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
}