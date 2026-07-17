package com.example.tests;

import com.example.core.BaseTest;
import com.example.pages.AviasalesHotelPage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AviasalesHotelTests extends BaseTest {

    @Test
    public void shouldFindHotelInMoscow() {

        AviasalesHotelPage page = AviasalesHotelPage.openPage();

        page.enterCity("Москва");
        page.clickCheckInDate();
        page.selectTodayDate();
        page.clickCheckOutDate();
        page.selectTomorrowDate();
        page.clickSearchHotels();

        assertTrue(
                page.searchResultsOpened(),
                "Должна открыться страница результатов"
        );

        assertTrue(
                page.hasResults(),
                "Должны отображаться найденные отели"
        );
    }

    @Test
    public void shouldSortHotelsByPriceDescending() {

        AviasalesHotelPage page = AviasalesHotelPage.openPage();

        page.enterCity("Москва");
        page.clickCheckInDate();
        page.selectTodayDate();
        page.clickCheckOutDate();
        page.selectTomorrowDate();
        page.clickSearchHotels();
        page.sortByPriceDescending();

        assertTrue(
                page.hasResults(),
                "После сортировки должны отображаться отели"
        );
    }


    @Test
    public void shouldFindHotelsCheaperThan10000() {

        AviasalesHotelPage page = AviasalesHotelPage.openPage();

        page.enterCity("Москва");
        page.clickCheckInDate();
        page.selectTodayDate();
        page.clickCheckOutDate();
        page.selectTomorrowDate();
        page.clickSearchHotels();
        page.setMaxPrice("10000");

        assertTrue(
                page.getFirstHotelPrice() <= 10000,
                "Первый найденный отель должен стоить не более 10000 рублей"
        );
    }

    @Test
    public void shouldOpenCheapestHotel() {

        AviasalesHotelPage page = AviasalesHotelPage.openPage();

        page.enterCity("Москва");
        page.clickCheckInDate();
        page.selectTodayDate();
        page.clickCheckOutDate();
        page.selectTomorrowDate();
        page.clickSearchHotels();
        page.sortByPriceAscending();
        assertTrue(
                page.isSortedByPriceAscending(),
                "Отели должны быть отсортированы по возрастанию цены"
        );
        page.openFirstHotel();
        assertTrue(
                page.hotelPageOpened(),
                "Должна открыться страница самого дешевого отеля"
        );
    }
}
