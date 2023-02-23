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


import static com.rosivanyshyn.controller.dispatcher.ControllerMessageConstant.APARTMENT_SUCCEED_DELETE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static com.rosivanyshyn.Constant.*;

public class DeleteApartmentControllerTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final ApartmentService apartmentService = mock(ApartmentService.class);

    //-----------------------
    private final Apartment apartment = Apartment.builder().id(ID_VALUE_LONG).build();

    @Test
    void testResolve() {

    AppContext.createInstance();

        // only non static
        // when(AppContext.getInstance().getApartmentService()).thenReturn(apartmentService);

        //Get static Application context instance
        /*try (MockedStatic<AppContext> mockedStatic = mockStatic(AppContext.class)) {
            mockedStatic.when(AppContext::getInstance).thenReturn(appContext);*/
            //service
            when(appContext.getApartmentService()).thenReturn(apartmentService);
            when(apartmentService.deleteApartment(apartment)).thenReturn(true);
            // only for void return
            // doNothing().when(apartmentService).deleteApartment(apartment);

            //apartment id
            when(request.getParameter(APARTMENT_ID_FIELD)).thenReturn(ID_VALUE);

            ViewResolver view = new DeleteApartmentController(appContext).resolve(request, response);
            //System.out.println("View: " + view.getView());

            assertNotNull(view.getView());
            assertTrue(view.getView().contains(APARTMENT_SUCCEED_DELETE));
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
        when(apartmentService.deleteApartment(apartment)).thenThrow(DAO_EXCEPTION);

        assertThrows(AppException.class,
                ()-> new DeleteApartmentController(appContext).resolve(requestWrapper, response)
        );
    }

}
