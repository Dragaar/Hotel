package com.rosivanyshyn.controller.other.order;

import com.rosivanyshyn.controller.dispatcher.Controller;
import com.rosivanyshyn.controller.dispatcher.viewresolve.ViewResolver;
import com.rosivanyshyn.exeption.AppException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.rosivanyshyn.controller.dispatcher.ControllerConstant.NEW_ORDER_JSP;

public class GetCreateOrderFormController implements Controller {
    @Override
    public ViewResolver resolve(HttpServletRequest request, HttpServletResponse response) {
        ViewResolver resolver = new ViewResolver();

        try {
            resolver.forward(NEW_ORDER_JSP);
        } catch (RuntimeException ex) {
            throw new AppException("Cannot get create-order page", ex);
        }
        return resolver;
    }
}
