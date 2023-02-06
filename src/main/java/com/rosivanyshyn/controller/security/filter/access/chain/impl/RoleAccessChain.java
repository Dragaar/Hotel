package com.rosivanyshyn.controller.security.filter.access.chain.impl;

import java.io.IOException;
import java.util.ArrayList;

import com.rosivanyshyn.controller.security.filter.access.chain.AccessChain;
import com.rosivanyshyn.controller.security.filter.access.map.AccessMapHolder;
import com.rosivanyshyn.db.dao.constant.AccountRole;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.rosivanyshyn.controller.dispatcher.ControllerConstant.*;

public class RoleAccessChain extends AccessChain {

	public RoleAccessChain(AccessChain successor, AccessMapHolder holder, ArrayList<String> urls) {
		super(successor, holder, urls);
	}

	@Override
	public void processRequest(HttpServletRequest req, HttpServletResponse res, FilterChain chain, String action,
							   AccountRole role) throws IOException, ServletException {
		if (role != null) {
			if (successor != null) {
				successor.processRequest(req, res, chain, action, role);
			}
		} else if(action.equals(LOGIN_CONTROLLER)
				|| action.equals(REGISTRATION_CONTROLLER)
		){
			chain.doFilter(req, res);
		} else {
			res.sendRedirect(req.getContextPath() + LOGIN_JSP);
		}

	}

}
