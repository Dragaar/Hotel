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

/** Role Access Chain class. Implements {@link AccessChain Access chain} interface
 * <br> Check whether user is login into the session.
 * <br> Otherwise redirect to login or registration page.
 *
 * @author Rostyslav Ivanyshyn.
 */
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
		} else if(isLoginOrRegistrationController(action)){
			chain.doFilter(req, res);
		} else {
			res.sendRedirect(req.getContextPath() + LOGIN_JSP);
		}

	}

	private boolean isLoginOrRegistrationController(String controller){
		return controller.equals(LOGIN_CONTROLLER) || controller.equals(REGISTRATION_CONTROLLER);
	}

}
