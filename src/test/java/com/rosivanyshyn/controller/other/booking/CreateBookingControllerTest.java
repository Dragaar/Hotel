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

    /**
     * Test standard behavior
     */
    @Test
    void testResolve() {

        AppContext.createInstance();
        RequestWrapper requestWrapper = new RequestWrapper(request);
        HttpSession session = requestWrapper.getSession();

        when(appContext.getApartmentService()).thenReturn(apartmentService);
        when(appContext.getBookingService()).thenReturn(bookingService);

        //account user id
        session.setAttribute(ID_FIELD, ID_VALUE_LONG);

        when(apartmentService.findApartmentByField(ENTITY_ID, ID_VALUE)).thenReturn(apartment);

        when(request.getParameter(APARTMENT_ID_FIELD)).thenReturn(ID_VALUE);
        when(request.getParameter(GUESTS_NUMBER_FIELD)).thenReturn("2");
        when(request.getParameter(CHECK_IN_DATE_FIELD)).thenReturn(DATE_VALUE.toString());
        when(request.getParameter(CHECK_OUT_DATE_FIELD)).thenReturn(DATE_VALUE2.toString());

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

        when(apartmentService.findApartmentByField(ENTITY_ID, ID_VALUE)).thenReturn(apartment);

        when(request.getParameter(APARTMENT_ID_FIELD)).thenReturn(ID_VALUE);
        when(request.getParameter(GUESTS_NUMBER_FIELD)).thenReturn(TWO_STRING);
        when(request.getParameter(CHECK_IN_DATE_FIELD)).thenReturn(INCORRECT_DATE_VALUE.toString());
        when(request.getParameter(CHECK_OUT_DATE_FIELD)).thenReturn(DATE_VALUE2.toString());

        when(bookingService.createBooking(any(Booking.class))).thenReturn(true);


    }

    /**
     * Test controller to return AppException when something goes wrong in Service
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

        when(apartmentService.findApartmentByField(ENTITY_ID, ID_VALUE)).thenReturn(apartment);

        when(request.getParameter(APARTMENT_ID_FIELD)).thenReturn(ID_VALUE);
        when(request.getParameter(GUESTS_NUMBER_FIELD)).thenReturn(TWO_STRING);
        when(request.getParameter(CHECK_IN_DATE_FIELD)).thenReturn(DATE_VALUE.toString());
        when(request.getParameter(CHECK_OUT_DATE_FIELD)).thenReturn(DATE_VALUE2.toString());

        when(bookingService.createBooking(any(Booking.class))).thenThrow(DAO_EXCEPTION);

        assertThrows(AppException.class,
                ()-> new CreateBookingController(appContext).resolve(requestWrapper, response)
        );
    }
}