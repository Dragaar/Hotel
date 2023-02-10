package com.rosivanyshyn.controller.security;

import com.rosivanyshyn.controller.context.AppContext;
import com.rosivanyshyn.controller.dispatcher.Controller;
import com.rosivanyshyn.controller.dispatcher.viewresolve.ViewResolver;
import com.rosivanyshyn.db.dao.constant.AccountRole;
import com.rosivanyshyn.db.dao.entity.Account;
import com.rosivanyshyn.exeption.AppException;
import com.rosivanyshyn.exeption.ValidationException;
import com.rosivanyshyn.service.AccountService;
import com.rosivanyshyn.utils.Validation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;

import static com.rosivanyshyn.controller.dispatcher.ControllerConstant.*;
import static com.rosivanyshyn.controller.dispatcher.ControllerMessageConstant.ACCOUNT_SUCCEED_REGISTRATION;
import static com.rosivanyshyn.exeption.Message.ACCOUNT_REGISTRATION_ERROR;

/** Registration Controller class.
 * <br> Add new user to database and redirect to log in JSP
 *
 * @author Rostyslav Ivanyshyn.
 */
public class RegistrationController implements Controller {
    AccountService accountService;
    public RegistrationController(AppContext appContext){
        accountService = appContext.getAccountService();
    }
    @Override
    public ViewResolver resolve(HttpServletRequest request, HttpServletResponse response) {
        ViewResolver resolver = new ViewResolver();

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

            Validation validation = new Validation();
            validation.validateAccount(account);

            accountService.createAccount(account);

            resolver.redirect(request.getContextPath() + LOGIN_JSP +
                    "?message=" + ACCOUNT_SUCCEED_REGISTRATION);

        } catch (ValidationException ex){
            throw new ValidationException(ex.getMessage(), ex);
        }
        catch (RuntimeException ex){
            throw new AppException(ACCOUNT_REGISTRATION_ERROR, ex);
        }
        return resolver;
    }
}
