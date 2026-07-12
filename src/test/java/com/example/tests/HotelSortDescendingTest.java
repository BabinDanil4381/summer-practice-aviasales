package com.example.tests;

import com.example.core.BaseTest;
import com.example.pages.AviasalesHotelPage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HotelSortDescendingTest extends BaseTest {

    @Test
    public void shouldSortHotelsByPriceDescending() {

        AviasalesHotelPage page =
                AviasalesHotelPage.openPage();

        page.enterCity("Москва")
                .clickCheckInDate()
                .selectTodayDate()
                .clickCheckOutDate()
                .selectTomorrowDate()
                .clickSearchHotels()
                .sortByPriceDescending();

        assertTrue(
                page.hasResults(),
                "После сортировки должны отображаться отели"
        );
    }
}