package com.rosivanyshyn.controller.other.responseToOrder;

import com.rosivanyshyn.controller.context.AppContext;
import com.rosivanyshyn.controller.dispatcher.viewresolve.ViewResolver;
import com.rosivanyshyn.db.dao.entity.Apartment;
import com.rosivanyshyn.db.dao.entity.ResponseToOrder;
import com.rosivanyshyn.exeption.AppException;
import com.rosivanyshyn.service.ApartmentService;
import com.rosivanyshyn.service.ResponseToOrderService;
import com.rosivanyshyn.util.RequestWrapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.rosivanyshyn.Constant.*;
import static com.rosivanyshyn.Constant.ID_VALUE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetResponseToOrderControllerTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final ApartmentService apartmentService = mock(ApartmentService.class);
    private final ResponseToOrderService responseToOrderService = mock(ResponseToOrderService.class);

    //-----------------------
    private final ResponseToOrder responseToOrder = ResponseToOrder.builder()
            .id(ID_VALUE_LONG)
            .description(DESCRIPTION_VALUE)
            .build();
    private final Apartment apartment = Apartment.builder().id(ID_VALUE_LONG).build();
    ArrayList<Apartment> apartments = new ArrayList<>();
    { apartments.add(apartment); }

    /**
     * Test standard behavior
     */
    @Test
    void testResolve() {
        AppContext.createInstance();
        RequestWrapper requestWrapper = new RequestWrapper(request);

        //response-to-order id
        when(request.getParameter(RESPONSE_TO_ORDER_ID_FIELD)).thenReturn(ID_VALUE);
        //service
        when(appContext.getApartmentService()).thenReturn(apartmentService);
        when(appContext.getResponseToOrderService()).thenReturn(responseToOrderService);
        when(responseToOrderService.findResponseToOrderByField(ID_FIELD, ID_VALUE_LONG)).thenReturn(responseToOrder);
        when(responseToOrderService.findAllResponseApartments(responseToOrder)).thenReturn(apartments);

        ViewResolver view = new GetResponseToOrderController(appContext).resolve(requestWrapper, response);

        assertNotNull(view.getView());
        assertEquals(apartments, requestWrapper.getAttribute(APARTMENTS_FIELD));
    }

    /**
     * Test controller to return AppException when something goes wrong in Service
     */
    @Test
    void testResolveError() {
        AppContext.createInstance();
        RequestWrapper requestWrapper = new RequestWrapper(request);

        //response-to-order id
        when(request.getParameter(RESPONSE_TO_ORDER_ID_FIELD)).thenReturn(ID_VALUE);
        //service
        when(appContext.getApartmentService()).thenReturn(apartmentService);
        when(appContext.getResponseToOrderService()).thenReturn(responseToOrderService);
        when(responseToOrderService.findResponseToOrderByField(ID_FIELD, ID_VALUE_LONG)).thenReturn(responseToOrder);
        when(responseToOrderService.findAllResponseApartments(responseToOrder)).thenThrow(DAO_EXCEPTION);

        assertThrows(AppException.class,
                ()-> new GetResponseToOrderController(appContext).resolve(requestWrapper, response)
        );
    }
}