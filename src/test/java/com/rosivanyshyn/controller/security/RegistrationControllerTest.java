package com.rosivanyshyn.controller.security;

import com.rosivanyshyn.controller.context.AppContext;
import com.rosivanyshyn.controller.dispatcher.viewresolve.ViewResolver;
import com.rosivanyshyn.db.dao.entity.Account;
import com.rosivanyshyn.exeption.AppException;
import com.rosivanyshyn.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import static com.rosivanyshyn.Constant.*;
import static com.rosivanyshyn.controller.dispatcher.ControllerMessageConstant.ACCOUNT_SUCCEED_REGISTRATION;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RegistrationControllerTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final AccountService accountService = mock(AccountService.class);

    /**
     * Test standard behavior
     */
    @Test
    void testResolve() {

        AppContext.createInstance();

        when(request.getParameter(EMAIL_FIELD)).thenReturn(EMAIL_VALUE);
        when(request.getParameter(FIRST_NAME_FIELD)).thenReturn(FIRST_NAME_VALUE);
        when(request.getParameter(LAST_NAME_FIELD)).thenReturn(LAST_NAME_VALUE);
        when(request.getParameter(PASSWORD_FIELD)).thenReturn(PASSWORD_VALUE);
        //service
        when(appContext.getAccountService()).thenReturn(accountService);
        when(accountService.createAccount(any(Account.class))).thenReturn(true);

        ViewResolver view = new RegistrationController(appContext).resolve(request, response);

        assertNotNull(view.getView());
        assertTrue(view.getView().contains(ACCOUNT_SUCCEED_REGISTRATION));
    }

    /**
     * Test controller to return AppException when something goes wrong in Service
     */
    @Test
    void testResolveError() {
        AppContext.createInstance();

        when(request.getParameter(EMAIL_FIELD)).thenReturn(EMAIL_VALUE);
        when(request.getParameter(FIRST_NAME_FIELD)).thenReturn(FIRST_NAME_VALUE);
        when(request.getParameter(LAST_NAME_FIELD)).thenReturn(LAST_NAME_VALUE);
        when(request.getParameter(PASSWORD_FIELD)).thenReturn(PASSWORD_VALUE);
        //service
        when(appContext.getAccountService()).thenReturn(accountService);
        when(accountService.createAccount(any(Account.class))).thenThrow(DAO_EXCEPTION);

        assertThrows(AppException.class,
                ()-> new RegistrationController(appContext).resolve(request, response)
        );
    }
}