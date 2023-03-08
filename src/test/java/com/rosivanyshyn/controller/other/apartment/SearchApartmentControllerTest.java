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

import java.util.ArrayList;

import static com.rosivanyshyn.Constant.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SearchApartmentControllerTest {

    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final ApartmentService apartmentService = mock(ApartmentService.class);

    //-----------------------
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

        //service
        when(appContext.getApartmentService()).thenReturn(apartmentService);
        when(apartmentService.searchApartment(any(String.class), any(int.class), any(int.class))).thenReturn(apartments);
        //apartment id
        when(request.getParameter(APARTMENT_SEARCH_FIELD)).thenReturn(APARTMENT_SEARCH_VALUE);

        ViewResolver view = new SearchApartmentController(appContext).resolve(requestWrapper, response);

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

        //service
        when(appContext.getApartmentService()).thenReturn(apartmentService);
        when(apartmentService.searchApartment(any(String.class), any(int.class), any(int.class))).thenThrow(DAO_EXCEPTION);
        //apartment id
        when(request.getParameter(APARTMENT_SEARCH_FIELD)).thenReturn(APARTMENT_SEARCH_VALUE);

        assertThrows(AppException.class,
                ()-> new SearchApartmentController(appContext).resolve(requestWrapper, response)
        );
    }

}