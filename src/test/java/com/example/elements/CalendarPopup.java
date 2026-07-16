//package com.example.elements;
//
//import static com.codeborne.selenide.Selenide.$x;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.codeborne.selenide.Condition;
//import com.codeborne.selenide.SelenideElement;
//import com.example.core.BaseElement;
//
//import java.time.Duration;
//
///**
// * Всплывающий календарь Aviasales.
// * Появляется после клика на DataPicker.
// */
//public class CalendarPopup extends BaseElement {
//
//    private static final Logger log = LoggerFactory.getLogger(CalendarPopup.class);
//
//    // XPath-константы для элементов внутри календаря
//    private static final String TODAY_BUTTON = "//button[contains(@aria-label, 'Today')]";
//    private static final String TOMORROW_BUTTON =
//        "//button[contains(@aria-label, 'Today')]/following::button[contains(@class, 'day')][1]";
//    private static final String TESTID_XPATH = "//*[@data-test-id='%s']";
//
//    public CalendarPopup() {
//        super(TESTID_XPATH, "dropdown");
//    }
//
//    /**
//     * Дождаться появления календаря.
//     */
//    public CalendarPopup waitVisible() {
//        log.info("Ожидание появления календаря");
//        visibleElement(Duration.ofSeconds(15));
//        return this;
//    }
//
//    /**
//     * Выбрать завтрашнюю дату.
//     */
//    public CalendarPopup selectTomorrow() {
//        log.info("Выбор завтрашней даты");
//        SelenideElement tomorrowButton = $x(TOMORROW_BUTTON);
//        tomorrowButton.shouldBe(Condition.visible, Duration.ofSeconds(15)).click();
//        return this;
//    }
//
//    /**
//     * Выбрать сегодняшнюю дату.
//     */
//    public CalendarPopup selectToday() {
//        log.info("Выбор сегодняшней даты");
//        SelenideElement todayButton = $x(TODAY_BUTTON);
//        todayButton.shouldBe(Condition.visible, Duration.ofSeconds(15)).click();
//        return this;
//    }
//}

package com.example.elements;

import static com.codeborne.selenide.Selenide.$x;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.example.core.BaseElement;

/**
 * Всплывающий календарь Aviasales.
 * Появляется после клика на DataPicker.
 */
public class CalendarPopup extends BaseElement {

    private static final Logger log =
            LoggerFactory.getLogger(CalendarPopup.class);

    private static final String TESTID_XPATH =
            "//*[@data-test-id='%s']";

    private static final String TODAY_BUTTON_XPATH =
            "//button[contains(@aria-label, 'Today')]";

    private static final String TOMORROW_BUTTON_XPATH =
            "//button[contains(@aria-label, 'Today')]/following::button[contains(@class, 'day')][1]";

    public CalendarPopup() {
        super(TESTID_XPATH, "dropdown");
    }

    /**
     * Дождаться появления календаря.
     */
    public CalendarPopup waitVisible() {

        log.info("Ожидание появления календаря");

        baseElement.shouldBe(
                Condition.visible,
                Duration.ofSeconds(15)
        );

        return this;
    }

    /**
     * Выбрать сегодняшнюю дату.
     */
    public CalendarPopup selectToday() {

        log.info("Выбор сегодняшней даты");

        SelenideElement todayButton =
                $x(TODAY_BUTTON_XPATH);

        todayButton.shouldBe(
                Condition.visible,
                Duration.ofSeconds(15)
        ).click();

        return this;
    }

    /**
     * Выбрать завтрашнюю дату.
     */
    public CalendarPopup selectTomorrow() {

        log.info("Выбор завтрашней даты");

        SelenideElement tomorrowButton =
                $x(TOMORROW_BUTTON_XPATH);

        tomorrowButton.shouldBe(
                Condition.visible,
                Duration.ofSeconds(15)
        ).click();

        return this;
    }
}