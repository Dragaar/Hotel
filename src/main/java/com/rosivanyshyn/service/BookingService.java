package com.rosivanyshyn.service;

import com.rosivanyshyn.db.dao.entity.Booking;

import java.util.ArrayList;

public interface BookingService {
    Boolean                  createBooking(Booking booking);
    ArrayList<Booking>       findFewBookingAndSort(String secondQueryPart, Object... fields);

    Boolean                  updateBooking(Booking booking);
    Boolean                  deleteBooking(Booking booking);

    /** Get last statement rows count
     * @return last statement rows count
     */
    int                      getRowsNumber();
}
