package com.rosivanyshyn.controller.other.account;

import com.rosivanyshyn.controller.context.AppContext;
import com.rosivanyshyn.controller.dispatcher.viewresolve.ViewResolver;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetAccountDetailsControllerTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final AppContext appContext = mock(AppContext.class);
    private final AccountService accountService = mock(AccountService.class);

    //-----------------------
    private final Account account = Account.builder()
            .id(ID_VALUE_LONG)
            .email(EMAIL_VALUE)
            .firstName(FIRST_NAME_VALUE)
            .lastName(LAST_NAME_VALUE)
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
        //service
        when(appContext.getAccountService()).thenReturn(accountService);
        when(accountService.findAccountByField(ID_FIELD, ID_VALUE_LONG)).thenReturn(account);

        ViewResolver view = new GetAccountDetailsController(appContext).resolve(requestWrapper, response);

        assertNotNull(view.getView());
        assertEquals(EMAIL_VALUE, requestWrapper.getAttribute(EMAIL_FIELD));
        assertEquals(FIRST_NAME_VALUE, requestWrapper.getAttribute(FIRST_NAME_FIELD));
        assertEquals(LAST_NAME_VALUE, requestWrapper.getAttribute(LAST_NAME_FIELD));
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
        //service
        when(appContext.getAccountService()).thenReturn(accountService);
        when(accountService.findAccountByField(ID_FIELD, ID_VALUE_LONG)).thenThrow(DAO_EXCEPTION);

        assertThrows(AppException.class,
                ()-> new GetAccountDetailsController(appContext).resolve(requestWrapper, response)
        );
    }
}
