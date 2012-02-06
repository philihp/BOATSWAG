package com.philihp.boatswag.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import static javax.persistence.AccessType.PROPERTY;
import static javax.persistence.AccessType.FIELD;

/**
 * Entity implementation class for Entity: User
 * 
 */
@Entity(name = "User")
@Table(name = "boatswag_user")
@Access(FIELD)
@NamedQuery(name = "findUserByFacebookId", query = "SELECT u FROM User u WHERE u.facebookId = :facebookId")
public class User extends BasicEntity implements Serializable {

	@Id
	@GeneratedValue
	@Column(name = "user_id")
	private int userId;

	@Column(name = "name")
	@Basic(optional = false)
	private String name;

	@Column(name = "facebook_id")
	@Basic(optional = false)
	private String facebookId;

	@Column(name = "link")
	private String link;

	@Column(name = "location_id")
	private String locationId;

	@Column(name = "longitude")
	private Double longitude;

	@Column(name = "latitude")
	private Double latitude;

	@Column(name = "access_token")
	private String accessToken;

	@Column(name = "access_expires")
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

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
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
