package com.rosivanyshyn.controller.authorization.filter.access.xml;

import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "constraint" })
@XmlRootElement(name = "security")
public class Security {

	@XmlElement(required = true)
	protected List<Constraint> constraint;

	public void setConstraint(List<Constraint> constraint) {
		this.constraint = constraint;
	}

	public List<Constraint> getConstraint() {
		if (constraint == null) {
			constraint = new ArrayList<Constraint>();
		}
		return this.constraint;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((constraint == null) ? 0 : constraint.hashCode());
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
		Security other = (Security) obj;
		if (constraint == null) {
			if (other.constraint != null)
				return false;
		} else if (!constraint.equals(other.constraint))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Security [constraint=" + constraint + "]";
	}

}
