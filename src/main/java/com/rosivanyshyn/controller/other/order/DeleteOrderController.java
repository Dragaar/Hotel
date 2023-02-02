package com.rosivanyshyn.controller.other.order;

import com.rosivanyshyn.controller.dispatcher.Controller;
import com.rosivanyshyn.controller.dispatcher.viewresolve.ViewResolver;
import com.rosivanyshyn.db.dao.constant.Field;
import com.rosivanyshyn.db.dao.entity.Order;
import com.rosivanyshyn.exeption.AppException;
import com.rosivanyshyn.service.OrderService;
import com.rosivanyshyn.service.implMySQL.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.NonNull;

import static com.rosivanyshyn.controller.dispatcher.ControllerConstant.*;

public class DeleteOrderController implements Controller {

    OrderService orderService = new OrderServiceImpl();
    @Override
    public ViewResolver resolve(HttpServletRequest request, HttpServletResponse response) {
        ViewResolver resolver = new ViewResolver();
        try {
            HttpSession session = request.getSession(false);
            @NonNull final Long accountId = (Long) session.getAttribute("id");

            @NonNull final Long orderId = Long.valueOf(request.getParameter("orderId"));
            Order order = orderService.findOrderByField(Field.ENTITY_ID, orderId);

            if(order.getAccount().getId().equals(accountId))
            {
                orderService.deleteOrder(order);

                resolver.redirect(request.getContextPath() + "/front?controller="+ GET_APARTMENTS_CONTROLLER +
                        "&message=" + "app.message.order.delete");
            }
            else {
                resolver.redirect(request.getContextPath() + "/front?controller="+ GET_APARTMENTS_CONTROLLER +
                        "&message=" + "app.message.order.dontHaveAccessToDelete");
            }



        } catch (RuntimeException ex){
            throw new AppException("Cannot delete order", ex);
        }
        return resolver;
    }
}
