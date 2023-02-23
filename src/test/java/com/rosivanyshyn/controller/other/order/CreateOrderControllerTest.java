package com.rosivanyshyn.controller.other.order;

import com.rosivanyshyn.controller.context.AppContext;
import com.rosivanyshyn.controller.dispatcher.viewresolve.ViewResolver;
import com.rosivanyshyn.db.dao.entity.Order;
import com.rosivanyshyn.exeption.AppException;
import com.rosivanyshyn.exeption.ValidationException;
import com.rosivanyshyn.service.OrderService;
import com.rosivanyshyn.util.RequestWrapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import static com.rosivanyshyn.Constant.*;
import static com.rosivanyshyn.controller.dispatcher.ControllerMessageConstant.ORDER_SUCCEED_CREATE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CreateOrderControllerTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final OrderService orderService = mock(OrderService.class);

    /**
     * Test standard behavior
     */
    @Test
    void testResolve() {

        AppContext.createInstance();
        RequestWrapper requestWrapper = new RequestWrapper(request);
        HttpSession session = requestWrapper.getSession();

        //account user id
        session.setAttribute(ID_FIELD, ID_VALUE_LONG);
        //param
        when(request.getParameter(GUESTS_NUMBER_FIELD)).thenReturn(TWO_STRING);
        when(request.getParameter(ROOMS_NUMBER_FIELD)).thenReturn(ROOMS_NUMBER_VALUE);
        when(request.getParameter(APARTMENT_CLASS_FIELD)).thenReturn(APARTMENT_CLASS_VALUE);
        when(request.getParameter(PRICE_FIELD)).thenReturn(PRICE_VALUE);
        when(request.getParameter(DESCRIPTION_FIELD)).thenReturn(DESCRIPTION_VALUE);
        when(request.getParameter(CHECK_IN_DATE_FIELD)).thenReturn(DATE_VALUE.toString());
        when(request.getParameter(CHECK_OUT_DATE_FIELD)).thenReturn(DATE_VALUE2.toString());
        //service
        when(appContext.getOrderService()).thenReturn(orderService);
        when(orderService.createOrder(any(Order.class))).thenReturn(true);

        ViewResolver view = new CreateOrderController(appContext).resolve(requestWrapper, response);

        assertTrue(view.getView().contains(ORDER_SUCCEED_CREATE));
    }

    /**
     * Test controller to return ValidationException when user insert wrong data
     */
    @Test
    void testResolveValidationException() {

        AppContext.createInstance();
        RequestWrapper requestWrapper = new RequestWrapper(request);
        HttpSession session = requestWrapper.getSession();

        //account user id
        session.setAttribute(ID_FIELD, ID_VALUE_LONG);
        //param
        when(request.getParameter(GUESTS_NUMBER_FIELD)).thenReturn(TWO_STRING);
        when(request.getParameter(ROOMS_NUMBER_FIELD)).thenReturn("4.732");
        when(request.getParameter(APARTMENT_CLASS_FIELD)).thenReturn(APARTMENT_CLASS_VALUE);
        when(request.getParameter(PRICE_FIELD)).thenReturn(PRICE_VALUE);
        when(request.getParameter(DESCRIPTION_FIELD)).thenReturn(DESCRIPTION_VALUE);
        when(request.getParameter(CHECK_IN_DATE_FIELD)).thenReturn(DATE_VALUE.toString());
        when(request.getParameter(CHECK_OUT_DATE_FIELD)).thenReturn(DATE_VALUE2.toString());
        //service
        when(appContext.getOrderService()).thenReturn(orderService);
        when(orderService.createOrder(any(Order.class))).thenReturn(true);

        assertThrows(ValidationException.class,
                ()-> new CreateOrderController(appContext).resolve(requestWrapper, response)
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

        //account user id
        session.setAttribute(ID_FIELD, ID_VALUE_LONG);
        //param
        when(request.getParameter(GUESTS_NUMBER_FIELD)).thenReturn(TWO_STRING);
        when(request.getParameter(ROOMS_NUMBER_FIELD)).thenReturn(ROOMS_NUMBER_VALUE);
        when(request.getParameter(APARTMENT_CLASS_FIELD)).thenReturn(APARTMENT_CLASS_VALUE);
        when(request.getParameter(PRICE_FIELD)).thenReturn(PRICE_VALUE);
        when(request.getParameter(DESCRIPTION_FIELD)).thenReturn(DESCRIPTION_VALUE);
        when(request.getParameter(CHECK_IN_DATE_FIELD)).thenReturn(DATE_VALUE.toString());
        when(request.getParameter(CHECK_OUT_DATE_FIELD)).thenReturn(DATE_VALUE2.toString());
        //service
        when(appContext.getOrderService()).thenReturn(orderService);
        when(orderService.createOrder(any(Order.class))).thenThrow(DAO_EXCEPTION);

        assertThrows(AppException.class,
                ()-> new CreateOrderController(appContext).resolve(requestWrapper, response)
        );
    }
}