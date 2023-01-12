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

@WebServlet(name = "DispatcherServlet", value = "/front")
public class DispatcherServlet extends HttpServlet {

    @Serial
    private static final long serialVersionUID = 7701796250012254733L;

    public DispatcherServlet() {
    }

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private void processRequest(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        //TODO Delete this
        String controllerName = request.getParameter(CONTROLLER_NAME);
        System.out.println(" Controller is: " + controllerName);

        ControllerFactory factory = new ControllerFactory();
        Controller controller = factory.getController(controllerName);
        ViewResolver resolver = controller.resolve(request, response);
        dispatch(request, response, resolver);
    }

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
