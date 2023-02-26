package com.rosivanyshyn.controller.other.booking;

import com.rosivanyshyn.controller.context.AppContext;
import com.rosivanyshyn.controller.dispatcher.Controller;
import com.rosivanyshyn.controller.dispatcher.viewresolve.ViewResolver;
import com.rosivanyshyn.db.dao.entity.Booking;
import com.rosivanyshyn.exeption.AppException;

import com.rosivanyshyn.service.BookingService;
import com.rosivanyshyn.utils.MySQLQueryBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.NonNull;


import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.rosivanyshyn.db.dao.constant.Field.*;
import static com.rosivanyshyn.utils.MySQLQueryBuilder.LogicalOperation.*;
import static com.rosivanyshyn.controller.dispatcher.ControllerConstant.*;
import static com.rosivanyshyn.exeption.Message.BOOKING_GET_CREATE_PAGE_ERROR;

/** Get Create Booking Form Controller class.
 * <br> Get JSP form for create new booking by apartment id and prepare data to working there.
 * <br>Get from DB bookings dates of related apartment (using requested apartment id)
 * and if this not exist or related to other apartment - set new into the session.
 * This HashMap inform validator about existing bookings
 * and if new booking date intersect existing - prevent booking
 */
public class GetCreateBookingFormController implements Controller {
    BookingService bookingService;
    public GetCreateBookingFormController(AppContext appContext){
        bookingService = appContext.getBookingService();
    }
    @Override
    public ViewResolver resolve(HttpServletRequest request, HttpServletResponse response) {
        ViewResolver resolver = new ViewResolver();
        HttpSession session = request.getSession(false);

        try {
            @NonNull final Long requestedApartmentId = Long.valueOf(request.getParameter("apartmentId"));

            request.setAttribute("currentDate", LocalDate.now());

            HashMap<Date, Date> bookingsDates = setBookingsDatesOfRelatedApartmentInSession(session, requestedApartmentId);
            ArrayList<LocalDate> bookingsDatesAsList = convertDatesRangeToList(bookingsDates);

            request.setAttribute("datesDisabled",  bookingsDatesAsList);

            resolver.forward(NEW_BOOKING_JSP);
        } catch (RuntimeException ex) {
            throw new AppException(BOOKING_GET_CREATE_PAGE_ERROR, ex);
        }
        return resolver;
    }

    private HashMap<Date, Date> setBookingsDatesOfRelatedApartmentInSession(HttpSession session, Long requestedApartmentId) {
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
        return bookingsDates;
    }

    private HashMap<Date, Date> getBookingsDatesFromDB(Long apartmentIdOfBookingDatesHashMap) {
        Date currentDate = Date.valueOf(LocalDate.now());
        MySQLQueryBuilder queryBuilder = new MySQLQueryBuilder();
        queryBuilder.setLabel("booking");
        queryBuilder.where(BOOKING_APARTMENT_ID, EQUAL, true);

        //get only actual dates
        queryBuilder.subcondition(true);
        queryBuilder.where(BOOKING_CHECK_IN_DATE, GREATER_OR_EQUAL, true);
        queryBuilder.where(BOOKING_CHECK_OUT_DATE, GREATER_OR_EQUAL, false);
        queryBuilder.subcondition(false);

        ArrayList<Booking> bookings = bookingService.findFewBookingAndSort(
                queryBuilder.getQuery(),
                apartmentIdOfBookingDatesHashMap,
                currentDate,
                currentDate
        );

        HashMap<Date, Date> bookingsDates = new HashMap<>();
        for(Booking booking : bookings){
            bookingsDates.put(booking.getCheckInDate(), booking.getCheckOutDate());
        }
        return bookingsDates;
    }

    private ArrayList<LocalDate> convertDatesRangeToList(HashMap<Date, Date> bookingsDates) {
        ArrayList<LocalDate> bookingsDatesAsList = new ArrayList<>();
        bookingsDates.forEach(
                (checkInDate, checkOutDate)-> {
                    bookingsDatesAsList.addAll(
                            getDatesFromRange(convertToLocalDate(checkInDate),
                                    convertToLocalDate(checkOutDate)) );
                }
        );
        return bookingsDatesAsList;
    }

    public List<LocalDate> getDatesFromRange(LocalDate dateFrom, LocalDate dateTill) {
        long numOfDaysBetween = ChronoUnit.DAYS.between(dateFrom, dateTill) + 1;
        return IntStream.iterate(0, i -> i + 1)
                .limit(numOfDaysBetween)
                .mapToObj(dateFrom::plusDays)
                .collect(Collectors.toList());
    }

    public LocalDate convertToLocalDate(Date dateToConvert) {
        return Optional.ofNullable(dateToConvert)
                .map(Date::toLocalDate)
                .orElse(null);
    }

}