package com.rosivanyshyn.controller.other.responseToOrder;

import com.rosivanyshyn.controller.dispatcher.Controller;
import com.rosivanyshyn.controller.dispatcher.viewresolve.ViewResolver;
import com.rosivanyshyn.db.dao.entity.Apartment;
import com.rosivanyshyn.db.dao.entity.ResponseToOrder;
import com.rosivanyshyn.exeption.AppException;
import com.rosivanyshyn.service.ResponseToOrderService;
import com.rosivanyshyn.service.implMySQL.ResponseToOrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;

import java.util.ArrayList;

import static com.rosivanyshyn.controller.dispatcher.ControllerConstant.RESPONSE_TO_ORDER_JSP;
import static com.rosivanyshyn.db.dao.constant.Field.ENTITY_ID;

public class GetResponseToOrderController implements Controller {
    ResponseToOrderService responseToOrderService = new ResponseToOrderServiceImpl();

    @Override
    public ViewResolver resolve(HttpServletRequest request, HttpServletResponse response) {
        ViewResolver resolver = new ViewResolver();

        try {

            @NonNull final Long responseToOrderId = Long.valueOf(request.getParameter("responseToOrderId"));
            ResponseToOrder responseToOrder = responseToOrderService.findResponseToOrderByField(ENTITY_ID, responseToOrderId);
            request.setAttribute("description", responseToOrder.getDescription());

            ArrayList<Apartment> apartments = responseToOrderService.findAllResponseApartments(responseToOrder);


            request.setAttribute("apartments", apartments);

            resolver.forward(RESPONSE_TO_ORDER_JSP);

        } catch (RuntimeException ex) {
            throw new AppException("Cannot get Response to order", ex);
        }
        return resolver;
    }

}
