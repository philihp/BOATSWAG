package com.philihp.boatswag.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.eclipse.persistence.annotations.ConversionValue;
import org.eclipse.persistence.annotations.ObjectTypeConverter;
import static javax.persistence.AccessType.PROPERTY;
import static javax.persistence.AccessType.FIELD;

/**
 * Entity implementation class for Entity: User
 * 
 */
@Entity(name = "Connection")
@Table(name = "boatswag_connection")
@NamedQueries({
		@NamedQuery(name = "findConnectionsBySubjectId", query = "SELECT c FROM Connection c WHERE c.facebookSubjectId = :facebookSubjectId"),
		@NamedQuery(name = "findConnectionsByPredicateId", query = "SELECT c FROM Connection c WHERE c.facebookPredicateId = :facebookPredicateId"),
		@NamedQuery(name = "findConnectionBySubjectIdAndPredicateId", query = "SELECT c FROM Connection c WHERE c.facebookSubjectId = :facebookSubjectId AND c.facebookPredicateId = :facebookPredicateId") })
@Access(FIELD)
public class Connection extends BasicEntity implements Serializable {

	@Id
	@GeneratedValue
	@Column(name = "connection_id")
	private int connectionId;

	@Column(name = "facebook_subject_id")
	@Basic(optional = false)
	private String facebookSubjectId;

	@Column(name = "facebook_predicate_id")
	@Basic(optional = false)
	private String facebookPredicateId;

	public int getConnectionId() {
		return connectionId;
	}

	public void setConnectionId(int connectionId) {
		this.connectionId = connectionId;
	}

	public String getFacebookSubjectId() {
		return facebookSubjectId;
	}

	public void setFacebookSubjectId(String facebookSubjectId) {
		this.facebookSubjectId = facebookSubjectId;
	}

	public String getFacebookPredicateId() {
		return facebookPredicateId;
	}

	public void setFacebookPredicateId(String facebookPredicateId) {
		this.facebookPredicateId = facebookPredicateId;
	}

}
