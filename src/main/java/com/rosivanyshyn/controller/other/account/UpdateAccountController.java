package com.rosivanyshyn.controller.other.account;

import com.rosivanyshyn.controller.context.AppContext;
import com.rosivanyshyn.controller.dispatcher.Controller;
import com.rosivanyshyn.controller.dispatcher.viewresolve.ViewResolver;
import com.rosivanyshyn.db.dao.entity.Account;
import com.rosivanyshyn.exeption.AppException;
import com.rosivanyshyn.exeption.ValidationException;
import com.rosivanyshyn.service.AccountService;
import com.rosivanyshyn.utils.Validation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.NonNull;

import static com.rosivanyshyn.controller.dispatcher.ControllerConstant.GET_APARTMENTS_CONTROLLER;
import static com.rosivanyshyn.controller.dispatcher.ControllerConstant.INITIALIZE_CONTROLLER;
import static com.rosivanyshyn.controller.dispatcher.ControllerMessageConstant.*;
import static com.rosivanyshyn.db.dao.constant.Field.ENTITY_ID;
import static com.rosivanyshyn.exeption.Message.*;

/** Update Account Controller class.
 * <br> Update account by id and redirect to apartments JSP
 * @author Rostyslav Ivanyshyn.
 */
public class UpdateAccountController implements Controller {
    AccountService accountService;

    public UpdateAccountController(AppContext appContext){
        this.accountService=appContext.getAccountService();
    }
    @Override
    public ViewResolver resolve(HttpServletRequest request, HttpServletResponse response) {
        ViewResolver resolver = new ViewResolver();

        try {
            @NonNull HttpSession session = request.getSession(false);

            @NonNull final Long accountId = (Long) session.getAttribute("id");
            Account account = accountService.findAccountByField(ENTITY_ID, accountId);

            final String firstName = request.getParameter("firstName");
            final String lastName = request.getParameter("lastName");
            final String oldPassword = request.getParameter("oldPassword");
            final String newPassword = request.getParameter("newPassword");

            boolean areChangesExist = false;

            if(isNotBlank(firstName)){
                account.setFirstName(firstName);
                areChangesExist=true;
            }
            if(isNotBlank(lastName)){
                account.setLastName(lastName);
                areChangesExist=true;
            }
            if(isNotBlank(oldPassword) && isNotBlank(newPassword))
            {
                if(oldPassword.equals(account.getPassword())) {
                    account.setPassword(newPassword);
                    areChangesExist = true;
                } else { throw new ValidationException(INCORRECT_ACCOUNT_PASSWORD); }
            }

            if(areChangesExist){
                Validation validation = new Validation();
                validation.validateAccount(account);

                accountService.updateAccount(account);
                resolver.redirect(request.getContextPath() + INITIALIZE_CONTROLLER + GET_APARTMENTS_CONTROLLER +
                        "&message=" + ACCOUNT_SUCCEED_UPDATE);
            } else {
                resolver.redirect(request.getContextPath() + INITIALIZE_CONTROLLER + GET_APARTMENTS_CONTROLLER +
                        "&message=" + ACCOUNT_NOTHING_UPDATE);
            }

        } catch (ValidationException ex){
            throw new ValidationException(ex.getMessage(), ex);
        }
        catch (RuntimeException ex){
            throw new AppException(ACCOUNT_UPDATE_ERROR, ex);
        }
        return resolver;
    }

    private boolean isBlank(String field) {
        return field == null || field.isEmpty();
    }
    private boolean isNotBlank(String field) {
        return !isBlank(field);
    }
}
