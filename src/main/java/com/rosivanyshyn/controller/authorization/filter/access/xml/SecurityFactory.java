package com.rosivanyshyn.controller.authorization.filter.access.xml;

import jakarta.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class SecurityFactory {

	public SecurityFactory() {
	}

	public Security createSecurity() {
		return new Security();
	}

	public Constraint createConstraint() {
		return new Constraint();
	}

}
