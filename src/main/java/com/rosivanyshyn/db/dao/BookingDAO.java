package com.rosivanyshyn.db.dao;

import com.rosivanyshyn.db.dao.entity.Booking;

import java.sql.Connection;

/**
 * Booking DAO interface.
 *
 * @author Rostyslav Ivanyshyn.
 */
public interface BookingDAO extends GenericDAO<Booking> {
    /** Create DB event:
     * <br> After the specified period of time,
     * <br> If bill for booking isn`t paid -> delete booking
     *
     * @param con connection to database
     * @param id id of the booking being checked
     * @return Boolean operation result
     */
    Boolean         createEventIsBillPaid(Connection con, Long id);
}
