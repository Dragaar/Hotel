package com.rosivanyshyn.controller.dispatcher;

import java.io.IOException;
import java.io.Serial;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.rosivanyshyn.controller.dispatcher.viewresolve.ViewResolver;


import static com.rosivanyshyn.controller.dispatcher.ControllerConstant.*;

/**
 * Dispatcher Servlet class. Implements Front-controller pattern.
 * <br> Dispatcher operates request and response depends on specific controller {@link ViewResolver} realisation
 *
 * @author Rostyslav Ivanyshyn.
 */
@WebServlet(name = "DispatcherServlet", value = "/front")
public class DispatcherServlet extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 7701796250012254733L;

    /** Default constructor
     */
    public DispatcherServlet() {
    }

    /**
     * Handles request and response in processRequest method
     * @param request user request
     * @param response response to user
     */
    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles request and response in processRequest method
     * @param request user request
     * @param response response to user
     */
    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * Resolve and dispatch the controller selected by the user
     * @param request user request
     * @param response response to user
     */
    private void processRequest(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {

        String controllerName = request.getParameter(CONTROLLER_NAME);

        ControllerFactory factory = new ControllerFactory();
        Controller controller = factory.getController(controllerName);
        ViewResolver resolver = controller.resolve(request, response);
        dispatch(request, response, resolver);
    }

    /**
     * Preform controller view by forward or redirect
     * @param request user request
     * @param response response to user
     * @param resolver controller {@link ViewResolver} interface realisation
     */
    private void dispatch(final HttpServletRequest request, final HttpServletResponse response,
                          final ViewResolver resolver) throws ServletException, IOException {

        String view = resolver.getView();
        switch (resolver.getResolveAction()) {
            case FORWARD:
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(view);
                dispatcher.forward(request, response);
                break;
            case REDIRECT:
                response.sendRedirect(view);
                break;

            default:
                break;
        }

    }

}
