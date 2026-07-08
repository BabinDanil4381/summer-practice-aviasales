package com.example.elements;

import com.codeborne.selenide.Selenide;
import com.example.core.BaseElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Элемент страницы «Поле ввода» (Input).
 * Наследуется от BaseElement.
 */
public class Input extends BaseElement {

    private static final Logger log = LoggerFactory.getLogger(Input.class);

    private static final String ID_XPATH = "//input[@id='%s']";
    private static final String NAME_XPATH = "//input[@name='%s']";
    private static final String TEXTAREA_NAME_XPATH = "//textarea[@name='%s']";

    private Input(String xpath, String param) {
        super(xpath, param);
    }

    /**
     * Создать поле ввода по атрибуту id.
     */
    public static Input byId(String id) {
        log.info("Создание Input по id: {}", id);
        return new Input(ID_XPATH, id);
    }

    /**
     * Создать поле ввода по атрибуту name.
     */
    public static Input byName(String name) {
        log.info("Создание Input по name: {}", name);
        return new Input(NAME_XPATH, name);
    }

    /**
     * Создать поле ввода (textarea) по атрибуту name.
     */
    public static Input byTextareaName(String name) {
        log.info("Создание Input (textarea) по name: {}", name);
        return new Input(TEXTAREA_NAME_XPATH, name);
    }

    /**
     * Клик на поле ввода.
     * Воспроизводит нажатие пользователя на элемент.
     * При перехвате клика (например, выпадающим списком) используется JavaScript.
     */
    public void click() {
        log.info("Клик на поле ввода");
        try {
            baseElement.click();
        } catch (Exception e) {
            log.warn("Обычный клик перехвачен, использую JavaScript клик");
            Selenide.executeJavaScript("arguments[0].click();", baseElement);
        }
    }

    /**
     * Ввести текст в поле.
     */
    public void fill(String value) {
        log.info("Ввод текста: '{}'", value);
        baseElement.setValue(value);
    }

    /**
     * Получить текущее значение поля.
     */
    public String getValue() {
        String value = baseElement.getValue();
        log.info("Текущее значение поля: '{}'", value);
        return value;
    }
}
