package com.philihp.boatswag.action;

import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.philihp.boatswag.facebook.FBUser;
import com.philihp.boatswag.facebook.FBUserDeserializer;
import com.philihp.boatswag.facebook.FBLocation;
import com.philihp.boatswag.facebook.FBLocationDeserializer;
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

	protected User fetchUser(String accessToken, String facebookId) throws AuthenticationException, Exception {
		URL url = new URL("https://graph.facebook.com/"+facebookId+"?access_token=" + accessToken);
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
		if (connection.getResponseCode() == 400)
			throw new AuthenticationException();

		InputStream inputStream = connection.getInputStream();
		Reader reader = new InputStreamReader(inputStream);

		Gson gson = new GsonBuilder().registerTypeAdapter(FBUser.class, new FBUserDeserializer()).create();
		FBUser user = gson.fromJson(reader, FBUser.class);

		return saveUser(user);
	}

	protected FBLocation fetchLocation(String accessToken, String facebookId) throws AuthenticationException, Exception {
		URL url = new URL("https://graph.facebook.com/"+facebookId+"?access_token=" + accessToken);
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
		if (connection.getResponseCode() == 400)
			throw new AuthenticationException();

		InputStream inputStream = connection.getInputStream();
		Reader reader = new InputStreamReader(inputStream);

		Gson gson = new GsonBuilder().registerTypeAdapter(FBLocation.class, new FBLocationDeserializer()).create();
		FBLocation location = gson.fromJson(reader, FBLocation.class);

		return location;
	}
	
	protected String fetchPictureURL(String accessToken, String facebookId) throws AuthenticationException, Exception {
		System.out.println("facebookId = "+facebookId);
		URL url = new URL("https://graph.facebook.com/"+facebookId+"/picture?access_token=" + accessToken);
		HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
		if (connection.getResponseCode() == 400)
			throw new AuthenticationException();

		InputStream inputStream = connection.getInputStream();
		Reader reader = new InputStreamReader(inputStream);

		Gson gson = new Gson();
		String pictureURL = gson.fromJson(reader, String.class);
		
		return pictureURL;
	}
	
	protected List<User> findUsersWithLocations(String groupId) {
		EntityManager em = EntityManagerManager.get();
		TypedQuery<User> query = em.createQuery( "SELECT u FROM User u, Connection c WHERE c.facebookPredicateId = u.facebookId AND c.facebookSubjectId = :groupId AND u.locationId IS NOT NULL AND u.longitude IS NOT NULL AND u.latitude IS NOT NULL", User.class);
		query.setParameter("groupId", groupId);
		List<User> results = query.getResultList();
		return results;
	}

	protected User findUserByFacebookId(String facebookId) {
		EntityManager em = EntityManagerManager.get();
		TypedQuery<User> query = em.createNamedQuery("findUserByFacebookId", User.class);
		query.setParameter("facebookId", facebookId);
		List<User> results = query.getResultList();

		if (results.isEmpty()) {
			User user = new User();
			em.persist(user);
			user.setFacebookId(facebookId);
			return user;
		} else {
			return results.get(0);
		}
	}

	protected Connection findConnection(String facebookSubjectId, String facebookPredicateId) {
		EntityManager em = EntityManagerManager.get();
		TypedQuery<Connection> query = em.createNamedQuery("findConnectionBySubjectIdAndPredicateId", Connection.class);
		query.setParameter("facebookSubjectId", facebookSubjectId);
		query.setParameter("facebookPredicateId", facebookPredicateId);
		List<Connection> results = query.getResultList();

		if (results.isEmpty()) {
			Connection connection = new Connection();
			em.persist(connection);
			connection.setFacebookSubjectId(facebookSubjectId);
			connection.setFacebookPredicateId(facebookPredicateId);
			return connection;
		} else {
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

	protected User saveUser(FBUser credentials) {
		User user = findUserByFacebookId(credentials.getId());

		user.setFacebookId(credentials.getId());
		user.setLink(credentials.getLink());
		user.setName(credentials.getName());
		user.setLocationId(credentials.getLocationId());
		user.setLocationName(credentials.getLocationName());
		
		return user;
	}
}
