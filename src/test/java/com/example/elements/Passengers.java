package com.example.elements;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
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
        clickElement(visibleElement(Duration.ofSeconds(WAIT_SECONDS)));
        return this;
    }

    public Passengers setAdults(int count) {
        log.info("Установка количества взрослых: {}", count);
        for (int i = 1; i < count; i++) {
            log.info("Нажатие +, шаг {}/{}", i, count - 1);
            $x(PLUS_BUTTON).shouldBe(Condition.visible, Duration.ofSeconds(WAIT_SECONDS)).click();
        }
        return this;
    }

    public Passengers apply() {
        log.info("Применение выбора пассажиров");
        dismissOverlayIfPresent();

        try {
            SelenideElement readyButton = findReadyButton();
            if (readyButton != null) {
                clickElement(readyButton);
                return this;
            }
        } catch (Exception ignored) {
            log.debug("Кнопка подтверждения не найдена по основному селектору: {}", ignored.getMessage());
        }

        try {
            SelenideElement passengersField = $x(PASSENGERS_FIELD);
            if (passengersField.exists() && passengersField.isDisplayed()) {
                clickElement(passengersField);
                log.warn("Кнопка подтверждения пассажиров не найдена, закрываю окно повторным кликом по полю");
                return this;
            }
        } catch (Exception ignored) {
            log.debug("Не удалось закрыть окно пассажиров повторным кликом: {}", ignored.getMessage());
        }

        log.warn("Кнопка подтверждения пассажиров не найдена, продолжаем без неё");
        return this;
    }

    private SelenideElement findReadyButton() {
        try {
            SelenideElement dataTestIdButton = Selenide.$x("//button[@data-test-id='passengers-ready-button']");
            if (dataTestIdButton.exists() && dataTestIdButton.isDisplayed()) {
                return dataTestIdButton;
            }
        } catch (Exception ignored) {
            log.debug("Кнопка по data-test-id не найдена");
        }

        try {
            SelenideElement textButton = Selenide.$x(
                    "//button[contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'готово') " +
                            "or contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'ready') " +
                            "or contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'done') " +
                            "or contains(translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'apply')]"
            );
            if (textButton.exists() && textButton.isDisplayed()) {
                return textButton;
            }
        } catch (Exception ignored) {
            log.debug("Кнопка по тексту не найдена");
        }

        return null;
    }

    public String getPassengersText() {
        String text = element().getText();
        log.info("Текст с пассажирами: '{}'", text);
        return text;
    }
}