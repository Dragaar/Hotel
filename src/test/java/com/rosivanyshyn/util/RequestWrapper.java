package com.rosivanyshyn.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestWrapper extends HttpServletRequestWrapper {
    private final Map<String, Object> attributes = new HashMap<>();
    private final List<Cookie> cookies = new ArrayList<>();
    private final HttpSession session = new SessionWrapper();
    private String encoding;

    public RequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public void setAttribute(String name, Object object) {
        attributes.put(name, object);
    }

    @Override
    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    @Override
    public HttpSession getSession() {
        return session;
    }

    @Override
    public HttpSession getSession(boolean create) {
        return session;
    }

    @Override
    public String getCharacterEncoding() {
        return encoding;
    }

    @Override
    public void setCharacterEncoding(String enc) {
        encoding = enc;
    }

    @Override
    public Cookie[] getCookies() {
        return cookies.isEmpty() ? null : cookies.toArray(Cookie[]::new);
    }

    public void addCookie(String name, String value) {
        cookies.add(new Cookie(name, value));
    }
}
