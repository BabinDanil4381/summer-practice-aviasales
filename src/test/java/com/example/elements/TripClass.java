package com.example.elements;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.core.BaseElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class TripClass extends BaseElement {

    private static final Logger log = LoggerFactory.getLogger(TripClass.class);

    private static final String TRIP_CLASS_FIELD = "//div[@data-test-id='passenger-numbers']";
    private static final String BUSINESS_RADIO = "//input[@data-test-id='trip-class-C']";

    public TripClass() {
        super(TRIP_CLASS_FIELD, "");
    }

    public TripClass open() {
        log.info("Открытие окна выбора класса");
        baseElement.shouldBe(Condition.visible, Duration.ofSeconds(5)).click();
        Selenide.sleep(500);
        return this;
    }

    public TripClass selectBusiness() {
        log.info("Выбор бизнес-класса");
        
        // кликаем через JavaScript (без проверки на visible)
        Selenide.executeJavaScript(
            "var el = document.querySelector('input[data-test-id=\"trip-class-C\"]');" +
            "if (el) { el.click(); }" +
            "else { console.log('Элемент не найден'); }"
        );
        
        Selenide.sleep(300);
        return this;
    }

    public TripClass apply() {
        log.info("Применение выбора класса");
        Selenide.sleep(500);
        Selenide.executeJavaScript(
            "var btn = document.querySelector('button[data-test-id=\"passengers-ready-button\"]');" +
            "if (btn) { btn.click(); }" +
            "else { console.log('Кнопка не найдена'); }"
        );
        Selenide.sleep(500);
        return this;
    }

    public String getSelectedClassText() {
        log.info("Получение текста с выбранным классом");
        try {
            String text = $x("//div[@data-test-id='trip-class']").getText();
            log.info("Текст с классом: '{}'", text);
            return text;
        } catch (Exception e) {
            log.warn("Не удалось получить текст класса");
            return "";
        }
    }
    
}