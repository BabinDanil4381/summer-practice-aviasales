package com.example.core;

import com.codeborne.selenide.SelenideElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

/**
 * Базовый класс для всех элементов страницы.
 * Каждый элемент определяется через XPath и значение атрибута.
 */
public class BaseElement {

    protected static final Logger log = LoggerFactory.getLogger(BaseElement.class);
    protected static final int WAIT_SECONDS = 5;

    protected final SelenideElement baseElement;

    protected BaseElement(String xpath, String attributeValue) {
        String fullXpath = String.format(xpath, attributeValue);
        log.info("Поиск элемента: {}", fullXpath);
        this.baseElement = $x(fullXpath);
    }

    /**
     * Проверяет, отображён ли элемент на странице.
     */
    public boolean isDisplayed() {
        log.info("Проверка видимости элемента");
        try {
            return baseElement
                    .shouldBe(visible, Duration.ofSeconds(WAIT_SECONDS))
                    .isDisplayed();
        } catch (Exception e) {
            log.warn("Элемент не отображается: {}", e.getMessage());
            return false;
        }
    }

    public SelenideElement getBaseElement() {
        return baseElement;
    }
}
