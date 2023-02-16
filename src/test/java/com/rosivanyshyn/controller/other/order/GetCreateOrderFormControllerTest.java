package com.rosivanyshyn.controller.other.order;

import com.rosivanyshyn.controller.dispatcher.viewresolve.ViewResolver;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;


class GetCreateOrderFormControllerTest {
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);

    /**
     * Test standard behavior
     */
    @Test
    void testResolve() {
        ViewResolver view = new GetCreateOrderFormController().resolve(request, response);
        assertNotNull(view.getView());
    }
}