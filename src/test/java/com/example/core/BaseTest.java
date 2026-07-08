package com.example.core;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Базовый класс для всех тестов.
 * Выполняет настройку браузера перед каждым тестом
 * и закрытие браузера после каждого теста.
 */
public class BaseTest {

    protected static final Logger log = LoggerFactory.getLogger(BaseTest.class);

    @BeforeEach
    public void before() {
        log.info("=== Настройка браузера ===");
        Configuration.browser = "chrome";
        Configuration.savePageSource = false;
        Configuration.timeout = 10000;
        Configuration.holdBrowserOpen = true; // что бы браузер не закрывался после тестов
        log.info("Браузер: Chrome, таймаут: 10с");
    }

    @AfterEach
    public void after() {
        log.info("=== Закрытие браузера ===");
        Selenide.closeWebDriver();
    }
}
