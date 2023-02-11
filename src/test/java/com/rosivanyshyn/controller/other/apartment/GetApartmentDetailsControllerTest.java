package com.rosivanyshyn.controller.other.apartment;

import com.rosivanyshyn.controller.context.AppContext;
import com.rosivanyshyn.controller.dispatcher.viewresolve.ViewResolver;
import com.rosivanyshyn.db.dao.entity.Apartment;
import com.rosivanyshyn.service.ApartmentService;
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
    private final Apartment apartment = Apartment.builder().id(ID_VALUE_LONG).build();

    @Test
    void testResolve() {

        AppContext.createInstance();

        when(appContext.getApartmentService()).thenReturn(apartmentService);
        when(apartmentService.findApartmentByField(ID_FIELD_NAME, ID_VALUE_LONG)).thenReturn(apartment);

        when(request.getParameter(APARTMENT_ID)).thenReturn(ID_VALUE);

        ViewResolver view = new GetApartmentDetailsController(appContext).resolve(request, response);

        assertNotNull(view);


    }
}