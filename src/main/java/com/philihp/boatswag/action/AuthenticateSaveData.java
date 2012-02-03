package com.philihp.boatswag.action;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import antlr.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.philihp.boatswag.facebook.Credentials;
import com.philihp.boatswag.facebook.CredentialsDeserializer;
import com.philihp.boatswag.facebook.Group;
import com.philihp.boatswag.facebook.Groups;
import com.philihp.boatswag.facebook.Location;
import com.philihp.boatswag.jpa.EntityManagerManager;
import com.philihp.boatswag.jpa.User;

public class AuthenticateSaveData extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Date accessExpires = (Date) request.getSession().getAttribute("accessExpires");
		String accessToken = (String) request.getSession().getAttribute("accessToken");
		Credentials credentials = (Credentials) request.getSession().getAttribute("facebook");
		Location location = (Location) request.getSession().getAttribute("location");

		TypedQuery<User> query = EntityManagerManager.get().createQuery("SELECT u FROM User u WHERE u.facebookId = :facebookId", User.class);
		query.setParameter("facebookId", credentials.getId());
		List<User> results = query.getResultList();

		User user = results.isEmpty()?new User():results.get(0);
		
		user.setFacebookId(credentials.getId());
		user.setLocationId(location.getId());
		user.setLatitude(location.getLatitude());
		user.setLongitude(location.getLongitude());
		user.setLink(credentials.getLink());
		user.setName(credentials.getName());
		user.setAccessExpires(accessExpires);
		user.setAccessToken(accessToken);

		EntityManagerManager.get().persist(user);

		return mapping.findForward("default");

	}

}
