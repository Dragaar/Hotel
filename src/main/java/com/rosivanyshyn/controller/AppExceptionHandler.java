package com.rosivanyshyn.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serial;

import com.rosivanyshyn.exeption.ValidationException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import static com.rosivanyshyn.controller.dispatcher.ControllerConstant.ERROR_JSP;
import static com.rosivanyshyn.controller.dispatcher.ControllerConstant.LOGIN_JSP;

/**
 * Application Exception Handler class. Web Servlet for handling exceptions and forward to error page.
 *
 * @author Rostyslav Ivanyshyn.
 */
@WebServlet("/AppExceptionHandler")
public class AppExceptionHandler extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 4916712330491358458L;

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        processError(request, response);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        processError(request, response);
    }

    private void processError(HttpServletRequest request,
                              HttpServletResponse response) throws ServletException, IOException {
        // Analyze the servlet exception
        Throwable throwable = (Throwable) request
                .getAttribute("jakarta.servlet.error.exception");

        Integer statusCode = (Integer) request
                .getAttribute("jakarta.servlet.error.status_code");
        String servletName = (String) request
                .getAttribute("jakarta.servlet.error.servlet_name");
        if (servletName == null) {
            servletName = "Unknown";
        }
        String requestUri = (String) request
                .getAttribute("jakarta.servlet.error.request_uri");
        if (requestUri == null) {
            requestUri = "Unknown";
        }

        if(statusCode != 500) {
            request.setAttribute("statusCode", statusCode);
            request.setAttribute("requestUri", requestUri);
        }
        else{
            request.setAttribute("statusCode", statusCode);
            request.setAttribute("requestUri", requestUri);
            if(throwable instanceof ValidationException){
                request.setAttribute("translatedMessage", throwable.getMessage());
            } else
                request.setAttribute("message", throwable.getMessage());
        }

        request.getRequestDispatcher(ERROR_JSP).forward(request, response);
    }
}
