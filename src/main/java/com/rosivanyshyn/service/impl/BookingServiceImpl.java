package com.rosivanyshyn.service.impl;

import com.rosivanyshyn.db.dao.BookingDAO;
import com.rosivanyshyn.db.dao.entity.Booking;
import com.rosivanyshyn.db.manager.DBManager;
import com.rosivanyshyn.db.transaction.TransactionManager;
import com.rosivanyshyn.service.BookingService;
import com.rosivanyshyn.utils.MySQLQueryBuilder;
import com.rosivanyshyn.utils.Validation;
import lombok.NonNull;

import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import static com.rosivanyshyn.db.dao.constant.Field.*;
import static com.rosivanyshyn.utils.MySQLQueryBuilder.LogicalOperation.EQUAL;
import static com.rosivanyshyn.utils.MySQLQueryBuilder.LogicalOperation.GREATER_OR_EQUAL;

public class BookingServiceImpl implements BookingService {

    BookingDAO bookingDAO;
    DBManager dbManager;
    private int rowsNumber;

    Validation validation = new Validation();
    public BookingServiceImpl(BookingDAO bookingDAO,   DBManager dbManager) {
        this.bookingDAO = bookingDAO;
        this.dbManager = dbManager;
    }

    @Override
    public Boolean createBooking(Booking booking) {
        Connection connection = dbManager.getConnection();

        return  (Boolean) TransactionManager.execute(connection,
            ()-> {
                //get related apartment
                @NonNull Long relatedApartmentId = booking.getApartment().getId();
                //get all existed bookings related to apartment
                @NonNull final ArrayList<Booking> bookings
                    = getBookingsAsPartOfTransaction(connection, relatedApartmentId);
                //get all existed bookings dates (check-in/check-out) related to apartment
                @NonNull final HashMap<Date, Date> bookingsDates
                    = this.getBookingsDates(bookings);

            validation.validateBooking(booking, bookingsDates);

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

    @Override
    public HashMap<Date, Date> getBookingsDatesFromDB(Long apartmentIdOfBookingDates) {
        Date currentDate = Date.valueOf(LocalDate.now());

        ArrayList<Booking> bookings = this.findFewBookingAndSort(
                getQueryForActualBookingsByAccountId(),
                apartmentIdOfBookingDates,
                currentDate,
                currentDate
        );

        return getBookingsDates(bookings);
    }

    /**
     * Get all existed bookings related to apartment. Can be used only inside other transaction!
     * @param connection DB Conndetion
     * @param apartmentId related apartment id
     * @return list of found bookings
     **/
    private ArrayList<Booking> getBookingsAsPartOfTransaction(Connection connection, Long apartmentId) {
        Date currentDate = Date.valueOf(LocalDate.now());

        @NonNull final ArrayList<Booking> bookings
                = bookingDAO.getWithDynamicQuery(
                connection,
                getQueryForActualBookingsByAccountId(),
                apartmentId,
                currentDate,
                currentDate
        );
        return bookings;
    }

    private String getQueryForActualBookingsByAccountId() {
        MySQLQueryBuilder queryBuilder = new MySQLQueryBuilder();
        queryBuilder.setLabel("booking");
        queryBuilder.where(BOOKING_APARTMENT_ID, EQUAL, true);

        //get only actual dates
        queryBuilder.subcondition(true);
        queryBuilder.where(BOOKING_CHECK_IN_DATE, GREATER_OR_EQUAL, true);
        queryBuilder.where(BOOKING_CHECK_OUT_DATE, GREATER_OR_EQUAL, false);
        queryBuilder.subcondition(false);
        return queryBuilder.getQuery();
    }

    private HashMap<Date, Date> getBookingsDates(ArrayList<Booking> bookings) {
        HashMap<Date, Date> bookingsDates = new HashMap<>();
        for(Booking booking : bookings){
            bookingsDates.put(booking.getCheckInDate(), booking.getCheckOutDate());
        }
        return bookingsDates;
    }
}
