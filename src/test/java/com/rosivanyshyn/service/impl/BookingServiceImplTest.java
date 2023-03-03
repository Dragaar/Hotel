package com.rosivanyshyn.service.impl;

import com.rosivanyshyn.db.dao.BookingDAO;
import com.rosivanyshyn.db.dao.entity.Account;
import com.rosivanyshyn.db.dao.entity.Apartment;
import com.rosivanyshyn.db.dao.entity.Booking;
import com.rosivanyshyn.db.manager.DBManager;
import com.rosivanyshyn.exeption.ValidationException;
import org.apache.log4j.Logger;
import org.apache.log4j.helpers.LogLog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

import static com.rosivanyshyn.Constant.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

class BookingServiceImplTest {

    BookingDAO bookingDAO = mock(BookingDAO.class);
    private final DBManager dbManager = mock(DBManager.class);
    private final Connection connection = mock(Connection.class);
    BookingServiceImpl bookingService = new BookingServiceImpl(bookingDAO, dbManager);
    BookingServiceImpl spyBookingService = spy(bookingService);
    Account account = Account.builder().id(ID_VALUE_LONG).build();
    Apartment apartment = Apartment.builder().id(ID_VALUE_LONG).build();

    ArrayList<Booking> bookings = new ArrayList<>();
    HashMap<Date, Date> bookingsDates = new HashMap<>();
    {
      bookings.add(getTestBooking());
      bookingsDates.put(getTestBooking().getCheckInDate(), getTestBooking().getCheckOutDate());
    }



    private Booking getTestBooking(){
        return Booking.builder()
                .id(ID_VALUE_LONG)
                .guestsNumber(GUESTS_NUMBER_VALUE)
                .checkInDate(Date.valueOf(DATE_VALUE))
                .checkOutDate(Date.valueOf(DATE_VALUE2))
                .account(account)
                .apartment(apartment)
                .build();
    }


    @BeforeEach
    void getConnection(){
        when( dbManager.getConnection()).thenReturn(connection);
    }

    @Test
    void createBooking() {
        ArrayList<Booking> didntFindOtherBookings = new ArrayList<>();
        when(bookingDAO.getWithDynamicQuery(isA(Connection.class), isA(String.class), isA(Long.class), isA(Date.class), isA(Date.class)))
                .thenReturn(didntFindOtherBookings);

        when(bookingDAO.insert(isA(Connection.class), isA(Booking.class))).thenReturn(true);
        when(bookingDAO.createEventIsBillPaid(isA(Connection.class), isA(Long.class))).thenReturn(true);
        assertTrue( bookingService.createBooking(getTestBooking()) );
    }

    @Test
    void createBookingValidationException() {

        ArrayList<Booking> didntFindOtherBookings = new ArrayList<>();
        Booking wrongDataBooking = getTestBooking();

        when(bookingDAO.getWithDynamicQuery(isA(Connection.class), isA(String.class), isA(Long.class), isA(Date.class), isA(Date.class)))
                .thenReturn(didntFindOtherBookings);

        when(bookingDAO.insert(isA(Connection.class), isA(Booking.class))).thenReturn(true);
        when(bookingDAO.createEventIsBillPaid(isA(Connection.class), isA(Long.class))).thenReturn(true);

        wrongDataBooking.setCheckInDate(Date.valueOf(INCORRECT_DATE_VALUE));
        assertThrows(ValidationException.class,
                ()-> bookingService.createBooking(wrongDataBooking));

    }

    @Test
    void findFewBookingAndSort() {
        when(bookingDAO.getWithDynamicQuery(isA(Connection.class), isA(String.class))).thenReturn(bookings);
        when(bookingDAO.countRowsInLastQuery(isA(Connection.class))).thenReturn(1);

        assertEquals(bookings,
                bookingService.findFewBookingAndSort("query")
        );
        assertEquals(1, bookingService.getRowsNumber());
    }

    @Test
    void updateBooking() {
        when(bookingDAO.update(isA(Connection.class), isA(Booking.class))).thenReturn(true);
        assertTrue( bookingService.updateBooking(getTestBooking()) );
    }

    @Test
    void deleteBooking() {
        when(bookingDAO.delete(isA(Connection.class), isA(Long.class))).thenReturn(true);
        assertTrue( bookingService.deleteBooking(getTestBooking()) );
    }

    @Test
    void getBookingsDatesFromDB(){

        when(spyBookingService.findFewBookingAndSort(isA(String.class), isA(Long.class),
                                                          isA(java.sql.Date.class), isA(java.sql.Date.class)))
                .thenReturn(bookings);

        assertEquals(bookingsDates, spyBookingService.getBookingsDatesFromDB(ID_VALUE_LONG));
    }
}