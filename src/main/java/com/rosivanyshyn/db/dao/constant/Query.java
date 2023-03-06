package com.rosivanyshyn.db.dao.constant;

/**
 * MySQL Database queries constants
 *
 * @author Rostyslav Ivanyshyn.
 */
public class Query {
    //-----------------------Account Queries -------------------------------------------------\\
    public static final String INSERT_ACCOUNT = "INSERT INTO account VALUES (DEFAULT, ?, ?, ?, ?, ?, ?)" ;

    public static final String SELECT_ACCOUNT= "SELECT * FROM account WHERE id = ?";
    public static final String SELECT_ALL_ACCOUNTS = "SELECT * FROM account " ;

    public static final String SELECT_FEW_ACCOUNTS = "SELECT * FROM account LIMIT ?, ?" ;

    //use formatter to insert field name
    public static final String SELECT_ACCOUNT_BY_FIELD= "SELECT * FROM account WHERE %s LIKE ?";

    //id isn`t change
    public static final String UPDATE_ACCOUNT = "UPDATE `account` " +
            "SET `role` = ?, `first_name` = ?, `last_name` = ?, `email` = ?, `password` = ?, `state` = ? " +
            "WHERE `id` = ?" ;

    public static final String DELETE_ACCOUNT = "DELETE FROM account WHERE id = ?" ;

    //-----------------------Apartment Queries -------------------------------------------------\\
    public static final String INSERT_APARTMENT = "INSERT INTO apartment VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?)" ;

    public static final String SELECT_APARTMENT= "SELECT * FROM apartment WHERE id = ?";
    public static final String SELECT_ALL_APARTMENTS = "SELECT SQL_CALC_FOUND_ROWS * FROM apartment " ;
    // Select only unique rows
    public static final String UNIQUE_APARTMENTS_WHICH_ARE_BOOKED =
            "INNER JOIN (SELECT DISTINCT `apartment`.`id` FROM apartment INNER JOIN `booking` booking ON booking.`apartment_id` = `apartment`.`id`) AS a2 " +
            "ON `apartment`.`id` = a2.`id` " +
            "%s" ;

    public static final String SELECT_FEW_APARTMENTS = "SELECT SQL_CALC_FOUND_ROWS * FROM apartment LIMIT ?, ?" ;

    //use formatter to insert field name
    public static final String SELECT_APARTMENT_BY_FIELD= "SELECT SQL_CALC_FOUND_ROWS * FROM apartment WHERE %s LIKE ?";

    //id isn`t change
    public static final String UPDATE_APARTMENT = "UPDATE apartment " +
            "SET `title` = ?, `description` = ?, `imageURL` = ?, " +
            "`address` = ?, `max_guests_number` = ?, `rooms_number` = ?, " +
            "`apartment_class` = ?, `price` = ?, `state` = ? " +
            "WHERE `id` = ?";

    public static final String DELETE_APARTMENT = "DELETE FROM apartment WHERE id = ?" ;

    public static final String SEARCH_APARTMENTS = "SELECT SQL_CALC_FOUND_ROWS *, " +
            "MATCH(`title`, `description`, `address`, `apartment_class`) AGAINST(? IN BOOLEAN MODE) AS REL " +
            "FROM hotel.apartment " +
            "WHERE MATCH(`title`, `description`, `address`, `apartment_class`) AGAINST(? IN BOOLEAN MODE) " +
            "ORDER BY REL " +
            "LIMIT ?, ? " ;

    //-----------------------Booking Queries -------------------------------------------------\\
    public static final String INSERT_BOOKING = "INSERT INTO booking VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?)" ;

    public static final String SELECT_BOOKING= "SELECT * FROM booking WHERE id = ?";
    public static final String SELECT_ALL_BOOKINGS = "SELECT SQL_CALC_FOUND_ROWS * FROM booking " ;

    public static final String SELECT_FEW_BOOKINGS = "SELECT SQL_CALC_FOUND_ROWS * FROM booking LIMIT ?, ?" ;


    //use formatter to insert field name
    public static final String SELECT_BOOKING_BY_FIELD= "SELECT SQL_CALC_FOUND_ROWS * FROM booking WHERE %s LIKE ?";

    //id, reservation_data aren`t change
    public static final String UPDATE_BOOKING = "UPDATE `booking` " +
            "SET `guests_number` = ?, `check_in_date` = ?, `check_out_date` = ?, `is_paid_for_reservation` = ?, " +
                 "`account_id` = ?,`apartment_id` = ? " +
            "WHERE `id` = ?";

    public static final String DELETE_BOOKING = "DELETE FROM booking WHERE id = ?" ;

    //-----------------------Order Queries -------------------------------------------------\\
    public static final String INSERT_ORDER = "INSERT INTO `order` VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, DEFAULT)" ;

    public static final String SELECT_ORDER = "SELECT * FROM `order` WHERE id = ?";
    public static final String SELECT_ALL_ORDERS= "SELECT SQL_CALC_FOUND_ROWS * FROM `order` " ;

    public static final String SELECT_FEW_ORDERS = "SELECT SQL_CALC_FOUND_ROWS * FROM `order` LIMIT ?, ?" ;

    //use formatter to insert field name
    public static final String SELECT_ORDER_BY_FIELD= "SELECT SQL_CALC_FOUND_ROWS * FROM `order` WHERE %s LIKE ?";

    //id, account_id aren`t change
    public static final String UPDATE_ORDER = "UPDATE `order` " +
            "SET `guests_number` = ?, `rooms_number` = ?, `apartment_class` = ?, `price` = ?, " +
            "`description` = ?, `check_out_date` = ?, `check_in_date` = ?, `response_to_order_id` = ? " +
            "WHERE `id` = ?";

    public static final String DELETE_ORDER = "DELETE FROM `order` WHERE id = ?" ;

    //-----------------------ResponseToOrder Queries -------------------------------------------------\\
    public static final String INSERT_RESPONSE_TO_ORDER = "INSERT INTO `response_to_order` VALUES (DEFAULT, ?)" ;

    public static final String SELECT_RESPONSE_TO_ORDER= "SELECT * FROM `response_to_order` WHERE id = ?";
    public static final String SELECT_ALL_RESPONSES_TO_ORDER= "SELECT * FROM `response_to_order` " ;

    public static final String SELECT_FEW_RESPONSES_TO_ORDER = "SELECT * FROM `response_to_order` LIMIT ?, ?" ;

    //use formatter to insert field name
    public static final String SELECT_RESPONSE_TO_ORDER_BY_FIELD= "SELECT * FROM `response_to_order` WHERE %s LIKE ?";

    //id isn`t change
    public static final String UPDATE_RESPONSE_TO_ORDER = "UPDATE `response_to_order`" +
            "SET `description` = ? " +
            "WHERE `id` = ? ";
    public static final String DELETE_RESPONSE_TO_ORDER = "DELETE FROM `response_to_order` WHERE id = ?" ;

        //------------- Many to Many
        public static final String INSERT_RESPONSE_TO_ORDER_APARTMENTS= "INSERT INTO `response_to_order_has_apartment` VALUES (?, ?)";

        public static final String SELECT_RESPONSE_TO_ORDER_APARTMENTS =
                "SELECT ap.id, ap.title, ap.description, ap.imageURL, ap.address, ap.max_guests_number, ap.rooms_number, ap.apartment_class, ap.price, ap.state " +
                        "FROM hotel.`response_to_order_has_apartment` as rto_ap " +
                        "JOIN hotel.`apartment` AS ap ON ap.id = rto_ap.apartment_id " +
                        "WHERE rto_ap.response_to_order_id = ?" ;

        public static final String DELETE_RESPONSE_TO_ORDER_APARTMENTS = "DELETE FROM `response_to_order_has_apartment` WHERE response_to_order_id = ?" ;

        //--------------------------------

        public static final String COUNT_ROWS_IN_LAST_QUERY = "SELECT FOUND_ROWS() AS count" ;

        public static final String CREATE_EVENT_IS_BILL_PAID = "CREATE EVENT %s " +
                "ON SCHEDULE AT CURRENT_TIMESTAMP + INTERVAL 1 MINUTE " +
                "DO " +
                "DELETE FROM `hotel`.`booking` WHERE id = ? AND is_paid_for_reservation = 0";
}
