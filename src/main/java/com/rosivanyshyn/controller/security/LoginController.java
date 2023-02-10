package com.rosivanyshyn.controller.security;

import com.rosivanyshyn.controller.context.AppContext;
import com.rosivanyshyn.controller.dispatcher.Controller;
import com.rosivanyshyn.controller.dispatcher.viewresolve.ViewResolver;

import com.rosivanyshyn.db.dao.constant.AccountRole;
import com.rosivanyshyn.db.dao.entity.Account;
import com.rosivanyshyn.db.dao.implMySQL.AccountDAOImpl;
import com.rosivanyshyn.exeption.AppException;
import com.rosivanyshyn.exeption.ValidationException;
import com.rosivanyshyn.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.NonNull;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;

import static com.rosivanyshyn.controller.dispatcher.ControllerConstant.*;
import static com.rosivanyshyn.db.dao.constant.Field.*;
import static com.rosivanyshyn.exeption.Message.ACCOUNT_ISNT_EXIST;
import static com.rosivanyshyn.exeption.Message.ACCOUNT_LOGIN_ERROR;

/** Login Controller class.
 * <br> Log in to user previously created account, create new session and redirect to apartments JSP.
 *
 * @author Rostyslav Ivanyshyn.
 */
public class LoginController implements Controller {
    AccountService accountService;

    protected static final Logger LOG = Logger.getLogger(AccountDAOImpl.class);
    public LoginController(AppContext appContext){
        accountService = appContext.getAccountService();
    }
    @Override
    public ViewResolver resolve(HttpServletRequest request, HttpServletResponse response) {
        ViewResolver resolver = new ViewResolver();

        try {
            @NonNull final String email = request.getParameter("email");
            @NonNull final String password = request.getParameter("password");
            Account DBAccount = accountService.findAccountByField(ACCOUNT_EMAIL, email);

            if(DBAccount!=null && DBAccount.getPassword().equals(password)) {
                HttpSession session = request.getSession(true);

                session.setAttribute("id", DBAccount.getId());
                session.setAttribute("email", DBAccount.getEmail());

                AccountRole role = AccountRole.get(DBAccount.getRole());
                session.setAttribute("role", role);

                session.setAttribute("managerRoleName", AccountRole.MANAGER);
                session.setAttribute("userRoleName", AccountRole.USER);

                session.setAttribute("lang", "en");
                //TODO move list to resources
                session.setAttribute("languages", new ArrayList<>(Arrays.asList("en", "uk")));

                resolver.redirect("/hotel/front?controller="+ GET_APARTMENTS_CONTROLLER);
                       // "&message=" + "Successful login!");
            } else throw new ValidationException(ACCOUNT_ISNT_EXIST);

        } catch (ValidationException ex){
            throw new ValidationException(ex.getMessage(), ex);
        }
        catch (RuntimeException ex){
            throw new AppException(ACCOUNT_LOGIN_ERROR, ex);
        }
        return resolver;
    }
}
