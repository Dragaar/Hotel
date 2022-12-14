package com.rosivanyshyn.controller.authorization;

import com.rosivanyshyn.controller.dispatcher.Controller;
import com.rosivanyshyn.controller.dispatcher.viewresolve.ViewResolver;

import com.rosivanyshyn.db.dao.entity.Account;
import com.rosivanyshyn.db.dao.implMySQL.AccountDAOImpl;
import com.rosivanyshyn.exeption.ValidationException;
import com.rosivanyshyn.service.AccountService;
import com.rosivanyshyn.service.implMySQL.AccountServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.NonNull;
import org.apache.log4j.Logger;
import org.apache.log4j.lf5.DefaultLF5Configurator;

import java.io.IOException;

import static com.rosivanyshyn.controller.dispatcher.ControllerConstant.*;
import static com.rosivanyshyn.db.dao.constant.Field.*;
import static com.rosivanyshyn.exeption.Message.SELECT_BY_FIELD_ERROR;

public class LoginController implements Controller {
    AccountService accountService = new AccountServiceImpl();

    protected static final Logger LOG = Logger.getLogger(AccountDAOImpl.class);

    public LoginController() {
        try { DefaultLF5Configurator.configure();
        } catch (IOException e) {throw new RuntimeException(e);}
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
                session.setAttribute("role", DBAccount.getRole());

                resolver.redirect("/hotel/front?controller="+ GET_APARTMENTS_CONTROLLER +
                        "&message=" + "Successful login!");
            } else throw new RuntimeException("Account isn`t exist!");

        } catch (RuntimeException ex){
            throw new ValidationException("Wrong login input data", ex);
        }
        return resolver;
    }
}
