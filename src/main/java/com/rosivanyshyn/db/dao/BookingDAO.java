package com.rosivanyshyn.db.dao;

import com.rosivanyshyn.db.dao.entity.Booking;

import java.sql.Connection;

public interface BookingDAO extends GenericDAO<Booking> {
    Boolean         createEventIsBillPaid(Connection con, Long id);
}
