package com.example.pages;
import java.time.Duration;

import com.codeborne.selenide.*;
import org.openqa.selenium.interactions.Actions;
import java.util.List;
import com.codeborne.selenide.Condition;
import com.example.core.BasePage;
import com.example.elements.CalendarPopup;
import com.example.elements.DataPicker;
import com.example.elements.Input;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.*;

public class AviasalesHotelPage extends BasePage {

    private static final Logger log =
            LoggerFactory.getLogger(AviasalesHotelPage.class);

    private final Input cityInput =
            Input.byId("hotel_autocomplete-input");

    private final DataPicker startDate =
            DataPicker.byTestId("start-date-field");

    private final DataPicker endDate =
            DataPicker.byTestId("end-date-field");

    private CalendarPopup calendarPopup;

    public AviasalesHotelPage() {
        super(AviasalesHotelPage.class);
    }

    public static AviasalesHotelPage openPage() {
        log.info("Открытие страницы отелей");
        open("https://www.aviasales.ru/hotels");
        return new AviasalesHotelPage();
    }

    public AviasalesHotelPage enterCity(String city) {
        log.info("Выбор города: {}", city);
        cityInput.click();
        cityInput.fill(city);
        return this;
    }

    public AviasalesHotelPage clickCheckInDate() {
        log.info("Открытие календаря заезда");
        calendarPopup = startDate.click();
        calendarPopup.waitVisible();
        return this;
    }

    public AviasalesHotelPage clickCheckOutDate() {
        log.info("Открытие календаря выезда");
        calendarPopup = endDate.click();
        calendarPopup.waitVisible();
        return this;
    }

    public AviasalesHotelPage selectTodayDate() {
        log.info("Выбор сегодняшней даты");
        calendarPopup.selectToday();
        return this;
    }

    public AviasalesHotelPage selectTomorrowDate() {
        log.info("Выбор завтрашней даты");
        calendarPopup.selectTomorrow();
        return this;
    }

    public AviasalesHotelPage clickSearchHotels() {
        log.info("Нажатие кнопки поиска");
        $x("//*[@data-test-id='form-submit']").click();
        return this;
    }

    public AviasalesHotelPage sortByPriceAscending() {
        log.info("Сортировка по возрастанию цены");
        openSortDropdown();
        SelenideElement option = $x("//*[@data-test-id='select-option-sort_price_asc']");
        option.shouldBe(Condition.visible, Duration.ofSeconds(10));
        Selenide.executeJavaScript("arguments[0].click();", option);

        Selenide.sleep(1500); // даём списку время пересортироваться и перерендериться

        return this;
    }

    public AviasalesHotelPage sortByPriceDescending() {
        log.info("Сортировка по убыванию цены");
        openSortDropdown();
        SelenideElement option = $x("//*[@data-test-id='select-option-sort_price_desc']");
        option.shouldBe(Condition.visible, Duration.ofSeconds(10));
        Selenide.executeJavaScript("arguments[0].click();", option);

        Selenide.sleep(1500);

        return this;
    }


    private void openSortDropdown() {
        SelenideElement trigger = $x("//*[@id='downshift-0-toggle-button']");
        trigger.shouldBe(Condition.visible, Duration.ofSeconds(10));
        Selenide.executeJavaScript("arguments[0].click();", trigger);
        trigger.shouldHave(Condition.attribute("aria-expanded", "true"), Duration.ofSeconds(5));
    }

    public AviasalesHotelPage setMaxPrice(String price) {
        log.info("Установка максимальной цены {}", price);

        SelenideElement maxPriceInput =
                $x("//*[@id='range-filter-price_range_inputs-max']");

        maxPriceInput.clear();
        maxPriceInput.setValue(price);

        // Кликаем куда-нибудь мимо поля, чтобы сработал blur
        $x("//body").click();

        return this;
    }

    public boolean hasResults() {
        try {
            $x("(//div[text()='Выбрать номер'])[1]")
                    .should(Condition.exist, Duration.ofSeconds(15));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public int getFirstHotelPrice() {

        ElementsCollection priceElements =
                $$x("//*[@data-test-id='text'][contains(text(),'₽')]");

        SelenideElement priceEl = priceElements
                .filter(Condition.visible)
                .first();

        priceEl.shouldBe(Condition.visible, Duration.ofSeconds(10));

        String text = priceEl.getText()
                .replace("₽", "")
                .replace(" ", "")
                .replace("\u202F", "")
                .trim();

        return Integer.parseInt(text);
    }

    public AviasalesHotelPage openFirstHotel() {

        log.info("Открытие первого найденного отеля");

        // Даём списку время пересортироваться и перерендериться после смены сортировки
        Selenide.sleep(1000);

        SelenideElement firstHotelButton =
                $x("(//button[.//div[text()='Выбрать номер']])[1]");

        firstHotelButton.shouldBe(Condition.visible, Duration.ofSeconds(10));
        firstHotelButton.scrollIntoView("{block: 'center'}");
        Selenide.sleep(300);

        Selenide.executeJavaScript(
                "var btn = arguments[0];" +
                        "var rect = btn.getBoundingClientRect();" +
                        "var cx = rect.left + rect.width / 2;" +
                        "var cy = rect.top + rect.height / 2;" +
                        "var el = document.elementFromPoint(cx, cy);" +
                        "var guard = 0;" +
                        "while (el && el !== btn && !btn.contains(el) && guard < 15) {" +
                        "  el.style.setProperty('pointer-events', 'none', 'important');" +
                        "  el.style.setProperty('display', 'none', 'important');" +
                        "  el = document.elementFromPoint(cx, cy);" +
                        "  guard++;" +
                        "}",
                firstHotelButton
        );

        String currentWindow = WebDriverRunner.getWebDriver().getWindowHandle();
        int windowsBefore = WebDriverRunner.getWebDriver().getWindowHandles().size();

        firstHotelButton.click();

        Selenide.Wait().withTimeout(Duration.ofSeconds(10))
                .until(d -> d.getWindowHandles().size() > windowsBefore
                        || !d.getCurrentUrl().contains("/hotels"));

        if (WebDriverRunner.getWebDriver().getWindowHandles().size() > windowsBefore) {
            for (String handle : WebDriverRunner.getWebDriver().getWindowHandles()) {
                if (!handle.equals(currentWindow)) {
                    WebDriverRunner.getWebDriver().switchTo().window(handle);
                    break;
                }
            }
        }

        return this;
    }

    public boolean hotelPageOpened() {
        return !WebDriverRunner.url().contains("/hotels");
    }

    public boolean searchResultsOpened() {
        return WebDriverRunner.url().contains("hotels");
    }
}