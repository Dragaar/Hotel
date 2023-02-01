package com.rosivanyshyn.utils;

import com.rosivanyshyn.controller.authorization.filter.AccessFilter;
import com.rosivanyshyn.db.dao.constant.AccountRole;
import com.rosivanyshyn.db.dao.entity.*;
import com.rosivanyshyn.exeption.ValidationException;
import org.apache.log4j.Logger;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.rosivanyshyn.exeption.Message.*;

public class Validation {

    private static final Logger LOG = Logger.getLogger(Validation.class);

    /**
     * Check if user entered valid number.
     *
     * @param number
     *            user input to check
     * @return true if entered text is valid number, false otherwise
     */
    public boolean isValidNumber(final String number) {
        if (number == null || number.isEmpty()) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[0-9]+$");
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }

    /**
     * Check if user entered valid number.
     *
     * @param number
     *            user input to check
     * @return true if entered text is valid number, false otherwise
     */
    public boolean isValidNumber(final Integer number) {
        if (number == null || number <= 0) {
            return false;
        }
        return true;
    }
    /**
     * Check if user entered valid number.
     *
     * @param number
     *            user input to check
     * @return true if entered text is valid number, false otherwise
     */
    public boolean isValidNumber(final Long number) {
        if (number == null || number <= 0) {
            return false;
        }
        return true;
    }
    /**
     * Check if user entered data has string format.
     *
     * @param string
     *            string to check
     * @return true, if string has valid format
     */
    public boolean isValidString(final String string) {
        if (string == null || string.isEmpty()) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[a-zA-Z\u0430-\u044F\u0410-\u042F\u0451\u0401\\s]{3,}$");
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }
    /**
     * Check if user entered data has description format.
     *
     * @param string
     *            string to check
     * @return true, if string has valid format
     */
    public boolean isValidDescription(final String string) {
        if (string == null || string.isEmpty()) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[a-zA-Z\u0430-\u044F\u0410-\u042F\u0451\u0401\\d\\s\\-'.,;:+~`!?@#$^&*()={}|/]{3,}$");
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }

    /**
     * Check if user entered data has date format.
     *
     * @param date
     *            date to check
     * @return true, if data has date format
     */
    public boolean isValidDate(final String date) {
        if (date == null || date.isEmpty()) {
            return false;
        }
        Pattern pattern = Pattern.compile(
                "^(?:19|20)[0-9]{2}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-9])|(?:(?!02)(?:0[1-9]|1[0-2])-(?:30))|(?:(?:0[13578]|1[02])-31))$");
        Matcher matcher = pattern.matcher(date);

        return matcher.matches();
    }

    /**
     * Check if second period-of-time does not intersect first
     *
     * @param fCheckInData
     *             start of first period of time
     * @param fCheckOutData
     *              end of first period of time
     * @param sCheckInData
     *            start of second period of time
     * @param sCheckOutData
     *           end of second period of time
     * @return true, if second period does not intersect first
     */
    public boolean comparisonDataValidator(final Date fCheckInData, final Date fCheckOutData,
                                          final Date sCheckInData, final Date sCheckOutData) {
    LocalDate localFCheckInData = fCheckInData.toLocalDate();
    LocalDate localFCheckOutData = fCheckOutData.toLocalDate();
    LocalDate localSCheckInData = sCheckInData.toLocalDate();
    LocalDate localSCheckOutData = sCheckOutData.toLocalDate();
        //якщо дата виїзду раніше дати заїзду
        if (localSCheckOutData.isBefore(localSCheckInData))
        {
            return false;
        }
        //якщо дати співпадають
        if (localSCheckInData.equals(localFCheckInData)
                || localSCheckInData.equals(localFCheckOutData)
                || localSCheckOutData.equals(localFCheckInData)
                || localSCheckOutData.equals(localFCheckOutData))
        {
            return false;
        }
        //якщо дата заїзду в проміжку існуючого бронювання
        if (localSCheckInData.isAfter(localFCheckInData)
                && localSCheckInData.isBefore(localFCheckOutData))
        {
            return false;
        }
        //якщо дата виїзду в проміжку існуючого бронювання
        if (localSCheckOutData.isAfter(localFCheckInData)
                && localSCheckOutData.isBefore(localFCheckOutData))
        {
            return false;
        }
        //якщо існуюче бронювання в проміжку нового
        if (localSCheckInData.isBefore(localFCheckInData)
                && localSCheckOutData.isAfter(localFCheckOutData))
        {
            return false;
        }
        return true;
    }

    /**
     * Check if entered data isn`t less, than current data
     * @param CheckInData date to check
     * @return true, if data is`n belong to pastime
     */
    public boolean isDataInFutureTime(final Date CheckInData){
        Date currentTime = Date.valueOf(LocalDate.now());
        return !CheckInData.before(currentTime);
    }

    /**
     * Check whether string length greater than given length param.
     *
     * @param string
     *            string to check
     * @param length
     *            length
     * @return boolean
     */
    public boolean isValidLength(final String string, final int length) {
        if (string == null || string.isEmpty() || length <= 0) {
            return false;
        }
        return string.length() > length;
    }

    /**
     * Check whether string length greater than given length param.
     *
     * @param string
     *            string to check
     * @param length
     *            length
     * @param maxlength
     *            max length
     * @return boolean
     */
    public boolean isValidLength(final String string, final int length, final int maxlength) {
        if (string == null || string.isEmpty() || length <= 0) {
            return false;
        }
        int size = string.length();
        return (size >= length) && (size <= maxlength);
    }

    /**
     * Check whether inputted string has valid email format
     *
     * @param email
     *            email to check
     * @return boolean
     */
    public boolean isValidEmail(final String email) {
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Check if role entered string has AccountRole format.
     *
     * @param role
     *            role string to check
     * @return true, if role has AccountRole format
     */
    public boolean isValidRole(final String role) {
        if (role == null || AccountRole.get(role) == null) {
            return false;
        }
        return true;
    }
    /**
     * Validates account parameters.
     *
     * @param account
     *            account to validate
     * @throws ValidationException
     *            validation exception
     */
    public void validateAccount(final Account account) {

        LOG.info("Validation account starts");

        String role = account.getRole();
        String firstName = account.getFirstName();
        String lastName = account.getLastName();
        String email = account.getEmail();
        String password = account.getPassword();
        //String repeatPassword = account.getRepeatPassword();
        if (!isValidRole(role)) {
            validationException(INCORRECT_ACCOUNT_ROLE);
        }
        if (!isValidString(firstName) || !isValidLength(firstName, 3)) {
            validationException(INCORRECT_ACCOUNT_FIRST_NAME);
        }
        if (!isValidString(lastName) || !isValidLength(lastName, 3)) {
            validationException(INCORRECT_ACCOUNT_LAST_NAME);
        }
        if (!isValidEmail(email)) {
            validationException(INCORRECT_ACCOUNT_EMAIL);
        }
        if (!isValidLength(password, 6, 20)) {
            validationException(INCORRECT_ACCOUNT_PASSWORD);
        }
        /*  if (!isValidLength(password, 6, 20) || !isValidLength(repeatPassword, 6, 20)
                || !password.equals(repeatPassword)) {
            return false;
        }*/
    }

    /**
     * Validates apartment parameters.
     *
     * @param apartment
     *            apartment to validate
     * @throws ValidationException
     *            validation exception
     */
    public void validateApartment(final Apartment apartment) {

        LOG.info("Validation apartment starts");

        String title = apartment.getTitle();
        String description = apartment.getDescription();
        //String imageURL = apartment.getImageURL();
        String address = apartment.getAddress();
        String maxGuestsNumber = apartment.getMaxGuestsNumber();
        String roomsNumber = apartment.getRoomsNumber();
        String apartmentClass = apartment.getApartmentClass();
        Long price = apartment.getPrice();


        if (!isValidDescription(title)) {
            validationException(INCORRECT_APARTMENT_TITLE);
        }
        if (!isValidDescription(description)) {
            validationException(INCORRECT_DESCRIPTION);
        }
        /*if (!isValidImage(imageURL) ) {
            throw new ValidationException(INCORRECT_APARTMENT_IMAGE);
        }*/
        if (!isValidDescription(address)) {
            validationException(INCORRECT_APARTMENT_ADDRESS);
        }
        if (!isValidNumber(maxGuestsNumber)) {
            validationException(INCORRECT_APARTMENT_MAX_GUESTS_NUMBER);
        }
        if (!isValidNumber(roomsNumber)) {
            validationException(INCORRECT_APARTMENT_ROOMS_NUMBER);
        }
        if (!isValidDescription(apartmentClass)) {
            validationException(INCORRECT_APARTMENT_CLASS);
        }
        if (!isValidNumber(price)) {
            validationException(INCORRECT_APARTMENT_PRICE);
        }

    }

    /**
     * Validates booking parameters.
     *
     * @param booking
     *            booking to validate
     * @param otherBookingDates
     *            HashMap of existing booking dates, to avoid interception with them. May be NULL.
     * @throws ValidationException
     *            validation exception
     */
    public void validateBooking(final Booking booking, final HashMap<Date, Date> otherBookingDates) {

        LOG.info("Validation booking starts");

        String guestsNumber = booking.getGuestsNumber();
        Date checkInDate = booking.getCheckInDate();
        Date checkOutDate = booking.getCheckOutDate();

        if (!isValidNumber(guestsNumber)) {
            validationException(INCORRECT_GUESTS_NUMBER);
        }
        if (!isValidDate(checkInDate.toString())
            ||!isValidDate(checkOutDate.toString())
            ||!isDataInFutureTime(checkInDate)
        ) {
            validationException(INCORRECT_BOOKING_DATA);
        }
        if(otherBookingDates != null){

            otherBookingDates.forEach(
                    (fCheckInDate, fCheckOutDate)-> {
                        if(!comparisonDataValidator(fCheckInDate, fCheckOutDate,
                                                     checkInDate, checkOutDate))
                        { validationException(INCORRECT_BOOKING_DATA); }
                    }
            );
        }

    }

    /**
     * Validates order parameters.
     *
     * @param order
     *            order to validate
     * @throws ValidationException
     *            validation exception
     */
    public void validateOrder(final Order order) {

        LOG.info("Validation order starts");

        Integer guestsNumber = order.getGuestsNumber();
        String roomsNumber = order.getRoomsNumber();
        String apartmentClass = order.getApartmentClass();
        Long price = order.getPrice();
        String description = order.getDescription();

        //Needn't comparing to existing booking dates!
        Date checkInDate = order.getCheckInDate();
        Date checkOutDate = order.getCheckOutDate();

        if (!isValidNumber(guestsNumber)) {
            validationException(INCORRECT_GUESTS_NUMBER);
        }
        if (!isValidString(roomsNumber)) {
            validationException(INCORRECT_APARTMENT_ROOMS_NUMBER);
        }
        if (!isValidString(apartmentClass)) {
            validationException(INCORRECT_APARTMENT_CLASS);
        }
        if (!isValidNumber(price)) {
            validationException(INCORRECT_APARTMENT_PRICE);
        }
        if (!isValidDescription(description)) {
            validationException(INCORRECT_DESCRIPTION);
        }
        if (!isValidDate(checkInDate.toString())
                ||!isValidDate(checkOutDate.toString())
        ) {
            validationException(INCORRECT_BOOKING_DATA);
        }

    }

    /**
     * Validates response-to-order parameters.
     *
     * @param responseToOrder
     *            response-to-order to validate
     * @throws ValidationException
     *            validation exception
     */
    public void validateResponseToOrder(final ResponseToOrder responseToOrder) {

        LOG.info("Validation response-to-order starts");

        String description = responseToOrder.getDescription();

        if (!isValidDescription(description)) {
           validationException(INCORRECT_DESCRIPTION);
        }
    }

    private void validationException(String msg){
        LOG.info("Validation exception -> " + msg);
        throw new ValidationException(msg);
    }
}
