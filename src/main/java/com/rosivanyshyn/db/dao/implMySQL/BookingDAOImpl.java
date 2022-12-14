package com.rosivanyshyn.db.dao.implMySQL;

import com.rosivanyshyn.db.dao.ApartmentDAO;
import com.rosivanyshyn.db.dao.BookingDAO;
import com.rosivanyshyn.db.dao.entity.Account;
import com.rosivanyshyn.db.dao.entity.Apartment;
import com.rosivanyshyn.db.dao.entity.Booking;
import com.rosivanyshyn.db.dao.entity.Order;
import com.rosivanyshyn.db.manager.DBManager;

import java.sql.Connection;
import java.sql.Timestamp;

import static com.rosivanyshyn.db.dao.constant.Field.*;
import static com.rosivanyshyn.db.dao.constant.Query.*;

public class BookingDAOImpl extends GenericDAOImpl<Booking> implements BookingDAO {
    //------------------ Queries initialising ------------------------\\
    @Override
    String insertQuery() { return INSERT_BOOKING; }
    @Override
    String selectQuery() { return SELECT_BOOKING; }
    @Override
    String selectAllQuery() { return SELECT_ALL_BOOKINGS; }
    @Override
    String selectFewQuery() { return SELECT_FEW_BOOKINGS; }
    @Override
    String selectByFieldQuery() { return SELECT_BOOKING_BY_FIELD; }
    @Override
    String updateQuery() { return UPDATE_BOOKING; }
    @Override
    String deleteQuery() { return DELETE_BOOKING; }

    //------------------ DBStatementOperations initialising ------------------------\\
    @Override
    DBStatementOperations<Booking> insertOperations() {
        return (stmt, entity) -> {
            stmt.setString(1, entity.getGuestsNumber());
            stmt.setDate(2, entity.getCheckInDate());
            stmt.setDate(3, entity.getCheckOutDate());
            stmt.setBoolean(4, entity.getIsPaidForReservation());
            //ReservationData auto-generated *Get from BD as 1 query impossible
            entity.setReservationData(new Timestamp(System.currentTimeMillis()));
            stmt.setTimestamp(5, entity.getReservationData());

            stmt.setLong(6, entity.getAccount().getId());
            stmt.setLong(7, entity.getApartment().getId());
        };
    }

    @Override
    DBStatementOperations<Booking> updateOperations() {
        return (stmt, entity) -> {
            stmt.setString(1, entity.getGuestsNumber());
            stmt.setDate(2, entity.getCheckInDate());
            stmt.setDate(3, entity.getCheckOutDate());
            stmt.setBoolean(4, entity.getIsPaidForReservation());
            //ReservationData unchanged

            stmt.setLong(5, entity.getAccount().getId());
            stmt.setLong(6, entity.getApartment().getId());

            stmt.setLong(7, entity.getId());
        };
    }
    //------------------ ExtractEntity initialising ------------------------\\
    @Override
    SetGeneratedValuesToEntity<Booking> setGeneratedValuesToEntity() {
        return (rs, entity) -> {
            entity.setId((long) rs.getInt(1));
        };
    }
    @Override
    ExtractEntity<Booking> entityFromGet() {
         return (rs) -> Booking.builder()
                    .id(rs.getLong(ENTITY_ID))
                    .guestsNumber(rs.getString(BOOKING_GUESTS_NUMBER))
                    .checkInDate(rs.getDate(BOOKING_CHECK_IN_DATE))
                    .checkOutDate(rs.getDate(BOOKING_CHECK_OUT_DATE))
                    .isPaidForReservation(rs.getBoolean(BOOKING_IS_PAID_FOR_RESERVATION))
                    .reservationData(rs.getTimestamp(BOOKING_RESERVATION_DATA))
                    //foreign keys
                    .account(
                            geAccountForeignKey(rs.getLong(BOOKING_ACCOUNT_ID))
                            )
                    .apartment(
                            getApartmentForeignKey(rs.getLong(BOOKING_APARTMENT_ID))
                            )
                    .build();
    }

    //foreign key
    private Account geAccountForeignKey(Long id){
        Connection connection= DBManager.getConnection();
        AccountDAOImpl accountDAO = new AccountDAOImpl();

        return accountDAO.get(connection, id);
    }
    //foreign key
    private Apartment getApartmentForeignKey(Long id){
        Connection connection= DBManager.getConnection();
        ApartmentDAOImpl apartmentDAO = new ApartmentDAOImpl();

        return apartmentDAO.get(connection, id);
    }



}