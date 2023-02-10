package com.rosivanyshyn.controller.other.apartment;

import com.rosivanyshyn.controller.context.AppContext;
import com.rosivanyshyn.controller.dispatcher.Controller;
import com.rosivanyshyn.controller.dispatcher.viewresolve.ViewResolver;
import com.rosivanyshyn.db.dao.entity.Apartment;
import com.rosivanyshyn.exeption.AppException;
import com.rosivanyshyn.service.ApartmentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;

import static com.rosivanyshyn.controller.dispatcher.ControllerConstant.*;
import static com.rosivanyshyn.controller.dispatcher.ControllerMessageConstant.APARTMENT_SUCCEED_DELETE;
import static com.rosivanyshyn.exeption.Message.APARTMENT_DELETE_ERROR;

/** Delete Apartment Controller class.
 * <br> Delete apartment from database and redirect to index JSP.
 * @author Rostyslav Ivanyshyn.
 */
public class DeleteApartmentController implements Controller {
    ApartmentService apartmentService;
    public DeleteApartmentController(AppContext appContext){
        apartmentService = appContext.getApartmentService();
    }
    @Override
    public ViewResolver resolve(HttpServletRequest request, HttpServletResponse response) {
        ViewResolver resolver = new ViewResolver();
        try {

        @NonNull final Long id = Long.valueOf(request.getParameter("apartmentId"));
        Apartment apartment = Apartment.builder()
                .id(id)
                .build();
        apartmentService.deleteApartment(apartment);

        resolver.redirect(request.getContextPath()+ INDEX_JSP +
                "?message=" + APARTMENT_SUCCEED_DELETE);

        } catch (RuntimeException ex){
            throw new AppException(APARTMENT_DELETE_ERROR, ex);
        }
        return resolver;
    }
}
