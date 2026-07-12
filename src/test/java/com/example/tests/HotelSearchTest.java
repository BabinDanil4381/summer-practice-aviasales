package com.example.tests;

import com.example.core.BaseTest;
import com.example.pages.AviasalesHotelPage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HotelSearchTest extends BaseTest {

    @Test
    public void shouldFindHotelInMoscow() {

        AviasalesHotelPage page =
                AviasalesHotelPage.openPage();

        page.enterCity("Москва")
                .clickCheckInDate()
                .selectTodayDate()
                .clickCheckOutDate()
                .selectTomorrowDate()
                .clickSearchHotels();

        assertTrue(
                page.searchResultsOpened(),
                "Должна открыться страница результатов"
        );

        assertTrue(
                page.hasResults(),
                "Должны отображаться найденные отели"
        );
    }
}