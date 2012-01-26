package com.philihp.boatswag.facebook;

import java.io.Serializable;

public class Group implements Serializable {

	private int version;
	private String name;
	private String id;
	private int unread;
	private int bookmarkOrder;

	public Group() {
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

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

	public int getUnread() {
		return unread;
	}

	public void setUnread(int unread) {
		this.unread = unread;
	}

	public int getBookmarkOrder() {
		return bookmarkOrder;
	}

	public void setBookmarkOrder(int bookmarkOrder) {
		this.bookmarkOrder = bookmarkOrder;
	}
}