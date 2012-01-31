package com.philihp.boatswag.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: User
 * 
 */
@Entity
@Table(name = "boatswag_user")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int userId;

	@Column
	private String name;

	@Column
	private String facebookId;

	@Column
	private String link;

	@Column
	private String locationId;

	@Column
	private double longitude;

	@Column
	private double latitude;
	
	@Column
	private String accessToken;
	
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date accessExpires;

	public User() {
		super();
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Date getAccessExpires() {
		return accessExpires;
	}

	public void setAccessExpires(Date accessExpires) {
		this.accessExpires = accessExpires;
	}

}
