package com.rosivanyshyn.controller.security.filter.access.map;

import java.util.ArrayList;

/** Access Map
 * Contains list of action to which provide access
 *
 * @author Rostyslav Ivanyshyn.
 */
public class AccessMap {
	protected ArrayList<String> urlList;

	public AccessMap(ArrayList<String> urlList) {
		this.urlList = urlList;
	}

	public void add(String action) {
		this.urlList.add(action);
	}

	public boolean contains(String action) {
		return this.urlList.contains(action);
	}

	public ArrayList<String> getUrlList() {
		return urlList;
	}
	

}
