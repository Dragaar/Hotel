package com.rosivanyshyn.controller.other.booking;

import com.rosivanyshyn.controller.dispatcher.Controller;
import com.rosivanyshyn.controller.dispatcher.viewresolve.ViewResolver;
import com.rosivanyshyn.db.dao.entity.Booking;
import com.rosivanyshyn.exeption.AppException;

import com.rosivanyshyn.service.BookingService;
import com.rosivanyshyn.service.implMySQL.BookingServiceImpl;
import com.rosivanyshyn.utils.MySQLQueryBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.NonNull;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static com.rosivanyshyn.controller.dispatcher.ControllerConstant.*;
import static com.rosivanyshyn.db.dao.constant.Field.BOOKING_ACCOUNT_ID;
import static com.rosivanyshyn.db.dao.constant.Field.BOOKING_APARTMENT_ID;

/** Get Create Booking Form Controller class.
 * <br> Get JSP form for create new booking by apartment id and prepare data to working there.
 * <br>Get from DB bookings dates of related apartment (using requested apartment id)
 * and if this not exist or related to other apartment - set new into the session.
 * This HashMap inform validator about existing bookings
 * and if new booking date intersect existing - prevent booking
 */
public class GetCreateBookingFormController implements Controller {

    @Override
    public ViewResolver resolve(HttpServletRequest request, HttpServletResponse response) {
        ViewResolver resolver = new ViewResolver();
        HttpSession session = request.getSession(false);

        try {
            @NonNull final Long requestedApartmentId = Long.valueOf(request.getParameter("apartmentId"));

            setBookingsDatesOfRelatedApartmentInSession(session, requestedApartmentId);

            resolver.forward(NEW_BOOKING_JSP);
        } catch (RuntimeException ex) {
            throw new AppException("Cannot get create-booking page", ex);
        }
        return resolver;
    }

    private void setBookingsDatesOfRelatedApartmentInSession(HttpSession session, Long requestedApartmentId) {
        final Long apartmentIdOfBookingDates = (Long) session.getAttribute("apartmentIdOfBookingDates");

        @SuppressWarnings("unchecked")
        HashMap<Date, Date> bookingsDates = (HashMap<Date, Date>) session.getAttribute("bookingsDates");

        if(apartmentIdOfBookingDates == null
                || bookingsDates == null
                || !apartmentIdOfBookingDates.equals(requestedApartmentId))
        {
            bookingsDates = getBookingsDatesFromDB(requestedApartmentId);
            session.setAttribute("apartmentIdOfBookingDates", requestedApartmentId);
            session.setAttribute("bookingsDates", bookingsDates);
        }
    }

    private HashMap<Date, Date> getBookingsDatesFromDB(Long apartmentIdOfBookingDatesHashMap) {
        BookingService bookingService = new BookingServiceImpl();

        MySQLQueryBuilder queryBuilder = new MySQLQueryBuilder();
        queryBuilder.setLabel("booking");
        queryBuilder.where(BOOKING_APARTMENT_ID, true);
        ArrayList<Booking> bookings = bookingService.findFewBookingAndSort(queryBuilder.getQuery(), apartmentIdOfBookingDatesHashMap);

        HashMap<Date, Date> bookingsDates = new HashMap<>();
        for(Booking booking : bookings){
            bookingsDates.put(booking.getCheckInDate(), booking.getCheckOutDate());
        }
        return bookingsDates;
    }
}