package com.rosivanyshyn.controller.other.responseToOrder;

import com.rosivanyshyn.controller.dispatcher.Controller;
import com.rosivanyshyn.controller.dispatcher.viewresolve.ViewResolver;
import com.rosivanyshyn.db.dao.constant.AccountRole;
import com.rosivanyshyn.db.dao.constant.Field;
import com.rosivanyshyn.db.dao.entity.Account;
import com.rosivanyshyn.db.dao.entity.Apartment;
import com.rosivanyshyn.db.dao.entity.Order;
import com.rosivanyshyn.db.dao.entity.ResponseToOrder;
import com.rosivanyshyn.exeption.AppException;
import com.rosivanyshyn.service.OrderService;
import com.rosivanyshyn.service.ResponseToOrderService;
import com.rosivanyshyn.service.implMySQL.OrderServiceImpl;
import com.rosivanyshyn.service.implMySQL.ResponseToOrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.NonNull;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import static com.rosivanyshyn.controller.dispatcher.ControllerConstant.*;

public class CreateResponseToOrderController implements Controller {

    ResponseToOrderService responseToOrderService = new ResponseToOrderServiceImpl();
    OrderService orderService = new OrderServiceImpl();

    @Override
    public ViewResolver resolve(HttpServletRequest request, HttpServletResponse response) {
        ViewResolver resolver = new ViewResolver();

        try {
            HttpSession session = request.getSession(false);
            @NonNull final AccountRole role = (AccountRole) session.getAttribute("role");

            if(role.equals(AccountRole.MANAGER)) {

                @NonNull final Long orderId = Long.valueOf(request.getParameter("orderId"));
                Order order = orderService.findOrderByField(Field.ENTITY_ID, orderId);

                @NonNull final String description = request.getParameter("description");

                @NonNull final String[] apartmentsId = request.getParameterValues("apartmentId");

                ArrayList<Apartment> apartments = new ArrayList<>();
                for (String id : apartmentsId) {
                    Apartment apartment = new Apartment();
                    apartment.setId(Long.valueOf(id));
                    apartments.add(apartment);
                }

                ResponseToOrder responseToOrder = ResponseToOrder.builder()
                        .description(description)
                        .build();

                responseToOrderService.createResponseToOrder(order, responseToOrder, apartments);

                resolver.redirect(request.getContextPath() + "/front?controller=" + GET_ALL_ORDERS_CONTROLLER +
                        "&message=" + "Successful create response to order!");
            }
        } catch (RuntimeException ex){
            throw new AppException("Cannot create response to order", ex);
        }
        return resolver;
    }
}
