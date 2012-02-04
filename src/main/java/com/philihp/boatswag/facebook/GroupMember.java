package com.philihp.boatswag.facebook;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class GroupMember implements Serializable {

	private String name;
	private String id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
