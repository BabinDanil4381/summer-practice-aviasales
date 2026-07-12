package com.example.elements;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.example.core.BaseElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

public class Passengers extends BaseElement {

    private static final Logger log = LoggerFactory.getLogger(Passengers.class);

    private static final String PASSENGERS_FIELD = "//div[@data-test-id='passenger-numbers']";
    private static final String PLUS_BUTTON = "//button[@data-test-id='increase-button']";

    public Passengers() {
        super(PASSENGERS_FIELD, "");
    }

    public Passengers open() {
        log.info("Открытие окна выбора пассажиров");
        baseElement.shouldBe(Condition.visible, Duration.ofSeconds(5)).click();
        Selenide.sleep(500);
        return this;
    }

    public Passengers setAdults(int count) {
        log.info("Установка количества взрослых: {}", count);
        for (int i = 1; i < count; i++) {
            log.info("Нажатие +, шаг {}/{}", i, count - 1);
            $x(PLUS_BUTTON).shouldBe(Condition.visible).click();
            Selenide.sleep(300);
        }
        return this;
    }

    public Passengers apply() {
        log.info("Применение выбора пассажиров");
        
        // Ждем, пока кнопка станет кликабельной, и кликаем через JavaScript
        Selenide.sleep(500);
        Selenide.executeJavaScript(
            "var btn = document.querySelector('button[data-test-id=\"passengers-ready-button\"]');" +
            "if (btn) { btn.click(); }" +
            "else { console.log('Кнопка не найдена'); }"
        );
        
        Selenide.sleep(500);
        return this;
    }

    public String getPassengersText() {
        String text = baseElement.getText();
        log.info("Текст с пассажирами: '{}'", text);
        return text;
    }
}