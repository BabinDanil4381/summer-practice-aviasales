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
        clickElement(visibleElement(Duration.ofSeconds(WAIT_SECONDS)));
        return this;
    }

    public TripClass selectBusiness() {
        log.info("Выбор бизнес-класса");
        Selenide.executeJavaScript(
                """
                (function() {
                    var labels = Array.from(document.querySelectorAll('label'));
                    var label = labels.find(function(el) {
                        if (!el || !el.isConnected) {
                            return false;
                        }
                        var text = (el.textContent || '').trim().toLowerCase();
                        return text.includes('бизнес') || text.includes('business');
                    });
                    if (label) {
                        label.click();
                        return true;
                    }
                    return false;
                })();
                """
        );
        return this;
    }

    public TripClass apply() {
        log.info("Применение выбора класса");
        SelenideElement readyButton = Selenide.$x("//button[@data-test-id='passengers-ready-button']");
        readyButton.shouldBe(Condition.visible, Duration.ofSeconds(WAIT_SECONDS));
        Selenide.executeJavaScript("arguments[0].click();", readyButton);
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