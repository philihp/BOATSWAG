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
	@Column(name = "user_id")
	private int userId;

	@Column(name = "name")
	private String name;

	@Column(name = "facebook_id")
	private String facebookId;

	@Column(name = "link")
	private String link;

	@Column(name = "location_id")
	private String locationId;

	@Column(name = "longitude")
	private double longitude;

	@Column(name = "latitude")
	private double latitude;

	@Column(name = "access_token")
	private String accessToken;

	@Column(name = "access_expires")
	@Temporal(TemporalType.TIMESTAMP)
	private Date accessExpires;

	@Column(name = "date_created")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated;

	@Column(name = "date_updated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateUpdated;

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

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	@PreUpdate
	@PrePersist
	public void updateTimeStamps() {
		Date now = new Date();
		setDateUpdated(now);
		if (getDateCreated() == null) {
			setDateCreated(now);
		}
	}

}
