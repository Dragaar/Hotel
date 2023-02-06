package com.rosivanyshyn.controller.dispatcher;

import com.rosivanyshyn.controller.dispatcher.viewresolve.ViewResolver;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Controller interface. To add a new controller in application, it must implement this interface
 *
 * @author Vitalii Kalinchyk
 * @version 1.0
 */
public interface Controller {
    ViewResolver resolve(final HttpServletRequest request, final HttpServletResponse response);
}
