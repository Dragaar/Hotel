package com.rosivanyshyn.controller.other.booking;

import com.rosivanyshyn.controller.context.AppContext;
import com.rosivanyshyn.controller.dispatcher.viewresolve.ViewResolver;
import com.rosivanyshyn.controller.other.apartment.GetFewApartmentsController;
import com.rosivanyshyn.db.dao.constant.AccountRole;
import com.rosivanyshyn.db.dao.entity.Account;
import com.rosivanyshyn.db.dao.entity.Apartment;
import com.rosivanyshyn.db.dao.entity.Booking;
import com.rosivanyshyn.exeption.AppException;
import com.rosivanyshyn.service.ApartmentService;
import com.rosivanyshyn.service.BookingService;
import com.rosivanyshyn.util.RequestWrapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.rosivanyshyn.Constant.*;
import static com.rosivanyshyn.Constant.DAO_EXCEPTION;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetFewBookingControllerTest {

    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);

    private final BookingService bookingService = mock(BookingService.class);

    //-----------------------
    private final Account account = Account.builder().id(ID_VALUE_LONG).build();
    private final Booking booking = Booking.builder().id(ID_VALUE_LONG).account(account).build();
    ArrayList<Booking> bookings = new ArrayList<>();
    { bookings.add(booking); }


    /**
     * Test standard behavior
     */
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
        when(bookingService.getRowsNumber()).thenReturn(ONE);

        ViewResolver view = new GetFewBookingController(appContext).resolve(requestWrapper, response);

        assertNotNull(view.getView());
        assertEquals(bookings, requestWrapper.getAttribute("bookings"));

    }

    /**
     *   RequestWrapper return session instance. Without it wrapper standard request will return null.
     */
    @Test
    void testSessionDoesntExist() {
        AppContext.createInstance();

        //service
        when(appContext.getBookingService()).thenReturn(bookingService);
        when(bookingService.findFewBookingAndSort(any(String.class), any(Long.class))).thenReturn(bookings);
        when(bookingService.getRowsNumber()).thenReturn(ONE);

        assertThrows(AppException.class,
                ()-> new GetFewBookingController(appContext).resolve(request, response)
        );
    }

    /**
     * Test controller to return AppException when something goes wrong in Service
     */
    @Test
    void testResolveError() {
        AppContext.createInstance();
        RequestWrapper requestWrapper = new RequestWrapper(request);
        HttpSession session = requestWrapper.getSession();

        //account id
        session.setAttribute(ID_FIELD, ID_VALUE_LONG);
        //service
        when(appContext.getBookingService()).thenReturn(bookingService);
        when(bookingService.findFewBookingAndSort(any(String.class), any(Long.class))).thenThrow(DAO_EXCEPTION);
        when(bookingService.getRowsNumber()).thenReturn(ONE);

        assertThrows(AppException.class,
                ()-> new GetFewBookingController(appContext).resolve(requestWrapper, response)
        );
    }
}