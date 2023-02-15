package com.rosivanyshyn.controller.other.booking;

import com.rosivanyshyn.controller.context.AppContext;
import com.rosivanyshyn.controller.dispatcher.viewresolve.ViewResolver;
import com.rosivanyshyn.db.dao.entity.Apartment;
import com.rosivanyshyn.db.dao.entity.Booking;
import com.rosivanyshyn.exeption.AppException;
import com.rosivanyshyn.service.ApartmentService;
import com.rosivanyshyn.service.BookingService;
import com.rosivanyshyn.util.RequestWrapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.NonNull;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.rosivanyshyn.Constant.*;
import static com.rosivanyshyn.Constant.DATE_VALUE2;
import static com.rosivanyshyn.controller.dispatcher.ControllerMessageConstant.BOOKING_SUCCEED_CREATE;
import static com.rosivanyshyn.controller.dispatcher.ControllerMessageConstant.BOOKING_SUCCEED_PAYMENT;
import static com.rosivanyshyn.db.dao.constant.Field.ENTITY_ID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PaymentForBookingControllerTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final BookingService bookingService = mock(BookingService.class);

    //-----------------------
    private final Booking booking = Booking.builder().id(ID_VALUE_LONG).build();
    ArrayList<Booking> bookings = new ArrayList<>();
    { bookings.add(booking); }

    /**
     * Test standard behavior
     */
    @Test
    void testResolve() {
        AppContext.createInstance();

        //booking id
        when(request.getParameter(BOOKING_ID_FIELD)).thenReturn(ID_VALUE);
        //service
        when(appContext.getBookingService()).thenReturn(bookingService);
        when(bookingService.findFewBookingAndSort(any(String.class), any(Long.class))).thenReturn(bookings);
        when(bookingService.updateBooking(any(Booking.class))).thenReturn(true);

        ViewResolver view = new PaymentForBookingController(appContext).resolve(request, response);

        assertTrue(view.getView().contains(BOOKING_SUCCEED_PAYMENT));
    }
    /**
     * Test controller to return AppException when something goes wrong in Service
     */
    @Test
    void testResolveError() {
        AppContext.createInstance();

        //booking id
        when(request.getParameter(BOOKING_ID_FIELD)).thenReturn(ID_VALUE);
        //service
        when(appContext.getBookingService()).thenReturn(bookingService);
        when(bookingService.findFewBookingAndSort(any(String.class), any(Long.class))).thenThrow(DAO_EXCEPTION);
        when(bookingService.updateBooking(any(Booking.class))).thenReturn(true);

        assertThrows(AppException.class,
                ()-> new PaymentForBookingController(appContext).resolve(request, response)
        );
    }
}