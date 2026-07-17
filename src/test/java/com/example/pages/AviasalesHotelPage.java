package com.example.pages;

import java.time.Duration;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

import com.example.core.BasePage;
import com.example.elements.CalendarPopup;
import com.example.elements.DataPicker;
import com.example.elements.Input;

import org.openqa.selenium.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.*;

public class AviasalesHotelPage extends BasePage {

    private static final Logger log =
            LoggerFactory.getLogger(AviasalesHotelPage.class);

    private static final String HOTELS_TAB_XPATH =
            "//nav//a[contains(@href, '/hotels') and normalize-space()='Отели']";

    private static final String COOKIE_ACCEPT_BUTTON_XPATH =
            "//button[contains(., 'Да без проблем')]";

    private static final String SEARCH_BUTTON_XPATH =
            "//*[@data-test-id='form-submit']";

    private static final String SORT_DROPDOWN_XPATH =
            "//*[@id='downshift-0-toggle-button']";

    private static final String SORT_ASC_XPATH =
            "//*[@data-test-id='select-option-sort_price_asc']";

    private static final String SORT_DESC_XPATH =
            "//*[@data-test-id='select-option-sort_price_desc']";

    private static final String MAX_PRICE_INPUT_XPATH =
            "//*[@id='range-filter-price_range_inputs-max']";

    private static final String PRICE_XPATH =
            "//*[@data-test-id='text'][contains(text(),'₽')]";

    private static final String CHOOSE_ROOM_XPATH =
            "//div[text()='Выбрать номер']";

    private static final String FIRST_HOTEL_BUTTON_XPATH =
            "(//button[.//div[text()='Выбрать номер']])[1]";

    private static final String FIRST_HOTEL_XPATH =
        "(//button[.//div[text()='Выбрать номер']])[1]";

    private static final String PRICE_PER_NIGHT_XPATH =
            "//*[@data-test-id='text'][contains(., '₽') and contains(., 'за')]";

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
        log.info("Открытие главной страницы Aviasales");
        open("https://www.aviasales.ru");

        dismissCookieBannerIfPresent();
        clickHotelsTab();

        $("#hotel_autocomplete-input")
                .shouldBe(Condition.visible, Duration.ofSeconds(15));

        return new AviasalesHotelPage();
    }

    private static void dismissCookieBannerIfPresent() {
        SelenideElement cookieButton = $x(COOKIE_ACCEPT_BUTTON_XPATH);

        if (cookieButton.exists() && cookieButton.isDisplayed()) {
            log.info("Закрытие cookie-баннера");
            cookieButton.click();
        }
    }

    private static void clickHotelsTab() {
        log.info("Переход в раздел «Отели» через кнопку навигации");

        ElementsCollection hotelsTabs = $$x(HOTELS_TAB_XPATH);
        hotelsTabs.shouldBe(CollectionCondition.sizeGreaterThan(0), Duration.ofSeconds(15));

        hotelsTabs
                .filter(Condition.visible)
                .first()
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .click();
    }


    public void enterCity(String city) {
        log.info("Выбор города: {}", city);
        cityInput.click();
        cityInput.fill(city);

        ElementsCollection options = $$x("//*[@role='option']");
        options.shouldBe(CollectionCondition.sizeGreaterThan(0), Duration.ofSeconds(10));

        SelenideElement match = options.stream()
                .filter(o -> o.$x(".//*[@data-test-id='text']").getText().equalsIgnoreCase(city))
                .findFirst()
                .orElse(options.first());

        match.click();
    }

    public void clickCheckInDate() {
        log.info("Открытие календаря заезда");
        calendarPopup = startDate.click();
        calendarPopup.waitVisible();
    }

    public void clickCheckOutDate() {
        log.info("Открытие календаря выезда");
        calendarPopup = endDate.click();
        calendarPopup.waitVisible();
    }

    public void selectTodayDate() {
        calendarPopup.selectToday();
    }

    public void selectTomorrowDate() {
        calendarPopup.selectTomorrow();
    }

    public void clickSearchHotels() {
        $x(SEARCH_BUTTON_XPATH).shouldBe(Condition.visible, Duration.ofSeconds(10)).click();
    }

    public void sortByPriceAscending() {
        selectSortOption(SORT_ASC_XPATH);
    }

    public void sortByPriceDescending() {
        selectSortOption(SORT_DESC_XPATH);
    }



    private void selectSortOption(String optionXpath) {
        openSortDropdown();
        SelenideElement option = $x(optionXpath);
        option.shouldBe(Condition.visible, Duration.ofSeconds(10));
        log.info( "Выбираем сортировку: {}", option.getText());
        option.click();
        $$x(PRICE_XPATH)
                .filter(Condition.visible)
                .first()
                .shouldBe(Condition.visible,Duration.ofSeconds(30));
    }

    private void openSortDropdown() {
        SelenideElement trigger = $x(SORT_DROPDOWN_XPATH);
        trigger.shouldBe(Condition.visible, Duration.ofSeconds(10));
        executeJavaScript("arguments[0].click();", trigger);
        trigger.shouldHave(Condition.attribute("aria-expanded", "true"), Duration.ofSeconds(5));
    }

    public void setMaxPrice(String price) {
        log.info("Установка максимальной цены {}", price);

        SelenideElement maxPriceInput = $x(MAX_PRICE_INPUT_XPATH);
        maxPriceInput.shouldBe(Condition.visible, Duration.ofSeconds(10));
        maxPriceInput.scrollIntoView("{block:'center'}");

        removeOverlappingElements(maxPriceInput);

        ElementsCollection pricesBefore = $$x(PRICE_XPATH).filter(Condition.visible);
        String priceTextBefore = pricesBefore.size() > 0 ? pricesBefore.first().getText() : "";

        executeJavaScript(
                "var el = arguments[0];" +
                        "var setter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;" +
                        "setter.call(el, '');" +
                        "el.dispatchEvent(new Event('input', { bubbles: true }));",
                maxPriceInput
        );

        maxPriceInput.setValue(price);
        executeJavaScript("arguments[0].blur();", maxPriceInput);

        Selenide.Wait()
                .withTimeout(Duration.ofSeconds(15))
                .until(driver -> {
                    ElementsCollection prices = $$x(PRICE_XPATH).filter(Condition.visible);
                    if (prices.size() == 0) {
                        return true;
                    }
                    String currentText = prices.first().getText();
                    return !currentText.equals(priceTextBefore);
                });
    }


    public int getFirstHotelPrice() {
        ElementsCollection priceElements = $$x(PRICE_XPATH).filter(Condition.visible);
        priceElements.shouldBe(CollectionCondition.sizeGreaterThan(0), Duration.ofSeconds(10));

        String digitsOnly = priceElements.first().getText().replaceAll("[^0-9]", "");
        return Integer.parseInt(digitsOnly);
    }



    public boolean hasResults() {
        try {
            $x("(" + CHOOSE_ROOM_XPATH + ")[1]")
                    .should(Condition.exist, Duration.ofSeconds(15));
            return true;
        } catch (Exception e) {
            log.warn("Результаты поиска не найдены");
            return false;
        }
    }


    public AviasalesHotelPage openFirstHotel() {
        log.info("Открытие самого дешевого отеля");

        SelenideElement firstHotel = $x(FIRST_HOTEL_XPATH);

        firstHotel.shouldBe(Condition.visible, Duration.ofSeconds(15));
        firstHotel.scrollIntoView("{block:'center'}");

        String currentWindow = WebDriverRunner.getWebDriver().getWindowHandle();
        int windowsBefore = WebDriverRunner.getWebDriver().getWindowHandles().size();

        executeJavaScript("arguments[0].click();", firstHotel);

        Selenide.Wait()
                .withTimeout(Duration.ofSeconds(15))
                .until(driver ->
                        driver.getWindowHandles().size() > windowsBefore
                );

        for (String handle : WebDriverRunner.getWebDriver().getWindowHandles()) {
            if (!handle.equals(currentWindow)) {
                WebDriverRunner.getWebDriver()
                    .switchTo()
                    .window(handle);
                break;
            }
        }

        return this;
    }

    public boolean isSortedByPriceAscending() {
        ElementsCollection prices =
                $$x(PRICE_XPATH)
                        .filter(Condition.visible);
        prices.shouldBe(
                CollectionCondition.sizeGreaterThan(1),
                Duration.ofSeconds(15));
        int firstPrice = Integer.parseInt(
                prices.get(0)
                        .getText()
                        .replaceAll("[^0-9]", ""));
        int secondPrice = Integer.parseInt(
                prices.get(1)
                        .getText()
                        .replaceAll("[^0-9]", ""));
        log.info(
                "Проверка сортировки: {} <= {}",
                firstPrice,
                secondPrice);
        return firstPrice <= secondPrice;
    }


    private void removeOverlappingElements(SelenideElement target) {
        executeJavaScript(
                "var btn = arguments[0];" +
                        "var rect = btn.getBoundingClientRect();" +
                        "var cx = rect.left + rect.width / 2;" +
                        "var cy = rect.top + rect.height / 2;" +
                        "var el = document.elementFromPoint(cx, cy);" +
                        "var guard = 0;" +
                        "while (el && el !== btn && !btn.contains(el) && guard < 15) {" +
                        "el.style.setProperty('pointer-events','none','important');" +
                        "el.style.setProperty('display','none','important');" +
                        "el = document.elementFromPoint(cx, cy);" +
                        "guard++;" +
                        "}",
                target
        );
    }

    public boolean hotelPageOpened() {
        return WebDriverRunner.url().matches(".*/hotels/[^/?]+.*");
    }

    public boolean searchResultsOpened() {
        return WebDriverRunner.url().contains("hotels");
    }
}



