package com.rosivanyshyn.controller.other.responseToOrder;

import com.rosivanyshyn.controller.context.AppContext;
import com.rosivanyshyn.controller.dispatcher.viewresolve.ViewResolver;
import com.rosivanyshyn.db.dao.constant.AccountRole;
import com.rosivanyshyn.db.dao.entity.Order;
import com.rosivanyshyn.db.dao.entity.ResponseToOrder;
import com.rosivanyshyn.exeption.AppException;
import com.rosivanyshyn.service.OrderService;
import com.rosivanyshyn.service.ResponseToOrderService;
import com.rosivanyshyn.util.RequestWrapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.rosivanyshyn.Constant.*;
import static com.rosivanyshyn.controller.dispatcher.ControllerMessageConstant.RESPONSE_TO_ORDER_SUCCEED_CREATE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CreateResponseToOrderControllerTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final OrderService orderService = mock(OrderService.class);
    private final ResponseToOrderService responseToOrderService = mock(ResponseToOrderService.class);

    //-----------------------
    private final Order order = Order.builder().id(ID_VALUE_LONG).build();

    AccountRole accountManagerRole = AccountRole.MANAGER;
    //AccountRole accountUserRole = AccountRole.USER;
    /**
     * Test standard behavior
     */
    @Test
    void testResolve() {

        AppContext.createInstance();
        RequestWrapper requestWrapper = new RequestWrapper(request);
        HttpSession session = requestWrapper.getSession();

        //access role
        session.setAttribute(ROLE_FIELD, accountManagerRole);
        //param
        when(request.getParameter("orderId")).thenReturn("1");
        when(request.getParameter("description")).thenReturn("answer to user application");
        when(request.getParameterValues("apartmentId")).thenReturn(new String[]{"1", "3","6"});

        //service
        when(appContext.getOrderService()).thenReturn(orderService);
        when(appContext.getResponseToOrderService()).thenReturn(responseToOrderService);
        when(orderService.findOrderByField(any(String.class), any(Long.class))).thenReturn(order);
        when(responseToOrderService.createResponseToOrder(any(Order.class), any(ResponseToOrder.class), any(ArrayList.class)))
                .thenReturn(true);

        ViewResolver view = new CreateResponseToOrderController(appContext).resolve(requestWrapper, response);

        assertTrue(view.getView().contains(RESPONSE_TO_ORDER_SUCCEED_CREATE));
    }

    /**
     * Test controller to return AppException when something goes wrong in Service
     */
    @Test
    void testResolveError() {
        AppContext.createInstance();
        RequestWrapper requestWrapper = new RequestWrapper(request);
        HttpSession session = requestWrapper.getSession();

        //access role
        session.setAttribute(ROLE_FIELD, accountManagerRole);
        //param
        when(request.getParameter("orderId")).thenReturn("1");
        when(request.getParameter("description")).thenReturn("answer to user application");
        when(request.getParameter("orderId")).thenReturn("1");
        when(request.getParameter("description")).thenReturn("answer to user application");
        when(request.getParameterValues("apartmentId")).thenReturn(new String[]{"1", "3","6"});

        //service
        when(appContext.getOrderService()).thenReturn(orderService);
        when(appContext.getResponseToOrderService()).thenReturn(responseToOrderService);
        when(orderService.findOrderByField(any(String.class), any(Long.class))).thenReturn(order);
        when(responseToOrderService.createResponseToOrder(any(Order.class), any(ResponseToOrder.class), any(ArrayList.class)))
                .thenThrow(DAO_EXCEPTION);

        assertThrows(AppException.class,
                ()-> new CreateResponseToOrderController(appContext).resolve(requestWrapper, response)
        );
    }
}