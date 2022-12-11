package com.rosivanyshyn.db.dao.constant;

public class Field {
    public static final String ENTITY_ID = "id";
    public static final String ENTITY_STATE = "state";

    public static final String ACCOUNT_ROLE = "role";
    public static final String ACCOUNT_FIRST_NAME = "first_name";
    public static final String ACCOUNT_LAST_NAME = "last_name";
    public static final String ACCOUNT_EMAIL = "email";
    public static final String ACCOUNT_PASSWORD = "password";


    public static final String APARTMENT_TITLE = "title";
    public static final String APARTMENT_DESCRIPTION = "description";
    public static final String APARTMENT_IMAGE_URL = "imageURL";
    public static final String APARTMENT_ADRESS = "address";
    public static final String APARTMENT_MAX_GUEST_NUMBER = "max_guests_number";
    public static final String APARTMENT_ROOMS_NUMBER = "rooms_number";
    public static final String APARTMENT_CLASS = "apartment_class";
    public static final String APARTMENT_PRICE = "price";


    public static final String BOOKING_GUESTS_NUMBER = "guests_number";
    public static final String BOOKING_CHECK_IN_DATE = "check_in_date" ;
    public static final String BOOKING_CHECK_OUT_DATE = "check_out_date";
    public static final String BOOKING_IS_PAID_FOR_RESERVATION = "is_paid_for_reservation";
    public static final String BOOKING_RESERVATION_DATA = "reservation_data";

    public static final String BOOKING_ACCOUNT_ID = "account_id";
    public static final String BOOKING_APARTMENT_ID = "apartment_id";


    public static final String ORDER_GUESTS_NUMBER = "guests_number";
    public static final String ORDER_ROOMS_NUMBER = "rooms_number";
    public static final String ORDER_APARTMENT_CLASS = "apartment_class";
    public static final String ORDER_CHECK_OUT_DATE = "check_out_date";
    public static final String ORDER_CHECK_IN_DATE = "check_in_date";

    public static final String ORDER_ACCOUNT_ID = "account_id";
    public static final String ORDER_RESPONSE_TO_ORDER_ID = "response_to_order_id";


    public static final String RESPONSE_TO_ORDER_DESKR = "description";

    public static final String RESPONSE_TO_ORDER_HAS_APARTMENT = "response_to_order_has_apartment";
    public static final String RESPONSE_TO_ORDER_HAS_APARTMENT_RS_ID = "response_to_order_id";
    public static final String RESPONSE_TO_ORDER_HAS_APARTMENT_AP_ID = "apartment_id";

}
