package com.rosivanyshyn.exeption;

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

    public static final String INCORRECT_BOOKING_DATA = "validation.error.bookingData.format";
    //general
    public static final String INCORRECT_GUESTS_NUMBER ="validation.error.guestsNumber.format";
    public static final String INCORRECT_DESCRIPTION ="validation.error.description.format";



}
