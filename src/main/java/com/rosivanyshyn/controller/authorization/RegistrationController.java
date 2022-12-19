package com.rosivanyshyn.controller.authorization;

import com.rosivanyshyn.controller.dispatcher.Controller;
import com.rosivanyshyn.controller.dispatcher.viewresolve.ViewResolver;
import com.rosivanyshyn.db.dao.constant.AccountRole;
import com.rosivanyshyn.db.dao.entity.Account;
import com.rosivanyshyn.exeption.ValidationException;
import com.rosivanyshyn.service.AccountService;
import com.rosivanyshyn.service.implMySQL.AccountServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.apache.log4j.lf5.DefaultLF5Configurator;

import java.io.IOException;

import static com.rosivanyshyn.controller.dispatcher.ControllerConstant.*;

public class RegistrationController implements Controller {
    AccountService accountService = new AccountServiceImpl();

    @Override
    public ViewResolver resolve(HttpServletRequest request, HttpServletResponse response) {
        ViewResolver resolver = new ViewResolver();

        //валидация -
        //https://github.com/muhireheir/userAuth/blob/main/src/main/java/com/controllers/RegisterFilter.java
        try {

            @NonNull final String email = request.getParameter("email");
            @NonNull final String firstName = request.getParameter("firstName");
            @NonNull final String lastName = request.getParameter("lastName");
            @NonNull final String password = request.getParameter("password");

            Account account = Account.builder()
                    .id(0L)
                    .role(AccountRole.USER.getRole())
                    .firstName(firstName)
                    .lastName(lastName)
                    .email(email)
                    .password(password)
                    .state(true)
                    .build();

            accountService.createAccount(account);

            resolver.redirect(request.getContextPath() + LOGIN_JSP +
                    "?message=" + "Your account has been successfully registered!");

        } catch (RuntimeException ex){
            throw new ValidationException("Wrong registration input data", ex);
        }
        return resolver;
    }
}
