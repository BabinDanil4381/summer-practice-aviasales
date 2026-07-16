package com.example.elements;

import java.time.Duration;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.example.core.BaseElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Поле ввода.
 */
public class Input extends BaseElement {

    private static final Logger log =
            LoggerFactory.getLogger(Input.class);

    private static final String ID_XPATH =
            "//input[@id='%s']";

    private static final String NAME_XPATH =
            "//input[@name='%s']";

    private static final String TEXTAREA_NAME_XPATH =
            "//textarea[@name='%s']";

    private Input(String xpath, String param) {
        super(xpath, param);
    }

    public static Input byId(String id) {
        log.info("Создание Input по id: {}", id);
        return new Input(ID_XPATH, id);
    }

    public static Input byName(String name) {
        log.info("Создание Input по name: {}", name);
        return new Input(NAME_XPATH, name);
    }

    public static Input byTextareaName(String name) {
        log.info("Создание textarea по name: {}", name);
        return new Input(TEXTAREA_NAME_XPATH, name);
    }

    public void click() {
        log.info("Клик по полю");

        try {
            baseElement.click();
        } catch (Exception e) {
            log.warn("Обычный клик перехвачен, использую JavaScript");
            Selenide.executeJavaScript(
                    "arguments[0].click();",
                    baseElement
            );
        }
    }

    /**
     * Для React-контролируемых полей.
     */
    protected void setValueReact(String value) {
        Selenide.executeJavaScript(
                "var el = arguments[0];" +
                        "var setter = Object.getOwnPropertyDescriptor(" +
                        "window.HTMLInputElement.prototype,'value').set;" +
                        "setter.call(el, arguments[1]);" +
                        "el.dispatchEvent(new Event('input',{bubbles:true}));",
                baseElement,
                value
        );
    }

    public void fill(String value) {
        log.info("Ввод текста: {}", value);

        setValueReact(value);

        ElementsCollection options = Selenide.$$x("//*[@role='option']");
        if (options.isEmpty()) {
            return;
        }

        options.shouldBe(CollectionCondition.sizeGreaterThan(0), Duration.ofSeconds(10));

        var match = options.stream()
                .filter(option -> option.$x(".//*[@data-test-id='text']")
                        .getText()
                        .equalsIgnoreCase(value))
                .findFirst()
                .orElse(options.first());

        match.shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    public String getValue() {
        String value = baseElement.getValue();
        log.info("Текущее значение поля: {}", value);
        return value;
    }
}
