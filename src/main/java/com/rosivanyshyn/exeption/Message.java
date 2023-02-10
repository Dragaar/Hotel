package com.rosivanyshyn.exeption;

/** Exceptions messages constants.
 *
 * @author Rostyslav Ivanyshyn.
 */
public class Message {
    public static final String INSERT_ERROR = "Cannot create row";
    public static final String SELECT_ERROR = "Cannot find row";
    public static final String SELECT_ALL_ERROR = "Cannot find all rows";

    public static final String SELECT_FEW_ERROR = "Cannot find few rows";
    public static final String SELECT_BY_FIELD_ERROR = "Cannot find row by given field and value";
    public static final String SELECT_DYNAMIC_ERROR = "Cannot find row by given query or fields";

    public static final String UPDATE_ERROR = "Cannot update row by given object";
    public static final String DELETE_ERROR = "Cannot delete row by given id";

    public static final String COUNT_ROWS_ERROR = "Cannot count rows of the last query";
    public static final String MANY_TO_MANY_ERROR = "Cannot operate with ManyToMany";

    public static final String EVENT_IS_BILL_PAID_ERROR = "Cannot create event is-bill-paid by given id";

    //------------------ Application exception constants ------------------------\\

    public static final String ACCOUNT_LOGIN_ERROR = "Cannot log in account";
    public static final String ACCOUNT_LOGOUT_ERROR = "Cannot log out";
    public static final String ACCOUNT_REGISTRATION_ERROR = "Cannot registration account";

    public static final String APARTMENT_DELETE_ERROR = "Cannot delete Apartment";
    public static final String APARTMENT_GET_DETAILS_ERROR ="Cannot get Apartment Details";
    public static final String APARTMENTS_GET_ERROR ="Cannot get Apartments";

    public static final String BOOKING_CREATE_ERROR = "Cannot booking apartment";
    public static final String BOOKING_DELETE_ERROR = "Cannot delete booking";
    public static final String BOOKINGS_GET_ALL_ERROR = "Cannot get All Bookings";
    public static final String BOOKING_GET_CREATE_PAGE_ERROR = "Cannot get create-booking page";
    public static final String BOOKINGS_GET_ERROR = "Cannot get Bookings";
    public static final String BOOKING_PAYMENT_ERROR = "Cannot make payment for booking";

    public static final String ORDER_CREATE_ERROR = "Cannot create order";
    public static final String ORDER_DELETE_ERROR = "Cannot delete order";
    public static final String ORDERS_GET_ALL_ERROR = "Cannot get All Orders";
    public static final String ORDER_GET_CREATE_PAGE_ERROR = "Cannot get create-order page";
    public static final String ORDERS_GET_ERROR = "Cannot get Orders";

    public static final String RESPONSE_TO_ORDER_CREATE_ERROR = "Cannot create response to order";
    public static final String RESPONSE_TO_ORDER_GET_CREATE_PAGE_ERROR = "Cannot get create-response to order page";
    public static final String RESPONSE_TO_ORDER_GET_ERROR = "Cannot get Response to order";

    public static final String LANGUAGE_CHANGE_ERROR ="Cannot change language";


    //------------------ Validation locale constants ------------------------\\

    public static final String INCORRECT_ACCOUNT_ROLE = "validation.error.role.format";
    public static final String INCORRECT_ACCOUNT_FIRST_NAME = "validation.error.firstName.format";
    public static final String INCORRECT_ACCOUNT_LAST_NAME = "validation.error.lastName.format";
    public static final String INCORRECT_ACCOUNT_EMAIL = "validation.error.email.format";
    public static final String INCORRECT_ACCOUNT_PASSWORD = "validation.error.password.format";


    public static final String INCORRECT_APARTMENT_TITLE ="validation.error.title.format";
    public static final String INCORRECT_APARTMENT_IMAGE ="validation.error.image.format";
    public static final String INCORRECT_APARTMENT_ADDRESS ="validation.error.address.format";
    public static final String INCORRECT_APARTMENT_MAX_GUESTS_NUMBER ="validation.error.maxGuestsNumber.format";
    public static final String INCORRECT_APARTMENT_PRICE ="validation.error.price.format";
    public static final String INCORRECT_APARTMENT_ROOMS_NUMBER ="validation.error.roomsNumber.format";
    public static final String INCORRECT_APARTMENT_CLASS ="validation.error.apartmentClass.format";

    public static final String INCORRECT_BOOKING_DATE = "validation.error.bookingDate.format";
    public static final String BOOKING_ALREADY_EXIST_ON_THIS_DATE = "validation.error.bookingAlreadyExistOnThisDate";
    //general
    public static final String INCORRECT_GUESTS_NUMBER ="validation.error.guestsNumber.format";
    public static final String INCORRECT_DESCRIPTION ="validation.error.description.format";


    public static final String INCORRECT_SORTING_ORDER ="Input sorting order doesn`t exist";

    public static final String ACCOUNT_ISNT_EXIST= "Account isn`t exist!";



}
