package com.rosivanyshyn.controller.security.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rosivanyshyn.controller.security.filter.access.chain.AccessChain;
import com.rosivanyshyn.controller.security.filter.access.chain.impl.AvailableAccessChain;
import com.rosivanyshyn.controller.security.filter.access.chain.impl.RoleAccessChain;
import com.rosivanyshyn.controller.security.filter.access.chain.impl.UrlAccessChain;
import com.rosivanyshyn.controller.security.filter.access.map.AccessMap;
import com.rosivanyshyn.controller.security.filter.access.map.AccessMapHolder;
import com.rosivanyshyn.controller.security.filter.access.parser.RequestParser;
import com.rosivanyshyn.controller.security.filter.access.parser.impl.HttpRequestParserImpl;
import com.rosivanyshyn.controller.security.filter.access.xml.Constraint;
import com.rosivanyshyn.controller.security.filter.access.xml.Security;
import com.rosivanyshyn.controller.security.filter.access.xml.parser.DOMParser;
import com.rosivanyshyn.controller.dispatcher.ControllerConstant;
import com.rosivanyshyn.db.dao.constant.AccountRole;
import com.rosivanyshyn.exeption.AppException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/** Access Filter class.
 * <br> Parse XML security access list from WEB-INF to comparison with requested page and check if user have access to it
 * <br> The comparison chain consists of three stages:
 * <br> {@link UrlAccessChain if URL exist}  -> {@link RoleAccessChain if user log in} -> {@link AvailableAccessChain if user have access to page}
 * @author Rostyslav Ivanyshyn.
 */
@WebFilter("/front")
public class AccessFilter implements Filter {

	private static final Logger LOG = Logger.getLogger(AccessFilter.class);
	private Security security;
	private RequestParser parser;
	private AccessMapHolder accessMap;
	private ArrayList<String> urls;
	private AccessChain filter;

	@Override
	public final void destroy() {
		LOG.debug("Access filter destruction starts");
		// no op
		LOG.debug("Access filter destruction finished");
	}

	@Override
	public final void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {
		LOG.debug("Access filter starts");
		HttpServletResponse res = (HttpServletResponse) response;
		HttpServletRequest req = (HttpServletRequest) request;
		String uri = req.getRequestURI();
		String action = req.getParameter(ControllerConstant.CONTROLLER_NAME);

		final AccountRole role = (AccountRole) req.getSession().getAttribute("role");

		LOG.info("Role -> " + role);
		//LOG.info("URI -> " + uri);
		LOG.info("Action -> " + action);

		//For static html pages filtrating
		//parser.parse(req);
		//filter.processRequest(req, res, chain, parser.getAction(), role);

		//For front-controller pattern
		filter.processRequest(req, res, chain, action, role);
	}

	@Override
	public final void init(final FilterConfig fConfig) throws ServletException {
		LOG.debug("Access filter initialization starts");
		security = initSecurity(fConfig.getServletContext());
		parser = new HttpRequestParserImpl();
		initAccessMap(security);
		initFilter();
		LOG.debug("Access filter initialization finished");
	}

	private void initFilter() {
		AvailableAccessChain available = new AvailableAccessChain(null, accessMap, urls);
		RoleAccessChain role = new RoleAccessChain(available, accessMap, urls);
		filter = new UrlAccessChain(role, accessMap, urls);
	}

	private void initAccessMap(Security security) {
		LOG.info("Init access map started");
		Map<AccountRole, AccessMap> map = new HashMap<>();
		map.put(AccountRole.MANAGER, new AccessMap(new ArrayList<>()));
		map.put(AccountRole.USER, new AccessMap(new ArrayList<>()));
		accessMap = new AccessMapHolder(map);
		urls = new ArrayList<>();
		List<Constraint> constraints = security.getConstraint();
		for (Constraint constraint : constraints) {
			for (AccountRole role : constraint.getRole()) {
				accessMap.get(role).add(constraint.getUrlPattern());
			}
			urls.add(constraint.getUrlPattern());
		}
		LOG.info("Access map for manager role -> " + accessMap.get(AccountRole.MANAGER).getUrlList());
		LOG.info("Access map for user role -> " + accessMap.get(AccountRole.USER).getUrlList());
		LOG.info("Init access map ended");
	}

	private Security initSecurity(ServletContext context) {
		String path = ControllerConstant.SECURITY_PATH;

		String securityPath =  context.getRealPath(path);

		DOMParser parser = new DOMParser(securityPath);
		try {
			parser.parse(true);
			Security security = parser.getSecurity();
			LOG.info("Security configuration: " + security);
			return security;
		} catch (Exception ex) {
			//throw new AppException(Messages.XML_PARSER_ERROR, ex);
			throw new AppException("Security xml parser error", ex);
		}
	}
}