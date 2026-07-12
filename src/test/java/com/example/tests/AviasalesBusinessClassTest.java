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

        // 1) Открыть страницу
        AviasalesSearchPage searchPage = AviasalesSearchPage.openPage();
        searchPage = searchPage.waitFieldsVisible();

        // 2) Открыть окно пассажиров и выбрать бизнес-класс
        searchPage = searchPage.openPassengers();  // Открываем то же окно
        searchPage = searchPage.selectBusinessClass();  // Выбираем бизнес
        searchPage = searchPage.applyPassengers();  // Закрываем окно

        // 3) Заполнить поле "откуда" — Москва
        searchPage = searchPage.clickOriginField();
        searchPage = searchPage.enterOriginQuery("Москва");

        // 4) Заполнить поле "куда" — Сочи
        searchPage = searchPage.clickDestinationField();
        searchPage = searchPage.enterDestinationQuery("Сочи");

        // 5) Выбрать дату "завтра"
        searchPage = searchPage.clickDatePickerStartDate();
        searchPage.waitFieldsVisible();
        searchPage = searchPage.selectTomorrowDate();

        // 6) Проверить, что в параметрах отображается "Бизнес"
        String classText = searchPage.getTripClassText();  
        log.info("Текст с классом: '{}'", classText);
        assertTrue(classText.contains("Бизнес") || classText.contains("Business"),
                "Должен отображаться выбранный класс: Бизнес. Фактически: " + classText);

        log.info("--- Тест успешно пройден ---");
    }
}