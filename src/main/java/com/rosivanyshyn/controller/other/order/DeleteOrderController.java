package com.rosivanyshyn.controller.other.order;

import com.rosivanyshyn.controller.context.AppContext;
import com.rosivanyshyn.controller.dispatcher.Controller;
import com.rosivanyshyn.controller.dispatcher.viewresolve.ViewResolver;
import com.rosivanyshyn.db.dao.constant.Field;
import com.rosivanyshyn.db.dao.entity.Order;
import com.rosivanyshyn.exeption.AppException;
import com.rosivanyshyn.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.NonNull;

import static com.rosivanyshyn.controller.dispatcher.ControllerConstant.*;
import static com.rosivanyshyn.controller.dispatcher.ControllerMessageConstant.ORDER_DONT_HAVE_ACCESS_TO_DELETE;
import static com.rosivanyshyn.controller.dispatcher.ControllerMessageConstant.ORDER_SUCCEED_DELETE;
import static com.rosivanyshyn.exeption.Message.ORDER_DELETE_ERROR;

/** Delete Order Controller class.
 * <br> Delete order from database (if order created by current user) and redirect to apartments JSP.
 *
 * @author Rostyslav Ivanyshyn.
 */
public class DeleteOrderController implements Controller {

    OrderService orderService;
    public DeleteOrderController(AppContext appContext){
        orderService = appContext.getOrderService();
    }
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

                resolver.redirect(request.getContextPath() + INITIALIZE_CONTROLLER + GET_APARTMENTS_CONTROLLER +
                        "&message=" + ORDER_SUCCEED_DELETE);
            }
            else {
                resolver.redirect(request.getContextPath() + INITIALIZE_CONTROLLER + GET_APARTMENTS_CONTROLLER +
                        "&message=" + ORDER_DONT_HAVE_ACCESS_TO_DELETE);
            }

        } catch (RuntimeException ex){
            throw new AppException(ORDER_DELETE_ERROR, ex);
        }
        return resolver;
    }
}
