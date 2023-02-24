package com.rosivanyshyn.controller.other.responseToOrder;

import com.rosivanyshyn.controller.context.AppContext;
import com.rosivanyshyn.controller.dispatcher.Controller;
import com.rosivanyshyn.controller.dispatcher.viewresolve.ViewResolver;
import com.rosivanyshyn.db.dao.constant.Field;
import com.rosivanyshyn.db.dao.entity.Apartment;
import com.rosivanyshyn.exeption.AppException;
import com.rosivanyshyn.service.ApartmentService;
import com.rosivanyshyn.service.OrderService;
import com.rosivanyshyn.service.ResponseToOrderService;
import com.rosivanyshyn.utils.MySQLQueryBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;

import java.util.ArrayList;

import static com.rosivanyshyn.controller.dispatcher.ControllerConstant.NEW_RESPONSE_TO_ORDER_JSP;
import static com.rosivanyshyn.exeption.Message.RESPONSE_TO_ORDER_GET_CREATE_PAGE_ERROR;

/** Get Create Response To Order Controller class.
 * <br> Get JSP form (for moderator) for create new response-to-order.
 *
 * @author Rostyslav Ivanyshyn.
 */
public class GetCreateResponseToOrderController implements Controller {
    ApartmentService apartmentService;
    public GetCreateResponseToOrderController(AppContext appContext){
        apartmentService = appContext.getApartmentService();
    }

    @Override
    public ViewResolver resolve(HttpServletRequest request, HttpServletResponse response) {
        ViewResolver resolver = new ViewResolver();

        ArrayList<Apartment> apartments;

        try {
            @NonNull final Long orderId = Long.valueOf(request.getParameter("orderId"));
            request.setAttribute("orderId", orderId);

            MySQLQueryBuilder queryBuilder = new MySQLQueryBuilder();
            queryBuilder.setLabel(Field.APARTMENT);

            apartments = apartmentService.findFewApartmentsAndSort(queryBuilder.getQuery());
            request.setAttribute("apartments", apartments);

            resolver.forward(NEW_RESPONSE_TO_ORDER_JSP);
        } catch (RuntimeException ex) {
            throw new AppException(RESPONSE_TO_ORDER_GET_CREATE_PAGE_ERROR, ex);
        }
        return resolver;
    }

}
