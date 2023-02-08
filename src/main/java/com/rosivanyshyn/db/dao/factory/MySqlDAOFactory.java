package com.rosivanyshyn.db.dao.factory;

import com.rosivanyshyn.db.dao.*;
import com.rosivanyshyn.db.dao.implMySQL.*;

/**
 * MySql DAO Factory class.
 *
 * @author Rostyslav Ivanyshyn.
 */
public class MySqlDAOFactory {
    private static MySqlDAOFactory instance;
    private AccountDAO accountDAO;
    private ApartmentDAO apartmentDAO;
    private BookingDAO bookingDAO;
    private OrderDAO orderDAO;
    private ResponseToOrderDAO responseToOrderDAO;

    private MySqlDAOFactory(){}

    /** Get singleton instance of the factory */
    public static synchronized MySqlDAOFactory getInstance() {
        if (instance == null) {
            instance = new MySqlDAOFactory();
        }
        return instance;
    }

    public AccountDAO getAccountDAO() {
        if (accountDAO == null) {
            accountDAO = new AccountDAOImpl();
        }
        return accountDAO;
    }
    public ApartmentDAO getApartmentDAO() {
        if (apartmentDAO == null) {
            apartmentDAO = new ApartmentDAOImpl();
        }
        return apartmentDAO;
    }
    public BookingDAO getBookingDAO() {
        if (bookingDAO == null) {
            bookingDAO = new BookingDAOImpl();
        }
        return bookingDAO;
    }
    public OrderDAO getOrderDAO() {
        if (orderDAO == null) {
            orderDAO = new OrderDAOImpl();
        }
        return orderDAO;
    }
    public ResponseToOrderDAO getResponseToOrderDAO() {
        if (responseToOrderDAO == null) {
            responseToOrderDAO = new ResponseToOrderDAOImpl();
        }
        return responseToOrderDAO;
    }

}
