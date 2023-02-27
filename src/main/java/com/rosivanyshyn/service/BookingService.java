package com.rosivanyshyn.service;

import com.rosivanyshyn.db.dao.entity.Booking;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Booking Service interface.
 *
 * @author Rostyslav Ivanyshyn.
 */
public interface BookingService {

    /**
     * Create new booking
     * @param booking the booking to be added
     * @return operation result
     */
    Boolean                  createBooking(Booking booking);

    /**
     * Find few bookings using second query part conditions and by fields for them
     * @param secondQueryPart QueryBuilder result string
     * @param fields fields to insert in second query part statement
     * @return found bookings
     */
    ArrayList<Booking>       findFewBookingAndSort(String secondQueryPart, Object... fields);

    /**
     * Update booking
     * @param booking booking to update
     * @return operation result
     */
    Boolean                  updateBooking(Booking booking);

    /**
     * Delete booking
     * @param booking booking to delete
     * @return operation result
     */
    Boolean                  deleteBooking(Booking booking);

    /**
     * Get all booking dates of related apartment
     * @param apartmentIdOfBookingDates related apartment id
     * @return HashMap with check-in-date as key and check-out-date as value
     */
    HashMap<Date, Date>      getBookingsDatesFromDB(Long apartmentIdOfBookingDates);

    /** Get last statement rows count
     * @return last statement rows count
     */
    int                      getRowsNumber();
}
