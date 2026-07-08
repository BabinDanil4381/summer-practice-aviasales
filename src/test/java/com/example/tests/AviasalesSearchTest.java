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
        searchPage = searchPage.waitSearchFieldVisible();

        // Шаг 3: Нажать на поле ввода запроса
        searchPage = searchPage.clickSearchField();

        // Шаг 4: Ввести поисковый запрос
        String expectedQuery = "Москва";
        searchPage = searchPage.enterSearchQuery(expectedQuery);

        // Шаг 5: Проверить, что текст в поле совпадает с введённым
        String actualQuery = searchPage.getSearchQuery();
        log.info("Ожидаемый текст: '{}'", expectedQuery);
        log.info("Фактический текст: '{}'", actualQuery);
        assertEquals(expectedQuery, actualQuery,
                "Текст в поле поиска должен совпадать с введённым");

        log.info("--- Тест успешно пройден ---");
    }
}
