package com.rosivanyshyn.controller.other.order;

import com.rosivanyshyn.controller.context.AppContext;
import com.rosivanyshyn.controller.dispatcher.viewresolve.ViewResolver;
import com.rosivanyshyn.db.dao.entity.Account;
import com.rosivanyshyn.db.dao.entity.Order;
import com.rosivanyshyn.exeption.AppException;
import com.rosivanyshyn.service.OrderService;
import com.rosivanyshyn.util.RequestWrapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;


import static com.rosivanyshyn.Constant.*;
import static com.rosivanyshyn.controller.dispatcher.ControllerMessageConstant.ORDER_SUCCEED_DELETE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DeleteOrderControllerTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final OrderService orderService = mock(OrderService.class);

    //-----------------------
    private final Account account = Account.builder().id(ID_VALUE_LONG).build();
    private final Order order = Order.builder().id(ID_VALUE_LONG).account(account).build();


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
        when(orderService.findOrderByField(any(String.class), any(Long.class))).thenReturn(order);
        when(orderService.deleteOrder(order)).thenReturn(true);
        //order id
        when(request.getParameter(ORDER_ID_FIELD)).thenReturn(ID_VALUE);

        ViewResolver view = new DeleteOrderController(appContext).resolve(requestWrapper, response);

        assertTrue(view.getView().contains(ORDER_SUCCEED_DELETE));
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
        when(orderService.findOrderByField(any(String.class), any(Long.class))).thenReturn(order);
        when(orderService.deleteOrder(order)).thenThrow(DAO_EXCEPTION);
        //order id
        when(request.getParameter(ORDER_ID_FIELD)).thenReturn(ID_VALUE);

        assertThrows(AppException.class,
                ()-> new DeleteOrderController(appContext).resolve(requestWrapper, response)
        );
    }
}