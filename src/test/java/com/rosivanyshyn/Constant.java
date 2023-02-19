package com.rosivanyshyn;

import com.rosivanyshyn.exeption.DAOException;
import lombok.NonNull;

import java.time.LocalDate;

public final class Constant {

    //------------------------- Fields names -------------------------------\\
    public static final String ID_FIELD = "id";
    public static final String ROLE_FIELD = "role";
    public static final String APARTMENT_ID_FIELD = "apartmentId";
    public static final String BOOKING_ID_FIELD = "bookingId";
    public static final String BOOKINGS_DATES_FIELD = "bookingsDates";

    public static final String ORDER_ID_FIELD = "orderId";
    public static final String RESPONSE_TO_ORDER_ID_FIELD = "responseToOrderId";

    public static final String EMAIL_FIELD = "email";
    public static final String FIRST_NAME_FIELD = "firstName";
    public static final String LAST_NAME_FIELD= "lastName";
    public static final String PASSWORD_FIELD = "password";


    //Sorting
    public static final String NEW_SORTING_ORDER_FIELD = "newSortingOrder";
    public static final String EXISTING_SORTING_ORDER_FIELD = "existingSortingOrder";
    public static final String SORT_BY_PRICE_FIELD = "price";
    public static final String SORT_BY_MAX_GUESTS_NUMBER_FIELD = "maxGuestsNumber";
    public static final String SORT_BY_CLASS_FIELD = "class";
    public static final String SORT_BY_STATUS_FIELD = "status";

    //------------------------- Fields values -------------------------------\\
    public static final String ID_VALUE = "1";
    public static final Long ID_VALUE_LONG = 1L;

    public static final String EMAIL_VALUE = "some_email@gmail.com";
    public static final String FIRST_NAME_VALUE = "Oleg";
    public static final String LAST_NAME_VALUE = "Ivanov";
    public static final String PASSWORD_VALUE = "some_password";



    //Sorting markers
    public static final String NEW_SORTING_ORDER_VALUE = "exist";
    public static final String EXISTING_SORTING_ORDER_VALUE = "exist";
    //Sorting by status value
    public static final String STATUS_FREE_VALUE = "Free";
    public static final String STATUS_BOOKED_VALUE = "Booked";
    public static final String STATUS_BUSY_VALUE = "Busy";
    public static final String STATUS_UNAVAILABLE_VALUE = "Unavailable";

    //DAO
    public static final DAOException DAO_EXCEPTION = new DAOException("DAO Exeption", new RuntimeException());

    //General values
    public static final int ZERO = 0;
    public static final int ONE = 1;
    public static final int THREE = 3;
    public static final int TEN = 10;

    public static final String ASC = "Asc";
    public static final String DESC = "Desc";

    public static final LocalDate DATE_VALUE = LocalDate.now().plusDays(ONE);
    public static final LocalDate DATE_VALUE2 = LocalDate.now().plusDays(THREE);
    public static final LocalDate INCORRECT_DATE_VALUE = LocalDate.now().minusDays(ONE);

    private Constant() {}
}