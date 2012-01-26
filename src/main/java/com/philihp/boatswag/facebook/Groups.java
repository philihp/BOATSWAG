package com.philihp.boatswag.facebook;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Groups implements Serializable {

	private List<Group> groups = new LinkedList<Group>();

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

}
