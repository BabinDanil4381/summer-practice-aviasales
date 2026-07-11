package com.example.pages;

import static com.codeborne.selenide.Selenide.open;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codeborne.selenide.Selenide;
import com.example.core.BasePage;
import com.example.elements.Input;
import com.example.elements.DataPicker;
import com.example.elements.CalendarPopup;

/**
 * Страница поиска Aviasales.
 * Содержит элементы, присутствующие на странице, и методы взаимодействия с ними.
 */
public class AviasalesSearchPage extends BasePage {

    private static final Logger log = LoggerFactory.getLogger(AviasalesSearchPage.class);


    private final Input originInput = Input.byId("avia_form_origin-input");
    private final Input destinationInput = Input.byId("avia_form_destination-input");
    private final DataPicker datePickerStartDate = DataPicker.byTestId("start-date-field");
    private final DataPicker datePickerEndDate = DataPicker.byTestId("end-date-field");
    private CalendarPopup calendarPopup;

    public AviasalesSearchPage() {
        super(AviasalesSearchPage.class);
    }

    /**
     * Открыть страницу Aviasales.
     */
    public static AviasalesSearchPage openPage() {
        log.info("Открытие страницы https://www.aviasales.ru");
        open("https://www.aviasales.ru");

        Selenide.executeJavaScript(
                "window.localStorage.clear(); window.sessionStorage.clear();"
            );

        return new AviasalesSearchPage();
    }

    /**
     * Проверить, что поля отображаются на странице.
     */
    public AviasalesSearchPage waitFieldsVisible() {
        log.info("Ожидание видимости поля поиска");
        originInput.isDisplayed();
        destinationInput.isDisplayed();
        return this;
    }

    /**
     * Нажать на поле "когда".
     */
    public AviasalesSearchPage clickDatePickerStartDate() {
        log.info("Нажатие на поле 'когда'");
        CalendarPopup datePickerPopup = datePickerStartDate.click();
        datePickerPopup.waitVisible();
        this.calendarPopup = datePickerPopup;
        return this;
    }

    /**
     * нажать на пооле "обратно"
     */
    public AviasalesSearchPage clickDatePickerEndDate() {
        log.info("Нажатие на поле 'когда'");
        CalendarPopup datePickerPopup = datePickerEndDate.click();
        datePickerPopup.waitVisible();
        this.calendarPopup = datePickerPopup;
        return this;
    }

    /**
     * Выбрать завтрашнюю дату в календаре.
     */
    public AviasalesSearchPage selectTomorrowDate() {
        log.info("Выбор завтрашней даты в календаре");
        if (calendarPopup == null) {
            throw new IllegalStateException("Календарь не открыт. Сначала вызовите clickDatePicker.");
        }
        calendarPopup.selectTomorrow();
        return this;
    }

    /**
     * выбрать сегодняшнюю дату в календаре
     */
    public AviasalesSearchPage selectTodayDate() {
        log.info("Выбор сегодняшней даты в календаре");
        if (calendarPopup == null) {
            throw new IllegalStateException("Календарь не открыт. Сначала вызовите clickDatePicker.");
        }
        calendarPopup.selectToday();
        return this;
    }


    /**
     * Нажать на поле "откуда".
     * Воспроизводит клик пользователя по полю ввода.
     */
    public AviasalesSearchPage clickOriginField() {
        log.info("Нажатие на поле 'откуда'");
        originInput.click();
        return this;
    }

    /**
     * Нажать на поле "куда".
     * Воспроизводит клик пользователя по полю ввода.
     */
    public AviasalesSearchPage clickDestinationField() {
        log.info("Нажатие на поле 'куда'");
        destinationInput.click();
        return this;
    }

    /**
     * Ввести в поле "откуда".
     */
    public AviasalesSearchPage enterOriginQuery(String query) {
        log.info("Ввод в поле 'откуда': '{}'", query);
        originInput.fill(query);
        return this;
    }

    /**
     * Ввести в поле "куда".
     */
    public AviasalesSearchPage enterDestinationQuery(String query) {
        log.info("Ввод в поле 'куда': '{}'", query);
        destinationInput.fill(query);
        return this;
    }

    /**
     * Получить текущее значение поля "откуда".
     */
    public String getOriginQuery() {
        return originInput.getValue();
    }

    /**
     * Получить текущее значение поля "откуда".
     */
    public String getdestinationQuery() {
        return destinationInput.getValue();
    }
}
