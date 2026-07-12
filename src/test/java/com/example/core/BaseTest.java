package com.example.core;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.chrome.ChromeOptions;
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
        Configuration.pageLoadTimeout = 180000;
        Configuration.holdBrowserOpen = true; // что бы браузер не закрывался после тестов
        Configuration.browserSize = "1920x1080";
        // Блокировка геолокации и других разрешений
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.geolocation", 2);  // 2 = блокировать
        options.setExperimentalOption("prefs", prefs);
        Configuration.browserCapabilities = options;
        
        log.info("Браузер: Chrome, таймаут: 10с");
    }

    @AfterEach
    public void after() {
        log.info("=== Закрытие браузера ===");
        Selenide.closeWebDriver();
    }
}
