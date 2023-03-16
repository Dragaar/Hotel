package com.rosivanyshyn.controller.security.filter.access.chain;

import java.io.IOException;
import java.util.ArrayList;

import com.rosivanyshyn.controller.security.filter.access.map.AccessMapHolder;
import com.rosivanyshyn.db.dao.constant.AccountRole;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/** Access Chain interface.
 * <br> Each chain provide security logic for different situation
 */
public abstract class AccessChain {
	protected AccessChain successor;
	protected AccessMapHolder holder;
	protected ArrayList<String> urls;

	public AccessChain(AccessChain successor, AccessMapHolder holder, ArrayList<String> urls) {
		this.successor = successor;
		this.holder = holder;
		this.urls = urls;
	}

	/** Process user request
	 *
	 * @param req User request
	 * @param res Response to user
	 * @param chain Application filter chain
	 * @param action Current Action
	 * @param role Current user role
	 * @throws IOException
	 * @throws ServletException
	 */
	abstract public void processRequest(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
										String action, AccountRole role) throws IOException, ServletException;

}
