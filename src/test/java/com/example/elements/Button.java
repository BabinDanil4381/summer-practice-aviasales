package com.example.elements;

import com.codeborne.selenide.Selenide;
import com.example.core.BaseElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Элемент страницы «Кнопка».
 * Наследуется от BaseElement.
 */
public class Button extends BaseElement {

    private static final Logger log = LoggerFactory.getLogger(Button.class);

    private static final String TEXT_XPATH = "//button[text()='%s']";
    private static final String TESTID_XPATH = "//button[@data-test-id='%s']";

    private Button(String xpath, String param) {
        super(xpath, param);
    }

    /**
     * Создать кнопку по тексту на ней.
     */
    public static Button byText(String text) {
        log.info("Создание Button по тексту: {}", text);
        return new Button(TEXT_XPATH, text);
    }

    /**
     * Создать кнопку по data-test-id.
     */
    public static Button byTestId(String testId) {
        log.info("Создание Button по data-test-id: {}", testId);
        return new Button(TESTID_XPATH, testId);
    }

    /**
     * Нажать на кнопку.
     */
    public void click() {
        log.info("Нажатие на кнопку");
        try {
            baseElement.click();
        } catch (Exception e) {
            log.warn("Обычный клик перехвачен, использую JavaScript клик");
            Selenide.executeJavaScript("arguments[0].click();", baseElement);
        }
    }
}