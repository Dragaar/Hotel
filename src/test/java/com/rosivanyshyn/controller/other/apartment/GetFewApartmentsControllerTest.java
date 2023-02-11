package com.rosivanyshyn.controller.other.apartment;

import com.rosivanyshyn.controller.context.AppContext;
import com.rosivanyshyn.controller.dispatcher.viewresolve.ViewResolver;
import com.rosivanyshyn.db.dao.entity.Apartment;
import com.rosivanyshyn.service.ApartmentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.rosivanyshyn.Constant.*;
import static com.rosivanyshyn.Constant.ID_VALUE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetFewApartmentsControllerTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    HttpSession session = mock(HttpSession.class);
    private final AppContext appContext = mock(AppContext.class);
    private final ApartmentService apartmentService = mock(ApartmentService.class);
    private final Apartment apartment = Apartment.builder().id(ID_VALUE_LONG).build();

    @Test
    void testResolve() {

        AppContext.createInstance();

        ArrayList<Apartment> apartments = new ArrayList<>();
        apartments.add(apartment);

        request.getSession(false);
        when(appContext.getApartmentService()).thenReturn(apartmentService);
        when(apartmentService.findFewApartmentsWhichAreBooked(QUERY_BUILDER_VALUE)).thenReturn(apartments);
        when(apartmentService.findFewApartmentsAndSort(QUERY_BUILDER_VALUE)).thenReturn(apartments);
        when(apartmentService.getRowsNumber()).thenReturn(1);

        when(request.getSession(true)).thenReturn(session);
        when(request.getSession(true).getAttribute("existingSortingOrder")).thenReturn(null);

        ViewResolver view = new GetFewApartmentsController(appContext).resolve(request, response);

        assertNotNull(view);


    }
}