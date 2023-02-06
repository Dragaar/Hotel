package com.rosivanyshyn.controller.dispatcher;

/**
 * Controller Constant class. It contains views paths, security config paths and controllers names
 *
 * @author Rostyslav Ivanyshyn.
 */
public class ControllerConstant {

    //---------------- JSP Path -------------------\\
    public static final String INDEX_JSP = "/index.jsp";
    public static final String ERROR_JSP = "/pages/error.jsp";

    public static final String LOGIN_JSP = "/pages/login.jsp";

    public static final String APARTMENTS_JSP = "/pages/apartments.jsp";
    //public static final String APARTMENTS_JSP = "/pages/test11.jsp";
    public static final String APARTMENT_DETAILS_JSP = "/pages/apartment.jsp";


    public static final String ALL_BOOKINGS_JSP = "/pages/booking/allBookings.jsp";
    public static final String BOOKINGS_JSP = "/pages/booking/bookings.jsp";
    public static final String NEW_BOOKING_JSP = "/pages/booking/newBooking.jsp";

    public static final String ALL_ORDERS_JSP = "/pages/order/allOrders.jsp";
    public static final String ORDERS_JSP = "/pages/order/orders.jsp";
    public static final String NEW_ORDER_JSP = "/pages/order/newOrder.jsp";

    public static final String RESPONSE_TO_ORDER_JSP = "/pages/responseToOrder/responseToOrder.jsp";

    public static final String NEW_RESPONSE_TO_ORDER_JSP = "/pages/responseToOrder/newResponseToOrder.jsp";

    //------------- Dispatcher Servlet -----------\\
    public static final String CONTROLLER_NAME = "controller";

    //------------- Security -----------\\
    public static final String SECURITY = "security";

    public static final String SECURITY_PATH = "/WEB-INF/security/security.xml";
    public static final String FEATURE_TURN_VALIDATION_ON = "http://xml.org/sax/features/validation";
    public static final String FEATURE_TURN_SCHEMA_VALIDATION_ON = "http://apache.org/xml/features/validation/schema";

    //------------- Controller Identification ------------\\
    public static final String LOGIN_CONTROLLER = "login";
    public static final String LOGOUT_CONTROLLER ="logout";
    public static final String REGISTRATION_CONTROLLER = "registration";

    public static final String GET_APARTMENTS_CONTROLLER = "getApartments";
    public static final String GET_APARTMENT_DETAILS_CONTROLLER = "getApartmentDetails";
    public static final String DELETE_APARTMENT_CONTROLLER = "deleteApartment";

    public static final String GET_BOOKINGS_CONTROLLER = "getBookings";
    public static final String GET_ALL_BOOKINGS_CONTROLLER = "getAllBookings";
    public static final String NEW_BOOKING_CONTROLLER = "newBooking";
    public static final String CREATE_BOOKING_CONTROLLER = "createBooking";
    public static final String MAKE_PAYMENT_FOR_BOOKING = "makePaymentForBooking";
    public static final String DELETE_BOOKING_CONTROLLER = "deleteBooking";


    public static final String GET_ORDERS_CONTROLLER = "getOrders";
    public static final String GET_ALL_ORDERS_CONTROLLER = "getAllOrders";
    public static final String NEW_ORDER_CONTROLLER = "newOrder";
    public static final String CREATE_ORDER_CONTROLLER = "createOrder";
    public static final String DELETE_ORDER_CONTROLLER = "deleteOrder";


    public static final String GET_RESPONSE_TO_ORDER_CONTROLLER = "getResponseToOrder";
    public static final String NEW_RESPONSE_TO_ORDER_CONTROLLER = "newResponseToOrder";
    public static final String CREATE_RESPONSE_TO_ORDER_CONTROLLER = "createResponseToOrder";


    public static final String CHANGE_LANGUAGE_CONTROLLER = "changeLanguage";

}
