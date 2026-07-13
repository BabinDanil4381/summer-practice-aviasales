package com.example.core;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Базовый класс для всех страниц приложения.
 * Определяет общую логику: создание страниц, обновление.
 */
public class BasePage {

    protected static final Logger log = LoggerFactory.getLogger(BasePage.class);

    private static final String BASE_ELEMENT_XPATH = "//div[contains(@id,'%s')]";

    protected final SelenideElement basePage;
    protected final Class<? extends BasePage> pageClass;

    protected BasePage(Class<? extends BasePage> pageClass, String type) {
        String xpath = String.format(BASE_ELEMENT_XPATH, type);
        log.info("Инициализация страницы: {} (XPath: {})", pageClass.getSimpleName(), xpath);
        this.basePage = Selenide.$x(xpath);
        this.pageClass = pageClass;
    }

    /**
     * Конструктор для страниц без привязки к конкретному XPath-элементу.
     */
    protected BasePage(Class<? extends BasePage> pageClass) {
        log.info("Инициализация страницы: {}", pageClass.getSimpleName());
        this.pageClass = pageClass;
        this.basePage = null;
    }

    /**
     * Обновить текущую страницу.
     */
    @SuppressWarnings("unchecked")
    public <T extends BasePage> T refresh() {
        log.info("Обновление страницы");
        Selenide.refresh();
        return (T) page(pageClass);
    }

    /**
     * Создать объект страницы по её классу.
     */
    protected <T extends BasePage> T page(Class<T> pageClass) {
        log.info("Переход на страницу: {}", pageClass.getSimpleName());
        try {
            return pageClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Не удалось создать страницу: " + pageClass.getSimpleName(), e);
        }
    }
}
