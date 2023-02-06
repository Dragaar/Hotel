package com.rosivanyshyn.controller.security.filter.access.map;

import java.util.Map;

import com.rosivanyshyn.db.dao.constant.AccountRole;

public class AccessMapHolder {
	private Map<AccountRole, AccessMap> map;

	public AccessMapHolder(Map<AccountRole, AccessMap> map) {
		this.map = map;
	}

	public AccessMap get(final AccountRole role) {
		if (role == null || !map.containsKey(role)) {
			return map.get(AccountRole.USER);
		}
		return map.get(role);
	}

}
