package com.rosivanyshyn.service.impl;

import com.rosivanyshyn.db.dao.BookingDAO;
import com.rosivanyshyn.db.dao.entity.Booking;
import com.rosivanyshyn.db.manager.DBManager;
import com.rosivanyshyn.db.transaction.TransactionManager;
import com.rosivanyshyn.service.BookingService;

import java.sql.Connection;
import java.util.ArrayList;

public class BookingServiceImpl implements BookingService {

    BookingDAO bookingDAO;
    DBManager dbManager;
    private int rowsNumber;

    public BookingServiceImpl(BookingDAO bookingDAO,   DBManager dbManager) {
        this.bookingDAO = bookingDAO;
        this.dbManager = dbManager;
    }
    @Override
    public Boolean createBooking(Booking booking) {
        Connection connection = dbManager.getConnection();

        return  (Boolean) TransactionManager.execute(connection,
                ()-> {
                if     (bookingDAO.insert(connection, booking))
                return bookingDAO.createEventIsBillPaid(connection, booking.getId());
                else return false;
                }
        );
    }

    @Override
    public ArrayList<Booking> findFewBookingAndSort(String secondQueryPart, Object... fields) {
        Connection connection = dbManager.getConnection();

        //sql start indexing from 0
        @SuppressWarnings("unchecked")
        ArrayList<Booking> result = (ArrayList<Booking>) TransactionManager.execute(connection,
                ()-> {
                ArrayList<Booking> r = bookingDAO.getWithDynamicQuery(connection, secondQueryPart, fields);
                rowsNumber = bookingDAO.countRowsInLastQuery(connection);
                return r;
                }
        );
        return result;
    }
    @Override
    public Boolean updateBooking(Booking booking) {
        Connection connection = dbManager.getConnection();

        return  (Boolean) TransactionManager.execute(connection,
                ()-> bookingDAO.update(connection, booking)
        );
    }

    @Override
    public Boolean deleteBooking(Booking booking) {
        Connection connection = dbManager.getConnection();
        return (Boolean) TransactionManager.execute(connection,
                ()-> bookingDAO.delete(connection, booking.getId())
        );
    }
    @Override
    public int getRowsNumber(){
        return rowsNumber;
    }
}