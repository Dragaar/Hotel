package com.rosivanyshyn.controller.security;

import com.rosivanyshyn.controller.dispatcher.viewresolve.ViewResolver;
import com.rosivanyshyn.exeption.AppException;
import com.rosivanyshyn.util.RequestWrapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import static com.rosivanyshyn.controller.dispatcher.ControllerMessageConstant.ACCOUNT_SUCCEED_LOGOUT;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class LogoutControllerTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    @Test
    void testResolve() {
        RequestWrapper requestWrapper = new RequestWrapper(request);
        ViewResolver view = new LogoutController().resolve(requestWrapper, response);

        assertTrue(view.getView().contains(ACCOUNT_SUCCEED_LOGOUT));
    }
    /**
     * Test controller to return AppException when session isn`t exist
     */
    @Test
    void testResolveError() {
        assertThrows(AppException.class,
                ()-> new LogoutController().resolve(request, response)
        );
    }
}