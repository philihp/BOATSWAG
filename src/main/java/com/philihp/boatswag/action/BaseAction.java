package com.philihp.boatswag.action;

import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.philihp.boatswag.jpa.Connection;
import com.philihp.boatswag.jpa.EntityManagerManager;
import com.philihp.boatswag.jpa.User;

import antlr.StringUtils;

abstract class BaseAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("BaseAction.execute()");
		return null;
	}

	protected User findUserByFacebookId(String facebookId) {
		EntityManager em = EntityManagerManager.get();
		TypedQuery<User> query = em.createNamedQuery("findUserByFacebookId", User.class);
		query.setParameter("facebookId", facebookId);
		List<User> results = query.getResultList();

		if(results.isEmpty()) {
			User user = new User();
			em.persist(user);
			user.setFacebookId(facebookId);
			return user;
		}
		else {
			return results.get(0);
		}
	}
	
	protected Connection findConnection(String facebookSubjectId, String facebookPredicateId) {
		EntityManager em = EntityManagerManager.get();
		TypedQuery<Connection> query = em.createNamedQuery("findConnectionBySubjectIdAndPredicateId", Connection.class);
		query.setParameter("facebookSubjectId", facebookSubjectId);
		query.setParameter("facebookPredicateId", facebookPredicateId);
		List<Connection> results = query.getResultList();

		if(results.isEmpty()) {
			Connection connection = new Connection();
			em.persist(connection);
			connection.setFacebookSubjectId(facebookSubjectId);
			connection.setFacebookPredicateId(facebookPredicateId);
			return connection;
		}
		else {
			return results.get(0);
		}
	}
	
	protected List<Connection> findConnectionsBySubjectId(String facebookSubjectId) {
		EntityManager em = EntityManagerManager.get();
		TypedQuery<Connection> query = em.createNamedQuery("findConnectionsBySubjectId", Connection.class);
		query.setParameter("facebookSubjectId", facebookSubjectId);
		List<Connection> results = query.getResultList();
		return results;
	}
	
}
