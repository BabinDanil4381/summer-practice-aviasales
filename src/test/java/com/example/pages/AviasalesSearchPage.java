package com.example.pages;

import static com.codeborne.selenide.Selenide.open;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.core.BasePage;
import com.example.elements.Input;

/**
 * Страница поиска Aviasales.
 * Содержит элементы, присутствующие на странице, и методы взаимодействия с ними.
 */
public class AviasalesSearchPage extends BasePage {

    private static final Logger log = LoggerFactory.getLogger(AviasalesSearchPage.class);


    private final Input searchInput = Input.byId("avia_form_origin-input");

    public AviasalesSearchPage() {
        super(AviasalesSearchPage.class);
    }

    /**
     * Открыть страницу Aviasales.
     */
    public static AviasalesSearchPage openPage() {
        log.info("Открытие страницы https://www.aviasales.ru");
        open("https://www.aviasales.ru");
        return new AviasalesSearchPage();
    }

    /**
     * Проверить, что поле поиска отображается на странице.
     */
    public AviasalesSearchPage waitSearchFieldVisible() {
        log.info("Ожидание видимости поля поиска");
        searchInput.isDisplayed();
        return this;
    }


    /**
     * Нажать на поле поиска.
     * Воспроизводит клик пользователя по полю ввода.
     */
    public AviasalesSearchPage clickSearchField() {
        log.info("Нажатие на поле поиска");
        searchInput.click();
        return this;
    }

    /**
     * Ввести поисковый запрос в поле.
     */
    public AviasalesSearchPage enterSearchQuery(String query) {
        log.info("Ввод поискового запроса: '{}'", query);
        searchInput.fill(query);
        return this;
    }

    /**
     * Получить текущее значение поля поиска.
     */
    public String getSearchQuery() {
        return searchInput.getValue();
    }
}
