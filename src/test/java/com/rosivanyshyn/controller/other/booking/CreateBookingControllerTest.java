package com.rosivanyshyn.controller.other.booking;

import com.rosivanyshyn.controller.context.AppContext;
import com.rosivanyshyn.controller.dispatcher.viewresolve.ViewResolver;
import com.rosivanyshyn.db.dao.entity.Apartment;
import com.rosivanyshyn.db.dao.entity.Booking;
import com.rosivanyshyn.exeption.AppException;
import com.rosivanyshyn.exeption.ValidationException;
import com.rosivanyshyn.service.ApartmentService;
import com.rosivanyshyn.service.BookingService;
import com.rosivanyshyn.util.RequestWrapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.HashMap;

import static com.rosivanyshyn.Constant.*;
import static com.rosivanyshyn.Constant.DAO_EXCEPTION;
import static com.rosivanyshyn.controller.dispatcher.ControllerMessageConstant.BOOKING_SUCCEED_CREATE;
import static com.rosivanyshyn.db.dao.constant.Field.ENTITY_ID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateBookingControllerTest {

    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final ApartmentService apartmentService = mock(ApartmentService.class);
    private final BookingService bookingService = mock(BookingService.class);

    //-----------------------
    private final Apartment apartment = Apartment.builder().id(ID_VALUE_LONG).build();
    private final HashMap<Date, Date> bookingsDates = new HashMap<>();

    @Test
    void testResolve() {

        AppContext.createInstance();
        RequestWrapper requestWrapper = new RequestWrapper(request);
        HttpSession session = requestWrapper.getSession();

        when(appContext.getApartmentService()).thenReturn(apartmentService);
        when(appContext.getBookingService()).thenReturn(bookingService);

        //account user id
        session.setAttribute(ID_FIELD, ID_VALUE_LONG);
        //if for current apartment not exist other bookings
        session.setAttribute(BOOKINGS_DATES_FIELD, bookingsDates);

        when(apartmentService.findApartmentByField(ENTITY_ID, ID_VALUE)).thenReturn(apartment);

        when(request.getParameter(APARTMENT_ID_FIELD)).thenReturn(ID_VALUE);
        when(request.getParameter("guestsNumber")).thenReturn("2");
        when(request.getParameter("checkInDate")).thenReturn(DATE_VALUE.toString());
        when(request.getParameter("checkOutDate")).thenReturn(DATE_VALUE2.toString());

        when(bookingService.createBooking(any(Booking.class))).thenReturn(true);

        ViewResolver view = new CreateBookingController(appContext).resolve(requestWrapper, response);

        assertTrue(view.getView().contains(BOOKING_SUCCEED_CREATE));
    }

    /**
     * Test controller to return ValidationException when user insert wrong Data
     */
    @Test
    void testResolveValidationException() {

        AppContext.createInstance();
        RequestWrapper requestWrapper = new RequestWrapper(request);
        HttpSession session = requestWrapper.getSession();

        when(appContext.getApartmentService()).thenReturn(apartmentService);
        when(appContext.getBookingService()).thenReturn(bookingService);

        //account user id
        session.setAttribute(ID_FIELD, ID_VALUE_LONG);
        //if for current apartment not exist other bookings
        session.setAttribute(BOOKINGS_DATES_FIELD, bookingsDates);

        when(apartmentService.findApartmentByField(ENTITY_ID, ID_VALUE)).thenReturn(apartment);

        when(request.getParameter(APARTMENT_ID_FIELD)).thenReturn(ID_VALUE);
        when(request.getParameter("guestsNumber")).thenReturn("2");
        when(request.getParameter("checkInDate")).thenReturn(INCORRECT_DATE_VALUE.toString());
        when(request.getParameter("checkOutDate")).thenReturn(DATE_VALUE2.toString());

        when(bookingService.createBooking(any(Booking.class))).thenReturn(true);

        assertThrows(ValidationException.class,
                ()-> new CreateBookingController(appContext).resolve(requestWrapper, response)
        );
    }

    /**
     * Test controller to return AppException when something goes wrong in DAO
     */
    @Test
    void testResolveError() {
        AppContext.createInstance();
        RequestWrapper requestWrapper = new RequestWrapper(request);
        HttpSession session = requestWrapper.getSession();

        when(appContext.getApartmentService()).thenReturn(apartmentService);
        when(appContext.getBookingService()).thenReturn(bookingService);

        //account user id
        session.setAttribute(ID_FIELD, ID_VALUE_LONG);
        //if for current apartment not exist other bookings
        session.setAttribute(BOOKINGS_DATES_FIELD, bookingsDates);

        when(apartmentService.findApartmentByField(ENTITY_ID, ID_VALUE)).thenReturn(apartment);

        when(request.getParameter(APARTMENT_ID_FIELD)).thenReturn(ID_VALUE);
        when(request.getParameter("guestsNumber")).thenReturn("2");
        when(request.getParameter("checkInDate")).thenReturn(DATE_VALUE.toString());
        when(request.getParameter("checkOutDate")).thenReturn(DATE_VALUE2.toString());

        when(bookingService.createBooking(any(Booking.class))).thenThrow(DAO_EXCEPTION);

        assertThrows(AppException.class,
                ()-> new CreateBookingController(appContext).resolve(requestWrapper, response)
        );
    }
}