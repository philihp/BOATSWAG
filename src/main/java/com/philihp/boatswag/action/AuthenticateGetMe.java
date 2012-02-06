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
import com.philihp.boatswag.jpa.EntityManagerManager;
import com.philihp.boatswag.jpa.User;

public class AuthenticateGetMe extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Date expires = (Date) request.getSession().getAttribute("accessExpires");

System.out.println("authenticate get me ");
		
		if (expires == null || expires.before(new Date())) {
			return mapping.findForward("authenticate");
		} else {
			String accessToken = (String) request.getSession().getAttribute("accessToken");
			URL url = new URL("https://graph.facebook.com/me/?access_token=" + accessToken);
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			if (connection.getResponseCode() == 400) {
				return mapping.findForward("authenticate");
			} else {
				InputStream inputStream = connection.getInputStream();
				Reader reader = new InputStreamReader(inputStream);

				Gson gson = new GsonBuilder().registerTypeAdapter(Credentials.class, new CredentialsDeserializer()).create();
				Credentials credentials = gson.fromJson(reader, Credentials.class);

				request.getSession().setAttribute("credentials", credentials);
				saveUser(credentials, accessToken, expires);

				return mapping.findForward("default");
			}
		}
	}

	private void saveUser(Credentials credentials, String accessToken, Date accessExpires) {
		User user = findUserByFacebookId(credentials.getId());

		user.setFacebookId(credentials.getId());
		user.setLink(credentials.getLink());
		user.setName(credentials.getName());
		user.setLocationId(credentials.getLocationId());
		user.setAccessExpires(accessExpires);
		user.setAccessToken(accessToken);
		
		System.out.println("name = "+user.getName());
		System.out.println("link = "+user.getLink());
		System.out.println("facebookId = "+user.getFacebookId());
	}

}
