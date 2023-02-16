package com.rosivanyshyn.controller.other.language;

import com.rosivanyshyn.controller.dispatcher.Controller;
import com.rosivanyshyn.controller.dispatcher.viewresolve.ViewResolver;
import com.rosivanyshyn.exeption.AppException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.NonNull;
import org.apache.log4j.Logger;

import static com.rosivanyshyn.controller.dispatcher.ControllerConstant.GET_APARTMENTS_CONTROLLER;
import static com.rosivanyshyn.exeption.Message.LANGUAGE_CHANGE_ERROR;

/** Change Language Controller class.
 * <br> Change and save localization in session.
 *
 * @author Rostyslav Ivanyshyn.
 */
public class ChangeLanguageController implements Controller {

    protected static final Logger LOG = Logger.getLogger(ChangeLanguageController.class);

    @Override
    public ViewResolver resolve(HttpServletRequest request, HttpServletResponse response) {
        LOG.info("Language changing");

        ViewResolver resolver = new ViewResolver();
        try {
        HttpSession session = request.getSession(false);
        @NonNull final String language = request.getParameter("language");
        session.setAttribute("lang", language);

        //TODO add forward support
        String lastPage = request.getHeader("referer");

        if(lastPage != null){
            resolver.redirect(lastPage);
        } else {
            resolver.redirect(request.getContextPath()+"/front?controller="+ GET_APARTMENTS_CONTROLLER);
        }
        } catch (RuntimeException ex) {
            LOG.error(this.getClass() + " " + LANGUAGE_CHANGE_ERROR, ex);
            throw new AppException(LANGUAGE_CHANGE_ERROR, ex);
        }
        return resolver;
    }

}
