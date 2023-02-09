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
import static com.rosivanyshyn.db.dao.constant.Field.*;

/** Get Apartment Details Controller class.
 * <br> Get apartment by id and forward to apartment details JSP
 * @author Rostyslav Ivanyshyn.
 */
public class GetApartmentDetailsController implements Controller {
    ApartmentService apartmentService = AppContext.getInstance().getApartmentService();

    @Override
    public ViewResolver resolve(HttpServletRequest request, HttpServletResponse response) {
        ViewResolver resolver = new ViewResolver();

        try {

            @NonNull final Long apartmentId = Long.valueOf(request.getParameter("apartmentId"));
            Apartment apartment = apartmentService.findApartmentByField(ENTITY_ID, apartmentId);

            request.setAttribute("apartment", apartment);

            resolver.forward(APARTMENT_DETAILS_JSP);

        } catch (RuntimeException ex) {
            throw new AppException("Cannot get Apartment Details", ex);
        }
        return resolver;
    }
}
