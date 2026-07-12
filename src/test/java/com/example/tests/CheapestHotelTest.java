package com.example.tests;

import com.example.core.BaseTest;
import com.example.pages.AviasalesHotelPage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheapestHotelTest extends BaseTest {

    @Test
    public void shouldOpenCheapestHotel() {

        AviasalesHotelPage page =
                AviasalesHotelPage.openPage();

        page.enterCity("Москва")
                .clickCheckInDate()
                .selectTodayDate()
                .clickCheckOutDate()
                .selectTomorrowDate()
                .clickSearchHotels()
                .sortByPriceAscending()
                .openFirstHotel();

        assertTrue(
                page.hotelPageOpened(),
                "Должна открыться страница отеля"
        );
    }
}