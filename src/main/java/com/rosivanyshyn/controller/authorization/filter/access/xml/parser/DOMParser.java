package com.rosivanyshyn.controller.authorization.filter.access.xml.parser;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.rosivanyshyn.controller.authorization.filter.access.xml.Constraint;
import com.rosivanyshyn.controller.authorization.filter.access.xml.Security;
import com.rosivanyshyn.controller.authorization.filter.access.xml.SecurityFactory;
import com.rosivanyshyn.controller.authorization.filter.access.xml.XML;
import com.rosivanyshyn.controller.dispatcher.ControllerConstant;
import com.rosivanyshyn.db.dao.constant.AccountRole;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Controller for DOM parser.
 *
 */
public class DOMParser {
	private static final Logger LOG = Logger.getLogger(DOMParser.class);
	private String xmlFileName;

	private Security security;
	private SecurityFactory factory;

	public DOMParser(final String xmlFileName) {
		this.xmlFileName = xmlFileName;
		this.factory = new SecurityFactory();
	}

	public final void parse(final boolean validate) throws ParserConfigurationException, SAXException, IOException {
		LOG.info("Parsing xml started");
		LOG.info("File to parse -> " + xmlFileName);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);

		if (validate) {
			LOG.info("Turn schema validation on");
			dbf.setFeature(ControllerConstant.FEATURE_TURN_VALIDATION_ON, true);
			dbf.setFeature(ControllerConstant.FEATURE_TURN_SCHEMA_VALIDATION_ON, true);
		}

		DocumentBuilder db = dbf.newDocumentBuilder();
		db.setErrorHandler(new DefaultHandler() {
			@Override
			public void error(final SAXParseException e) throws SAXException {
				throw e;
			}
		});

		Document document = db.parse(xmlFileName);
		Element root = document.getDocumentElement();
		security = factory.createSecurity();
		NodeList constraintNodes = root.getElementsByTagName(XML.CONSTRAINT.value());
		Constraint constraint;
		ArrayList<Constraint> constraints = new ArrayList<>();
		for (int j = 0; j < constraintNodes.getLength(); j++) {
			constraint = getConstraint(constraintNodes.item(j));
			constraints.add(constraint);
		}
		security.setConstraint(constraints);
		LOG.info("Parsed xml: " + security);
		LOG.info("Parsing xml ended");
	}

	private Constraint getConstraint(final Node qNode) {
		Constraint constraint = new Constraint();
		Element qElement = (Element) qNode;
		Node qtNode = qElement.getElementsByTagName(XML.URL_PATTERN.value()).item(0);
		constraint.setUrlPattern(qtNode.getTextContent());
		constraint.setRole(getRoles(qNode));
		return constraint;
	}

	public final ArrayList<AccountRole> getRoles(final Node qNode) {
		ArrayList<AccountRole> roles = new ArrayList<>();
		Element element = (Element) qNode;
		NodeList roleNodes = element.getElementsByTagName(XML.ROLE.value());
		String role;
		for (int j = 0; j < roleNodes.getLength(); j++) {
			role = roleNodes.item(j).getTextContent();
			roles.add(AccountRole.valueOf(role.toUpperCase()));
		}
		return roles;
	}

	public final Security getSecurity() {
		return security;
	}
}
