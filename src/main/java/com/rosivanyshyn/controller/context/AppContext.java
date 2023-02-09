package com.rosivanyshyn.controller.context;

import com.rosivanyshyn.db.dao.factory.MySqlDAOFactory;
import com.rosivanyshyn.db.manager.MySQLDBManagerImpl;
import com.rosivanyshyn.service.*;
import com.rosivanyshyn.service.factory.MySQLServiceFactory;
import lombok.*;
import org.slf4j.*;

/**
 * App Context class.
 * <br> Contains all required to correct application work objects.
 *
 * @author Rostyslav Ivanyshyn.
 */
@Getter
public class AppContext {
    private static final Logger logger = LoggerFactory.getLogger(AppContext.class);
    private static AppContext instance;
    private final AccountService accountService;
    private final ApartmentService apartmentService;
    private final BookingService bookingService;
    private final OrderService orderService;
    private final ResponseToOrderService responseToOrderService;

    private AppContext() {
        MySQLDBManagerImpl dbManager = MySQLDBManagerImpl.getInstance();
        MySqlDAOFactory daoFactory = MySqlDAOFactory.getInstance();
        MySQLServiceFactory serviceFactory = MySQLServiceFactory.getInstance(daoFactory, dbManager);

        accountService =  serviceFactory.getAccountService();
        apartmentService = serviceFactory.getApartmentService();
        bookingService = serviceFactory.getBookingService();
        orderService = serviceFactory.getOrderService();
        responseToOrderService = serviceFactory.getResponseToOrderService();
    }

    /**
     * @return AppContext instance
     */
    public static AppContext getInstance() {
        return instance;
    }

    /**
     * Creates AppContext instance to use in Actions. Configure all required classes.
     */
    public static void createInstance() {
        instance = new AppContext();
    }
}