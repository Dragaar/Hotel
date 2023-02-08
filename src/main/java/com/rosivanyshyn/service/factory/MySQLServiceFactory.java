package com.rosivanyshyn.service.factory;

import com.rosivanyshyn.db.dao.factory.MySqlDAOFactory;
import com.rosivanyshyn.db.manager.DBManager;
import com.rosivanyshyn.service.*;
import com.rosivanyshyn.service.impl.*;
import lombok.Getter;

@Getter
public class MySQLServiceFactory {

    /** An instance of database manager to provide connection to database */
    private final DBManager dbManager;

    private final AccountService accountService;
    private final ApartmentService apartmentService;
    private final BookingService bookingService;
    private final OrderService orderService;
    private final ResponseToOrderService responseToOrderService;

    private MySQLServiceFactory(MySqlDAOFactory daoFactory, DBManager dbManager){
        accountService =
                new AccountServiceImpl(daoFactory.getAccountDAO(), dbManager);
        apartmentService =
                new ApartmentServiceImpl(daoFactory.getApartmentDAO(), dbManager);
        bookingService =
                new BookingServiceImpl(daoFactory.getBookingDAO(), dbManager);
        orderService =
                new OrderServiceImpl(daoFactory.getOrderDAO(), daoFactory.getResponseToOrderDAO(), dbManager);
        responseToOrderService =
                new ResponseToOrderServiceImpl(daoFactory.getResponseToOrderDAO(), daoFactory.getOrderDAO(), dbManager);
        this.dbManager = dbManager;
    }

    public static MySQLServiceFactory getInstance(MySqlDAOFactory daoFactory, DBManager dbManager) {
        return new MySQLServiceFactory(daoFactory, dbManager);
    }
}
