package com.rosivanyshyn.controller.other.apartment;

import com.rosivanyshyn.controller.dispatcher.Controller;
import com.rosivanyshyn.controller.dispatcher.viewresolve.ViewResolver;
import com.rosivanyshyn.db.dao.entity.Apartment;
import com.rosivanyshyn.exeption.AppException;
import com.rosivanyshyn.exeption.ValidationException;
import com.rosivanyshyn.service.ApartmentService;
import com.rosivanyshyn.service.implMySQL.ApartmentServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;

import static com.rosivanyshyn.controller.dispatcher.ControllerConstant.*;

public class DeleteApartmentController implements Controller {
    ApartmentService apartmentService = new ApartmentServiceImpl();
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
                "?message=" + "Successful apartment delete!");

        } catch (RuntimeException ex){
            throw new AppException("Cannot delete Apartment", ex);
        }
        return resolver;
    }
}
