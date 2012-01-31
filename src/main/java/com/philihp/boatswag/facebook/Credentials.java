package com.philihp.boatswag.facebook;

import java.io.Serializable;

public class Credentials implements Serializable {

	private String id;
	private String name;
	private String firstName;
	private String link;
	private String username;
	private String locationId;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String toString() {
		return "FacebookCredentials{id=" + id + ", name=\"" + name
				+ "\", firstName=\"" + firstName + "\", link=\"" + link + "\"}";
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
}
