package com.example.elements;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.core.BaseElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/**
 * Наследуется от BaseElement.
 */
public class DataPicker extends BaseElement {

    private static final Logger log = LoggerFactory.getLogger(DataPicker.class);


    private static final String TESTID_XPATH = "//*[@data-test-id='%s']";

    private DataPicker(String xpath, String param) {
        super(xpath, param);
    }

    /**
     * Создать поле ввода по атрибуту class.
     */
    public static DataPicker byTestId(String testId) {
    log.info("Создание DataPicker по data-testid: {}", testId);
    return new DataPicker(TESTID_XPATH, testId);
}

    /**
     * Клик на поле ввода.
     */
    public CalendarPopup click() {
        log.info("Клик на поле ввода");
        SelenideElement picker = visibleElement(Duration.ofSeconds(WAIT_SECONDS));
        clickElement(picker);
        return new CalendarPopup();
    }

    /**
     * Получить текст из поля ввода.
     */
    public String getDisplayedDate() {
        log.info("Получение отображаемой даты из DataPicker");
        String value = this.getBaseElement().$("div").$("div").getText();
        log.info("Дата в поле: '{}'", value);
        return value;
    }

}
