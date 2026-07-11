package com.example.elements;

import com.codeborne.selenide.Selenide;
import com.example.core.BaseElement;
import com.example.elements.CalendarPopup;

import org.openqa.selenium.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Элемент страницы «Поле ввода» (Input).
 * Наследуется от BaseElement.
 */
public class DataPicker extends BaseElement {

    private static final Logger log = LoggerFactory.getLogger(DataPicker.class);

    private static final String ID_XPATH = "//input[@id='%s']";
    private static final String NAME_XPATH = "//input[@name='%s']";
    private static final String TEXTAREA_NAME_XPATH = "//textarea[@name='%s']";
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
     * Воспроизводит нажатие пользователя на элемент.
     * При перехвате клика (например, выпадающим списком) используется JavaScript.
     */
    public CalendarPopup click() {
        log.info("Клик на поле ввода");
        Selenide.sleep(200); // Можно убрать
        try {
            baseElement.click();
        } catch (Exception e) {
            log.warn("Обычный клик перехвачен, использую JavaScript клик");
            Selenide.executeJavaScript("arguments[0].click();", baseElement);
        }
        return new CalendarPopup();
    }

}
