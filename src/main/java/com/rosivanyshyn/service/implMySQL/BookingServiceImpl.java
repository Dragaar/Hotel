package com.rosivanyshyn.service.implMySQL;

import com.rosivanyshyn.db.dao.BookingDAO;
import com.rosivanyshyn.db.dao.entity.Booking;
import com.rosivanyshyn.db.dao.implMySQL.BookingDAOImpl;
import com.rosivanyshyn.db.manager.DBManager;
import com.rosivanyshyn.db.transaction.TransactionManager;
import com.rosivanyshyn.service.BookingService;

import java.sql.Connection;
import java.util.ArrayList;

public class BookingServiceImpl implements BookingService {

    BookingDAO bookingDAO = new BookingDAOImpl();
    @Override
    public Boolean createBooking(Booking booking) {
        Connection connection = DBManager.getConnection();

        return  (Boolean) TransactionManager.execute(connection,
                ()-> bookingDAO.insert(connection, booking)
        );
    }

    @Override
    public Booking findBookingByField(String field, Object value) {
        return null;
    }

    @Override
    public ArrayList<Booking> findAllBooking() {
        return null;
    }

    @Override
    public ArrayList<Booking> findFewBooking(int start, int total) {
        return null;
    }

    @Override
    public Boolean updateBooking(Booking booking) {
        return null;
    }

    @Override
    public Boolean deleteBooking(Booking booking) {
        Connection connection = DBManager.getConnection();
        return (Boolean) TransactionManager.execute(connection,
                ()-> bookingDAO.delete(connection, booking.getId())
        );
    }
}
