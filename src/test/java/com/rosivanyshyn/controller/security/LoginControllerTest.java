package com.rosivanyshyn.controller.security;

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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LoginControllerTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final AccountService accountService = mock(AccountService.class);

    //-----------------------
    private final Account account = Account.builder()
            .id(ID_VALUE_LONG)
            .email(EMAIL_VALUE)
            .role(AccountRole.USER.getRole())
            .password(PASSWORD_VALUE).build();

    /**
     * Test standard behavior
     */
    @Test
    void testResolve() {

        AppContext.createInstance();
        RequestWrapper requestWrapper = new RequestWrapper(request);
        HttpSession session = requestWrapper.getSession();

        when(request.getParameter(EMAIL_FIELD)).thenReturn(EMAIL_VALUE);
        when(request.getParameter(PASSWORD_FIELD)).thenReturn(PASSWORD_VALUE);
        //service
        when(appContext.getAccountService()).thenReturn(accountService);
        when(accountService.findAccountByField(any(String.class), any(String.class))).thenReturn(account);

        ViewResolver view = new LoginController(appContext).resolve(requestWrapper, response);

        assertNotNull(view.getView());
        assertEquals(ID_VALUE_LONG, session.getAttribute(ID_FIELD));
        assertEquals(EMAIL_VALUE, session.getAttribute(EMAIL_FIELD));
        assertEquals(AccountRole.USER, session.getAttribute(ROLE_FIELD));
    }

    /**
     * Test controller to return AppException when something goes wrong in Service
     */
    @Test
    void testResolveError() {
        AppContext.createInstance();
        RequestWrapper requestWrapper = new RequestWrapper(request);

        when(request.getParameter(EMAIL_FIELD)).thenReturn(EMAIL_VALUE);
        when(request.getParameter(PASSWORD_FIELD)).thenReturn(PASSWORD_VALUE);
        //service
        when(appContext.getAccountService()).thenReturn(accountService);
        when(accountService.findAccountByField(any(String.class), any(String.class))).thenThrow(DAO_EXCEPTION);

        assertThrows(AppException.class,
                ()-> new LoginController(appContext).resolve(requestWrapper, response)
        );
    }
}