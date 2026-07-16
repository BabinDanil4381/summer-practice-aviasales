package com.example.elements;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import static com.codeborne.selenide.Selenide.$x;
import com.codeborne.selenide.SelenideElement;
import com.example.core.BaseElement;

import java.time.Duration;


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
        SelenideElement input = visibleElement(Duration.ofSeconds(WAIT_SECONDS));
        clickElement(input);
    }

    /** КОСТЫЛЬ ИЗ ЗА РЕАКТА
     * Установить значение через нативный сеттер + input-событие.
     * Работает с React/Vue-контролируемыми полями.
     */
    protected void setValueReact(String value) {
        Selenide.executeJavaScript(
            "var el = arguments[0];" +
            "var setter = Object.getOwnPropertyDescriptor(" +
            "    window.HTMLInputElement.prototype, 'value').set;" +
            "setter.call(el, arguments[1]);" +
            "el.dispatchEvent(new Event('input', { bubbles: true }));",
            element(), value
        );
    }

    /**
     * Ввести текст в поле.
     */
    public void fill(String value) {
        log.info("Ввод текста: '{}'", value);
        SelenideElement input = visibleElement(Duration.ofSeconds(WAIT_SECONDS));
        input.shouldBe(Condition.visible, Duration.ofSeconds(WAIT_SECONDS));
        setValueReact(value);
        input.shouldHave(Condition.value(value), Duration.ofSeconds(WAIT_SECONDS));
    }

    /**
     * Получить текущее значение поля.
     */
    public String getValue() {
        String value = element().getValue();
        log.info("Текущее значение поля: '{}'", value);
        return value;
    }

    /**
     * получить подсказку о том что пунк назначения не вставлен
     */
    public String getValidationMessage() {
        String fullXpath = this.getXPath() + "/preceding-sibling::div[1]";
        SelenideElement hintElement = $x(fullXpath);
        String message = hintElement.getText();
        log.info("Сообщение валидации: '{}'", message);
        return message;
        
    }
}
