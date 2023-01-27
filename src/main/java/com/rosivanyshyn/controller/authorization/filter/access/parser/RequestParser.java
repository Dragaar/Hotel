package com.rosivanyshyn.controller.authorization.filter.access.parser;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;


public interface RequestParser {
	void parse(HttpServletRequest request);

	String getAction();

	Map<String, String> getQuery();
}
