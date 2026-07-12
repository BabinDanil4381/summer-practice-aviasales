package com.example.tests;

import com.example.core.BaseTest;
import com.example.pages.AviasalesHotelPage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HotelMaxPriceTest extends BaseTest {

    @Test
    public void shouldFindHotelsCheaperThan10000() {

        AviasalesHotelPage page =
                AviasalesHotelPage.openPage();

        page.enterCity("Москва")
                .clickCheckInDate()
                .selectTodayDate()
                .clickCheckOutDate()
                .selectTomorrowDate()
                .clickSearchHotels()
                .setMaxPrice("10000");

        assertTrue(
                page.getFirstHotelPrice() <= 10000,
                "Первый найденный отель должен стоить не более 10000 рублей"
        );
    }
}