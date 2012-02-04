package com.philihp.boatswag.facebook;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class GroupMembers implements Serializable {

	private List<GroupMember> groupMembers = new LinkedList<GroupMember>();
	
	private String next;
	private String previous;

	public List<GroupMember> getGroupMembers() {
		return groupMembers;
	}

	public void setGroupMembers(List<GroupMember> groupMembers) {
		this.groupMembers = groupMembers;
	}

	public String getNext() {
		return next;
	}

	public void setNext(String next) {
		this.next = next;
	}

	public String getPrevious() {
		return previous;
	}

	public void setPrevious(String previous) {
		this.previous = previous;
	}

}
