package com.rosivanyshyn.controller.other.booking;

import com.rosivanyshyn.controller.context.AppContext;
import com.rosivanyshyn.controller.dispatcher.viewresolve.ViewResolver;
import com.rosivanyshyn.db.dao.entity.Account;
import com.rosivanyshyn.db.dao.entity.Booking;
import com.rosivanyshyn.exeption.AppException;
import com.rosivanyshyn.service.BookingService;
import com.rosivanyshyn.util.RequestWrapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.core.util.Assert;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

import static com.rosivanyshyn.Constant.*;
import static com.rosivanyshyn.Constant.ID_VALUE_LONG;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetCreateBookingFormControllerTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final BookingService bookingService = mock(BookingService.class);

    //-----------------------
    private final Booking booking = Booking.builder()
            .checkInDate(Date.valueOf(DATE_VALUE))
            .checkOutDate(Date.valueOf(DATE_VALUE2))
            .build();
    ArrayList<Booking> bookings = new ArrayList<>();
    { bookings.add(booking); }

    HashMap<java.util.Date, java.util.Date> bookingsDates = new HashMap<>();

    { for (Booking booking : bookings) {
            bookingsDates.put(booking.getCheckInDate(), booking.getCheckOutDate());
        }
    }

    /**
     * Test standard behavior
     */
    @Test
    void testResolve() {
        AppContext.createInstance();
        RequestWrapper requestWrapper = new RequestWrapper(request);
        HttpSession session = requestWrapper.getSession();
        //apartment id
        when(request.getParameter(APARTMENT_ID_FIELD)).thenReturn(ID_VALUE);
        //service
        when(appContext.getBookingService()).thenReturn(bookingService);
        when(bookingService.findFewBookingAndSort(any(String.class), any(Long.class))).thenReturn(bookings);

        ViewResolver view = new GetCreateBookingFormController(appContext).resolve(requestWrapper, response);

        assertNotNull(view.getView());
        //check if dates was correctly inserted in session for apartment which can be booked
        assertEquals(ID_VALUE_LONG, session.getAttribute(APARTMENT_ID_OF_BOOKINGS_DATES_FIELD));
        assertEquals(bookingsDates,   session.getAttribute(BOOKINGS_DATES_FIELD));

    }

    /**
     * Test controller to return AppException when something goes wrong in Service
     */
    @Test
    void resolveError() {
        AppContext.createInstance();
        RequestWrapper requestWrapper = new RequestWrapper(request);
        HttpSession session = requestWrapper.getSession();
        //apartment id
        when(request.getParameter(APARTMENT_ID_FIELD)).thenReturn(ID_VALUE);
        //service
        when(appContext.getBookingService()).thenReturn(bookingService);
        when(bookingService.findFewBookingAndSort(any(String.class), any(Long.class))).thenThrow(DAO_EXCEPTION);

        assertThrows(AppException.class,
                ()-> new GetCreateBookingFormController(appContext).resolve(requestWrapper, response)
        );
    }
}