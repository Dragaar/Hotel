package com.rosivanyshyn.controller.authorization.filter;

import com.rosivanyshyn.db.dao.constant.AccountRole;
import com.rosivanyshyn.db.dao.entity.Account;
import com.rosivanyshyn.exeption.SecurityException;
import jakarta.servlet.annotation.WebFilter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.lf5.DefaultLF5Configurator;

import java.io.IOException;

import static com.rosivanyshyn.controller.dispatcher.ControllerConstant.*;
import static java.util.Objects.nonNull;

@WebFilter("/front")
public class AuthFilter implements Filter {



    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try { DefaultLF5Configurator.configure();
        } catch (IOException e) {throw new RuntimeException(e);}
    }
    @Override
    public void doFilter(final ServletRequest request,
                         final ServletResponse response,
                         final FilterChain filterChain)

            throws IOException, ServletException {

        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        /*if(session == null
                && ( req.getParameter(CONTROLLER_NAME).equals(LOGIN_CONTROLLER)
                || req.getParameter(CONTROLLER_NAME).equals(REGISTRATION_CONTROLLER))
        ){*/
        if(req.getParameter(CONTROLLER_NAME).equals(LOGIN_CONTROLLER)
                || req.getParameter(CONTROLLER_NAME).equals(REGISTRATION_CONTROLLER)
        ){
            filterChain.doFilter(request, response);
        }
        //Logged user.
        else if (nonNull(session) &&
                nonNull(session.getAttribute("id")) &&
                nonNull(session.getAttribute("email")) &&
                nonNull(session.getAttribute("role"))) {

            final AccountRole role = AccountRole.valueOf(
                    ((String) session.getAttribute("role")).toUpperCase()
                    );

            filterChain.doFilter(request, response);
            //moveToMenu(req, res, role);
        } else {
            throw new SecurityException("You don't have access to this resource", new RuntimeException());
            //moveToMenu(req, res, AccountRole.UNKNOWN);
        }
    }
    /**
     * Move user to menu.
     * If access 'admin' move to admin menu.
     * If access 'user' move to user menu.
     */
   /* private void moveToMenu(final HttpServletRequest req,
                            final HttpServletResponse res,
                            final User.ROLE role)
            throws ServletException, IOException {


        if (role.equals(User.ROLE.ADMIN)) {

            req.getRequestDispatcher("/WEB-INF/view/admin_menu.jsp").forward(req, res);

        } else if (role.equals(User.ROLE.USER)) {

            req.getRequestDispatcher("/WEB-INF/view/user_menu.jsp").forward(req, res);

        } else {

            req.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(req, res);
        }
    }*/
    @Override
    public void destroy() {
    }

}
