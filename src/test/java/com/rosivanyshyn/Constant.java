package com.rosivanyshyn;

import com.rosivanyshyn.exeption.DAOException;

import java.time.LocalDate;

public final class Constant {

    //------------------------- Fields names -------------------------------\\
    public static final String ID_FIELD = "id";
    public static final String ROLE_FIELD = "role";
    public static final String APARTMENT_ID_FIELD = "apartmentId";
    public static final String BOOKING_ID_FIELD = "bookingId";
    public static final String BOOKINGS_DATES_FIELD = "bookingsDates";

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

    public static final String EMAIL_VALUE = "em@em.com";
    public static final String INCORRECT_EMAIL = "em.com";
    public static final String PASSWORD_VALUE = "Password1";
    public static final String INCORRECT_PASSWORD = "Pass1";
    public static final String NAME_VALUE = "Joe";
    public static final String SURNAME_VALUE = "Yi";
    public static final String SPEAKER_NAME = "Joe Yi";
    public static final String ROLE_VISITOR = "VISITOR";
    public static final int ROLE_ID_VALUE = 4;
    public static final String TOPIC_VALUE = "Report topic";
    public static final String NAME_FIELD = "name";
    public static final String EMAIL_FIELD = "email";
    public static final String UPCOMING = "upcoming";
    public static final String PASSED = "passed";

    public static final String TITLE_VALUE = "Event title";
    public static final String TITLE_FIELD = "title";

    public static final String LOCATION_VALUE = "Kyiv";
    public static final String DESCRIPTION_VALUE = "What an awesome event!";
    public static final int REGISTRATIONS_VALUE = 111;
    public static final int VISITORS_VALUE = 99;
    public static final int REPORTS_VALUE = 13;
    private Constant() {}
}