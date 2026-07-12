package com.example.elements;

import static com.codeborne.selenide.Selenide.$x;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codeborne.selenide.Selenide;
import com.example.core.BaseElement;

/**
 * Всплывающий календарь Aviasales.
 * Появляется после клика на DataPicker.
 */
public class CalendarPopup extends BaseElement {

    private static final Logger log = LoggerFactory.getLogger(CalendarPopup.class);

    // XPath-константы для элементов внутри календаря
    private static final String TODAY_BUTTON = "//button[contains(@aria-label, 'Today')]";
    private static final String TOMORROW_BUTTON =
            "//button[contains(@aria-label, 'Today')]/following::button[contains(@class, 'day')][1]";
    private static final String TESTID_XPATH =
            "//*[@data-test-id='dropdown' or @data-test-id='exact-calendar']";

    public CalendarPopup() {
        super(TESTID_XPATH, "");
    }

    /**
     * Дождаться появления календаря.
     */
    public CalendarPopup waitVisible() {
        log.info("Ожидание появления календаря");
        Selenide.sleep(500);
        baseElement.shouldBe(com.codeborne.selenide.Condition.visible);
        return this;
    }

    /**
     * Выбрать завтрашнюю дату.
     */
    public CalendarPopup selectTomorrow() {
        log.info("Выбор завтрашней даты");
        Selenide.sleep(500);
        $x(TOMORROW_BUTTON).click();
        return this;
    }

    /**
     * Выбрать сегодняшнюю дату.
     */
    public CalendarPopup selectToday() {
        log.info("Выбор сегодняшней даты");
        Selenide.sleep(500);
        $x(TODAY_BUTTON).click();
        return this;
    }
}