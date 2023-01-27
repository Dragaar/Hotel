package com.rosivanyshyn.controller.authorization.filter.access.xml;

import java.util.ArrayList;
import java.util.List;

import com.rosivanyshyn.db.dao.constant.AccountRole;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Constraint", propOrder = { "urlPattern", "role" })
public class Constraint {

	@XmlElement(name = "url-pattern", required = true)
	protected String urlPattern;
	@XmlElement(required = true)
	protected List<AccountRole> role;

	public String getUrlPattern() {
		return urlPattern;
	}

	public void setUrlPattern(String value) {
		this.urlPattern = value;
	}

	public List<AccountRole> getRole() {
		if (role == null) {
			role = new ArrayList<AccountRole>();
		}
		return this.role;
	}

	public void setRole(List<AccountRole> role) {
		this.role = role;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((urlPattern == null) ? 0 : urlPattern.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Constraint other = (Constraint) obj;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		if (urlPattern == null) {
			if (other.urlPattern != null)
				return false;
		} else if (!urlPattern.equals(other.urlPattern))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Constraint [urlPattern=" + urlPattern + ", role=" + role + "]";
	}

}
