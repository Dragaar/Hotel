package com.rosivanyshyn.controller.other.apartment;

import com.rosivanyshyn.controller.context.AppContext;
import com.rosivanyshyn.controller.dispatcher.viewresolve.ViewResolver;
import com.rosivanyshyn.db.dao.entity.Apartment;
import com.rosivanyshyn.exeption.AppException;
import com.rosivanyshyn.service.ApartmentService;
import com.rosivanyshyn.util.RequestWrapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.rosivanyshyn.Constant.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetFewApartmentsControllerTest {
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
        HttpSession session = requestWrapper.getSession();

        when(appContext.getApartmentService()).thenReturn(apartmentService);
        //when(apartmentService.findFewApartmentsWhichAreBooked(any(String.class))).thenReturn(apartments);
        when(apartmentService.findFewApartmentsAndSort(any(String.class))).thenReturn(apartments);
        when(apartmentService.getRowsNumber()).thenReturn(1);

        //session.setAttribute("existingSortingOrder", null);
        //або
        //when(requestWrapper.getSession(true).getAttribute("existingSortingOrder")).thenReturn(null);

        ViewResolver view = new GetFewApartmentsController(appContext).resolve(requestWrapper, response);

        assertNotNull(view.getView());
        assertEquals(apartments, requestWrapper.getAttribute(APARTMENTS_FIELD));


    }

    /**
     * Imitation of user sorting parameters insertion and checking whether the program will behave as expected
     */
    @Test
    void testResolveWithSortingParameters() {

        AppContext.createInstance();
        RequestWrapper requestWrapper = new RequestWrapper(request);
        HttpSession session = requestWrapper.getSession();

        //service
        when(appContext.getApartmentService()).thenReturn(apartmentService);
        when(apartmentService.findFewApartmentsAndSort(any(String.class))).thenReturn(apartments);
        when(apartmentService.getRowsNumber()).thenReturn(1);

        //Sorting order param.
        when(request.getParameter(NEW_SORTING_ORDER_FIELD)).thenReturn(NEW_SORTING_ORDER_VALUE);
            //Asc, Desc
        when(request.getParameter(SORT_BY_PRICE_FIELD)).thenReturn(ASC);
        when(request.getParameter(SORT_BY_MAX_GUESTS_NUMBER_FIELD)).thenReturn(ASC);
        when(request.getParameter(SORT_BY_CLASS_FIELD)).thenReturn(DESC);
            //Free, Booked, Busy, Unavailable
        when(request.getParameter(SORT_BY_STATUS_FIELD)).thenReturn(STATUS_FREE_VALUE);

        ViewResolver view = new GetFewApartmentsController(appContext).resolve(requestWrapper, response);

        assertNotNull(view.getView());
        assertEquals(apartments, requestWrapper.getAttribute(APARTMENTS_FIELD));

        assertEquals(EXISTING_SORTING_ORDER_VALUE, session.getAttribute(EXISTING_SORTING_ORDER_FIELD));
        assertEquals(ASC, session.getAttribute(SORT_BY_PRICE_FIELD));
        assertEquals(ASC, session.getAttribute(SORT_BY_MAX_GUESTS_NUMBER_FIELD));
        assertEquals(DESC, session.getAttribute(SORT_BY_CLASS_FIELD));
        assertEquals(STATUS_FREE_VALUE, session.getAttribute(SORT_BY_STATUS_FIELD));

    }

    /**
     *   RequestWrapper return session instance. Without it wrapper standard request will return null.
     */
    @Test
    void testSessionDoesntExist() {
        AppContext.createInstance();
        //service
        when(appContext.getApartmentService()).thenReturn(apartmentService);
        when(apartmentService.findFewApartmentsWhichAreBooked(any(String.class))).thenReturn(apartments);
        when(apartmentService.findFewApartmentsAndSort(any(String.class))).thenReturn(apartments);
        when(apartmentService.getRowsNumber()).thenReturn(1);

        assertThrows(AppException.class,
                ()-> new GetFewApartmentsController(appContext).resolve(request, response)
        );
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
        when(apartmentService.findFewApartmentsAndSort(any(String.class))).thenThrow(DAO_EXCEPTION);
        when(apartmentService.getRowsNumber()).thenReturn(0);

        assertThrows(AppException.class,
                ()-> new GetFewApartmentsController(appContext).resolve(requestWrapper, response)
        );
    }
}