package com.rosivanyshyn.controller.other.apartment;

import com.rosivanyshyn.controller.context.AppContext;
import com.rosivanyshyn.controller.dispatcher.viewresolve.ViewResolver;
import com.rosivanyshyn.db.dao.entity.Apartment;
import com.rosivanyshyn.exeption.AppException;
import com.rosivanyshyn.service.ApartmentService;
import com.rosivanyshyn.util.RequestWrapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import static com.rosivanyshyn.Constant.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetApartmentDetailsControllerTest {

    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final ApartmentService apartmentService = mock(ApartmentService.class);

    //-----------------------
    private final Apartment apartment = Apartment.builder().id(ID_VALUE_LONG).build();

    /**
     * Test standard behavior
     */
    @Test
    void testResolve() {
        AppContext.createInstance();
        RequestWrapper requestWrapper = new RequestWrapper(request);
        //service
        when(appContext.getApartmentService()).thenReturn(apartmentService);
        when(apartmentService.findApartmentByField(ID_FIELD, ID_VALUE_LONG)).thenReturn(apartment);
        //apartment id
        when(request.getParameter(APARTMENT_ID_FIELD)).thenReturn(ID_VALUE);

        ViewResolver view = new GetApartmentDetailsController(appContext).resolve(requestWrapper, response);

        assertNotNull(view.getView());
        assertEquals(apartment, requestWrapper.getAttribute(APARTMENT_FIELD));
    }

    /**
     * Test controller to return AppException when something goes wrong in Service
     */
    @Test
    void testResolveError() {
        AppContext.createInstance();
        RequestWrapper requestWrapper = new RequestWrapper(request);
        //service
        when(appContext.getApartmentService()).thenReturn(apartmentService);
        when(apartmentService.findApartmentByField(ID_FIELD, ID_VALUE_LONG)).thenThrow(DAO_EXCEPTION);
        //apartment id
        when(request.getParameter(APARTMENT_ID_FIELD)).thenReturn(ID_VALUE);

        assertThrows(AppException.class,
                ()-> new GetApartmentDetailsController(appContext).resolve(requestWrapper, response)
                );
    }
}