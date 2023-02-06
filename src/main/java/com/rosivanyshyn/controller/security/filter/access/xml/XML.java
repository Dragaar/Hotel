package com.rosivanyshyn.controller.security.filter.access.xml;

public enum XML {
    CONSTRAINT("constraint"),
    URL_PATTERN("url-pattern"),
    ROLE("role");
    XML(String tag) {
        this.value = tag;
    }
    private String value;
    public String value() {
        return value;
    }
}
