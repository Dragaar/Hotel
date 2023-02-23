package com.rosivanyshyn.controller.other.order;

import com.rosivanyshyn.controller.context.AppContext;
import com.rosivanyshyn.controller.dispatcher.viewresolve.ViewResolver;
import com.rosivanyshyn.db.dao.constant.AccountRole;
import com.rosivanyshyn.db.dao.entity.Account;
import com.rosivanyshyn.db.dao.entity.Order;
import com.rosivanyshyn.exeption.AppException;
import com.rosivanyshyn.service.OrderService;
import com.rosivanyshyn.util.RequestWrapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.rosivanyshyn.Constant.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetAllOrdersControllerTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final OrderService orderService = mock(OrderService.class);

    //-----------------------
    private final Account account = Account.builder().id(ID_VALUE_LONG).build();
    private final Order order = Order.builder().id(ID_VALUE_LONG).account(account).build();
    ArrayList<Order> orders = new ArrayList<>();
    { orders.add(order); }
    AccountRole accountManagerRole = AccountRole.MANAGER;
    AccountRole accountUserRole = AccountRole.USER;

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
        //service
        when(appContext.getOrderService()).thenReturn(orderService);
        when(orderService.findFewOrdersAndSort(any(String.class))).thenReturn(orders);
        when(orderService.getRowsNumber()).thenReturn(ONE);

        ViewResolver view = new GetAllOrdersController(appContext).resolve(requestWrapper, response);

        assertNotNull(view.getView());
        assertEquals(orders, requestWrapper.getAttribute(ORDERS_FIELD));
    }

    /**
     * Test controller to return void when user don`t have access. Exception should throw other application layer
     */
    @Test
    void testResolveAccessError() {
        AppContext.createInstance();
        RequestWrapper requestWrapper = new RequestWrapper(request);
        HttpSession session = requestWrapper.getSession();

        //access role
        session.setAttribute(ROLE_FIELD, accountUserRole);
        //service
        when(appContext.getOrderService()).thenReturn(orderService);
        when(orderService.findFewOrdersAndSort(any(String.class))).thenReturn(orders);
        when(orderService.getRowsNumber()).thenReturn(ONE);

        ViewResolver view = new GetAllOrdersController(appContext).resolve(requestWrapper, response);

        assertNull(view.getView());
        assertNull(requestWrapper.getAttribute(ORDERS_FIELD));
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
        //service
        when(appContext.getOrderService()).thenReturn(orderService);
        when(orderService.findFewOrdersAndSort(any(String.class))).thenThrow(DAO_EXCEPTION);
        when(orderService.getRowsNumber()).thenReturn(ONE);

        assertThrows(AppException.class,
                ()-> new GetAllOrdersController(appContext).resolve(requestWrapper, response)
        );
    }
}