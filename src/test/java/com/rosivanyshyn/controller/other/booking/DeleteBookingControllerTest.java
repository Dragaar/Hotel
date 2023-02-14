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
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.rosivanyshyn.Constant.*;
import static com.rosivanyshyn.Constant.DAO_EXCEPTION;
import static com.rosivanyshyn.controller.dispatcher.ControllerMessageConstant.APARTMENT_SUCCEED_DELETE;
import static com.rosivanyshyn.controller.dispatcher.ControllerMessageConstant.BOOKING_SUCCEED_DELETE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DeleteBookingControllerTest {

    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final BookingService bookingService = mock(BookingService.class);

    //-----------------------
    private final Account account = Account.builder().id(ID_VALUE_LONG).build();
    private final Booking booking = Booking.builder().id(ID_VALUE_LONG).account(account).build();
    ArrayList<Booking> bookings = new ArrayList<>();
    { bookings.add(booking); }

    @Test
    void testResolve() {

        AppContext.createInstance();
        RequestWrapper requestWrapper = new RequestWrapper(request);
        HttpSession session = requestWrapper.getSession();

        //account id
        session.setAttribute(ID_FIELD, ID_VALUE_LONG);
        //service
        when(appContext.getBookingService()).thenReturn(bookingService);
        when(bookingService.findFewBookingAndSort(any(String.class), any(Long.class))).thenReturn(bookings);
        when(bookingService.deleteBooking(booking)).thenReturn(true);
        //booking id
        when(request.getParameter(BOOKING_ID_FIELD)).thenReturn(ID_VALUE);

        ViewResolver view = new DeleteBookingController(appContext).resolve(requestWrapper, response);

        assertTrue(view.getView().contains(BOOKING_SUCCEED_DELETE));
    }

    /**
     * Test controller to return AppException when something goes wrong in DAO
     */
    @Test
    void testResolveError() {
        AppContext.createInstance();
        RequestWrapper requestWrapper = new RequestWrapper(request);
        HttpSession session = requestWrapper.getSession();

        //account id
        session.setAttribute(ID_FIELD, ID_VALUE);
        //service
        when(appContext.getBookingService()).thenReturn(bookingService);
        when(bookingService.findFewBookingAndSort(any(String.class), any(Long.class))).thenReturn(bookings);
        when(bookingService.deleteBooking(booking)).thenThrow(DAO_EXCEPTION);
        //booking id
        when(request.getParameter(BOOKING_ID_FIELD)).thenReturn(ID_VALUE);

        assertThrows(AppException.class,
                ()-> new DeleteBookingController(appContext).resolve(requestWrapper, response)
        );
    }
}