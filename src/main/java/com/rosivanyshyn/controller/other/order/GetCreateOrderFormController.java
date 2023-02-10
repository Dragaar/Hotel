package com.rosivanyshyn.controller.other.order;

import com.rosivanyshyn.controller.dispatcher.Controller;
import com.rosivanyshyn.controller.dispatcher.viewresolve.ViewResolver;
import com.rosivanyshyn.exeption.AppException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.rosivanyshyn.controller.dispatcher.ControllerConstant.NEW_ORDER_JSP;
import static com.rosivanyshyn.exeption.Message.ORDER_GET_CREATE_PAGE_ERROR;

/** Get Create Order Form Controller class.
 * <br> Get JSP form for create new order.
 *
 * @author Rostyslav Ivanyshyn.
 */
public class GetCreateOrderFormController implements Controller {
    @Override
    public ViewResolver resolve(HttpServletRequest request, HttpServletResponse response) {
        ViewResolver resolver = new ViewResolver();

        try {
            resolver.forward(NEW_ORDER_JSP);
        } catch (RuntimeException ex) {
            throw new AppException(ORDER_GET_CREATE_PAGE_ERROR, ex);
        }
        return resolver;
    }
}
