package com.rosivanyshyn.controller.other.order;

import com.rosivanyshyn.controller.context.AppContext;
import com.rosivanyshyn.controller.dispatcher.viewresolve.ViewResolver;
import com.rosivanyshyn.controller.other.booking.GetFewBookingController;
import com.rosivanyshyn.db.dao.entity.Account;
import com.rosivanyshyn.db.dao.entity.Booking;
import com.rosivanyshyn.db.dao.entity.Order;
import com.rosivanyshyn.exeption.AppException;
import com.rosivanyshyn.service.BookingService;
import com.rosivanyshyn.service.OrderService;
import com.rosivanyshyn.util.RequestWrapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.rosivanyshyn.Constant.*;
import static com.rosivanyshyn.Constant.ONE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetFewOrdersControllerTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final OrderService orderService = mock(OrderService.class);

    //-----------------------
    private final Order order = Order.builder().id(ID_VALUE_LONG).build();
    ArrayList<Order> orders = new ArrayList<>();
    { orders.add(order); }
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
        when(appContext.getOrderService()).thenReturn(orderService);
        when(orderService.findFewOrdersAndSort(any(String.class), any(Long.class))).thenReturn(orders);
        when(orderService.getRowsNumber()).thenReturn(ONE);

        ViewResolver view = new GetFewOrdersController(appContext).resolve(requestWrapper, response);

        assertNotNull(view.getView());
        assertEquals(orders, requestWrapper.getAttribute("orders"));

    }

    /**
     *   RequestWrapper return session instance. Without it wrapper standard request will return null.
     */
    @Test
    void testSessionDoesntExist() {
        AppContext.createInstance();

        //service
        when(appContext.getOrderService()).thenReturn(orderService);
        when(orderService.findFewOrdersAndSort(any(String.class), any(Long.class))).thenReturn(orders);
        when(orderService.getRowsNumber()).thenReturn(ONE);

        assertThrows(AppException.class,
                ()-> new GetFewOrdersController(appContext).resolve(request, response)
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
        when(appContext.getOrderService()).thenReturn(orderService);
        when(orderService.findFewOrdersAndSort(any(String.class), any(Long.class))).thenThrow(DAO_EXCEPTION);
        when(orderService.getRowsNumber()).thenReturn(ONE);

        assertThrows(AppException.class,
                ()-> new GetFewOrdersController(appContext).resolve(requestWrapper, response)
        );
    }
}