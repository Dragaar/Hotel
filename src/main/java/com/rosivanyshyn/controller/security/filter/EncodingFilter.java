package com.rosivanyshyn.controller.security.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;

import java.io.IOException;

/** Encoding Filter class.
 * <br> Check and change page character encoding to UTF-8
 *
 * @author Rostyslav Ivanyshyn.
 */
@WebFilter(urlPatterns = { "/*" },
        initParams = {
                @WebInitParam(name = "encoding", value = "UTF-8", description = "Encoding Param") })
public class EncodingFilter implements Filter {
    private String code;
    public void init(FilterConfig fConfig) {
        code = fConfig.getInitParameter("encoding");
    }
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        String codeRequest = request.getCharacterEncoding();

        if (code != null && !code.equalsIgnoreCase(codeRequest)) {
            request.setCharacterEncoding(code);
            response.setCharacterEncoding(code);
        }
        chain.doFilter(request, response);
    }
    public void destroy() {
        code = null;
    }
}