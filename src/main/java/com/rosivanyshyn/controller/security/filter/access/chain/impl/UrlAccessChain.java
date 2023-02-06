package com.rosivanyshyn.controller.security.filter.access.chain.impl;

import java.io.IOException;
import java.util.ArrayList;

import com.rosivanyshyn.controller.security.filter.access.chain.AccessChain;
import com.rosivanyshyn.controller.security.filter.access.map.AccessMapHolder;
import com.rosivanyshyn.db.dao.constant.AccountRole;
import com.rosivanyshyn.exeption.SecurityException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UrlAccessChain extends AccessChain {

	public UrlAccessChain(AccessChain successor, AccessMapHolder holder, ArrayList<String> urls) {
		super(successor, holder, urls);
	}

	@Override
	public void processRequest(HttpServletRequest req, HttpServletResponse res, FilterChain chain, String action,
							   AccountRole role) throws IOException, ServletException {
		if (urls.contains(action)) {
			if (successor != null) {
				successor.processRequest(req, res, chain, action, role);
			}
		} else {
			String errorMessage = "This source is unavailable";
			throw new SecurityException(errorMessage, new RuntimeException());
		}
	}

}
