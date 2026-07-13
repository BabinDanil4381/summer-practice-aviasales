package com.example.core;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$x;

/**
 * Базовый класс для всех элементов страницы.
 * Каждый элемент определяется через XPath и значение атрибута.
 */
public class BaseElement {

    protected static final Logger log = LoggerFactory.getLogger(BaseElement.class);
    protected static final int WAIT_SECONDS = 10;

    private final String xpathTemplate;
    private final String attributeValue;

    protected BaseElement(String xpath, String attributeValue) {
        this.xpathTemplate = xpath;
        this.attributeValue = attributeValue;
        log.info("Поиск элемента: {}", getXPath());
    }

    protected String getXPath() {
        return String.format(xpathTemplate, attributeValue);
    }

    protected SelenideElement element() {
        return $x(getXPath());
    }

    protected void dismissOverlayIfPresent() {
        try {
            SelenideElement overlay = $x("//div[contains(@data-test-id,'smart-prices-modal') or contains(@class,'smart-prices-modal')]");
            if (overlay.exists()) {
                SelenideElement closeButton = $x("//button[contains(@aria-label,'Close') or contains(@aria-label,'Закрыть') or @data-test-id='modal-close']");
                if (closeButton.exists() && closeButton.isDisplayed()) {
                    closeButton.click();
                } else {
                    Selenide.executeJavaScript(
                            "var modal = document.querySelector('[data-test-id=\"smart-prices-modal\"]'); if (modal) { modal.remove(); }"
                    );
                }
            }
        } catch (Exception ignored) {
            log.debug("Не удалось закрыть overlay: {}", ignored.getMessage());
        }
    }

    protected SelenideElement visibleElement(Duration timeout) {
        dismissOverlayIfPresent();
        return element().shouldBe(Condition.visible, timeout);
    }

    protected void clickElement(SelenideElement target) {
        for (int attempt = 1; attempt <= 3; attempt++) {
            try {
                dismissOverlayIfPresent();
                target.scrollIntoView(true);
                target.shouldBe(Condition.visible, Duration.ofSeconds(WAIT_SECONDS)).click();
                return;
            } catch (Throwable t) {
                if (attempt == 3) {
                    log.warn("Обычный клик не удался, пробую JavaScript-клик");
                    Selenide.executeJavaScript(
                            "arguments[0].scrollIntoView(true); arguments[0].dispatchEvent(new MouseEvent('click', { bubbles: true }));",
                            target
                    );
                    return;
                }
            }
        }
    }

    /**
     * Проверяет, отображён ли элемент на странице.
     */
    public boolean isDisplayed() {
        log.info("Проверка видимости элемента");
        try {
            return visibleElement(Duration.ofSeconds(WAIT_SECONDS)).isDisplayed();
        } catch (Exception e) {
            log.warn("Элемент не отображается: {}", e.getMessage());
            return false;
        }
    }

    public SelenideElement getBaseElement() {
        return element();
    }
}
