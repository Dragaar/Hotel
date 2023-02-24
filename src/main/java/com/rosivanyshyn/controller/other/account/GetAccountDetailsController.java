package com.rosivanyshyn.controller.other.account;

import com.rosivanyshyn.controller.context.AppContext;
import com.rosivanyshyn.controller.dispatcher.Controller;
import com.rosivanyshyn.controller.dispatcher.viewresolve.ViewResolver;
import com.rosivanyshyn.db.dao.entity.Account;
import com.rosivanyshyn.exeption.AppException;
import com.rosivanyshyn.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.NonNull;

import static com.rosivanyshyn.controller.dispatcher.ControllerConstant.ACCOUNT_DETAILS_JSP;
import static com.rosivanyshyn.db.dao.constant.Field.ENTITY_ID;
import static com.rosivanyshyn.exeption.Message.ACCOUNT_DETAILS_ERROR;

public class GetAccountDetailsController implements Controller {
    AccountService accountService;

    public GetAccountDetailsController(AppContext appContext){
        this.accountService=appContext.getAccountService();
    }

    @Override
    public ViewResolver resolve(HttpServletRequest request, HttpServletResponse response) {
        ViewResolver resolver = new ViewResolver();

        try {
            @NonNull HttpSession session = request.getSession(false);

            @NonNull final Long accountId = (Long) session.getAttribute("id");
            Account account = accountService.findAccountByField(ENTITY_ID, accountId);

            request.setAttribute("email", account.getEmail());
            request.setAttribute("firstName", account.getFirstName());
            request.setAttribute("lastName", account.getLastName());

            resolver.forward(ACCOUNT_DETAILS_JSP);

        } catch (RuntimeException ex) {
            throw new AppException(ACCOUNT_DETAILS_ERROR, ex);
        }
        return resolver;
    }
}
