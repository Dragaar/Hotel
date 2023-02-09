package com.rosivanyshyn.controller.security;

import com.rosivanyshyn.controller.dispatcher.Controller;
import com.rosivanyshyn.controller.dispatcher.viewresolve.ViewResolver;
import com.rosivanyshyn.exeption.AppException;
import com.rosivanyshyn.exeption.ValidationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;

import static com.rosivanyshyn.controller.dispatcher.ControllerConstant.LOGIN_JSP;

/** Logout Controller class.
 * <br> Destroy current user session and redirect to log in JSP
 *
 * @author Rostyslav Ivanyshyn.
 */
public class LogoutController implements Controller {

    private static final Logger LOG = Logger.getLogger(LogoutController.class);
    @Override
    public ViewResolver resolve(HttpServletRequest request, HttpServletResponse response) {
        ViewResolver resolver = new ViewResolver();
        try{
            destroySession(request, response);

            resolver.redirect(request.getContextPath() + LOGIN_JSP +
                    "?message=" + "app.message.logout");

        } catch (RuntimeException ex){
            throw new AppException("Cannot log out", ex);
        }
        return resolver;
    }

    private void destroySession(HttpServletRequest request, HttpServletResponse response) {
        LOG.info("Destroy session");
        HttpSession session = request.getSession();
        session.invalidate();
    }
}
