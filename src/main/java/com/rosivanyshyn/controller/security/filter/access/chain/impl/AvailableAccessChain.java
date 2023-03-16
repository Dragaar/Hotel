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

import static com.rosivanyshyn.controller.dispatcher.ControllerConstant.*;

/** Available Access Chain class. Implements {@link AccessChain Access chain} interface
 * <br> Check availability of requested action for current user.
 *
 * @author Rostyslav Ivanyshyn.
 */
public class AvailableAccessChain extends AccessChain {

	public AvailableAccessChain(AccessChain successor, AccessMapHolder holder, ArrayList<String> urls) {
		super(successor, holder, urls);
	}

	@Override
	public void processRequest(HttpServletRequest req, HttpServletResponse res, FilterChain chain, String action,
							   AccountRole role) throws IOException, ServletException {
		if (this.holder.get(role).contains(action)) {
			if(isLoginOrRegistrationController(action))
			{
				res.sendRedirect(req.getContextPath() + INITIALIZE_CONTROLLER + GET_APARTMENTS_CONTROLLER);
			} else {
				chain.doFilter(req, res);
			}
		} else {
			accessError();
		}

	}

	private void accessError() {
		String errorMessage = "You do not have permission " + "to access the requested resource";
		throw new SecurityException(errorMessage, new RuntimeException());
	}
	private boolean isLoginOrRegistrationController(String controller){
		return controller.equals(LOGIN_CONTROLLER) || controller.equals(REGISTRATION_CONTROLLER);
	}
}
