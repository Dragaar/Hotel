package com.rosivanyshyn.service;

import com.rosivanyshyn.db.dao.entity.Account;
import com.rosivanyshyn.db.dao.entity.Apartment;
import com.rosivanyshyn.db.dao.entity.Booking;

import java.util.ArrayList;

public interface BookingService {
    Boolean                  createBooking(Booking booking);

    Booking                  findBookingByField(String field, Object value);
    ArrayList<Booking>       findAllBooking();
    ArrayList<Booking>       findFewBooking(int start, int total);

    Boolean                  updateBooking(Booking booking);
    Boolean                  deleteBooking(Booking booking);
}
