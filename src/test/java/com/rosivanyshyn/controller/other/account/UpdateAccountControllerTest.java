package com.rosivanyshyn.controller.other.account;

import com.rosivanyshyn.controller.context.AppContext;
import com.rosivanyshyn.controller.dispatcher.viewresolve.ViewResolver;
import com.rosivanyshyn.db.dao.constant.AccountRole;
import com.rosivanyshyn.db.dao.entity.Account;
import com.rosivanyshyn.exeption.AppException;
import com.rosivanyshyn.service.AccountService;
import com.rosivanyshyn.util.RequestWrapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import static com.rosivanyshyn.Constant.*;
import static com.rosivanyshyn.Constant.DAO_EXCEPTION;
import static com.rosivanyshyn.controller.dispatcher.ControllerMessageConstant.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UpdateAccountControllerTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final AccountService accountService = mock(AccountService.class);

    //-----------------------
    private final Account account = Account.builder()
            .id(ID_VALUE_LONG)
            .role(AccountRole.USER.getRole())
            .email(EMAIL_VALUE)
            .firstName(FIRST_NAME_VALUE)
            .lastName(LAST_NAME_VALUE)
            .password(PASSWORD_VALUE)
            .build();

    /**
     * Test standard behavior
     */
    @Test
    void testResolve() {
        AppContext.createInstance();
        RequestWrapper requestWrapper = new RequestWrapper(request);
        HttpSession session = requestWrapper.getSession();
        //account id
        session.setAttribute(ID_FIELD, ID_VALUE_LONG);
        //data to update
        when(request.getParameter(FIRST_NAME_FIELD)).thenReturn(FIRST_NAME_VALUE);
        when(request.getParameter(LAST_NAME_FIELD)).thenReturn(LAST_NAME_VALUE);
        //service
        when(appContext.getAccountService()).thenReturn(accountService);
        when(accountService.findAccountByField(any(String.class), any(Long.class))).thenReturn(account);
        when(accountService.updateAccount(any(Account.class))).thenReturn(true);

        ViewResolver view = new UpdateAccountController(appContext).resolve(requestWrapper, response);

        assertTrue(view.getView().contains(ACCOUNT_SUCCEED_UPDATE));
    }

    /**
     * Test if user didn't input any new data
     */
    @Test
    void testResolveWithoutInputData() {
        AppContext.createInstance();
        RequestWrapper requestWrapper = new RequestWrapper(request);
        HttpSession session = requestWrapper.getSession();
        //account id
        session.setAttribute(ID_FIELD, ID_VALUE_LONG);
        //service
        when(appContext.getAccountService()).thenReturn(accountService);
        when(accountService.findAccountByField(any(String.class), any(Long.class))).thenReturn(account);
        when(accountService.updateAccount(any(Account.class))).thenReturn(true);

        ViewResolver view = new UpdateAccountController(appContext).resolve(requestWrapper, response);

        assertTrue(view.getView().contains(ACCOUNT_NOTHING_UPDATE));
    }
    /**
     * Test controller to return AppException when something goes wrong in Service
     */
    @Test
    void testResolveError() {
        AppContext.createInstance();
        RequestWrapper requestWrapper = new RequestWrapper(request);
        HttpSession session = requestWrapper.getSession();
        //account id
        session.setAttribute(ID_FIELD, ID_VALUE_LONG);
        //data to update
        when(request.getParameter(FIRST_NAME_FIELD)).thenReturn(FIRST_NAME_VALUE);
        when(request.getParameter(LAST_NAME_FIELD)).thenReturn(LAST_NAME_VALUE);
        //service
        when(appContext.getAccountService()).thenReturn(accountService);
        when(accountService.findAccountByField(any(String.class), any(Long.class))).thenReturn(account);
        when(accountService.updateAccount(any(Account.class))).thenThrow(DAO_EXCEPTION);

        assertThrows(AppException.class,
                ()-> new UpdateAccountController(appContext).resolve(requestWrapper, response)
        );
    }
}