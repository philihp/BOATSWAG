package com.philihp.boatswag.action;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.philihp.boatswag.facebook.Credentials;
import com.philihp.boatswag.facebook.Groups;
import com.philihp.boatswag.facebook.GroupsDeserializer;
import com.philihp.boatswag.facebook.Location;
import com.philihp.boatswag.facebook.LocationDeserializer;
import com.philihp.boatswag.jpa.EntityManagerManager;
import com.philihp.boatswag.jpa.User;

public class RefreshLocation extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Date expires = (Date) request.getSession().getAttribute("accessExpires");
		String accessToken = (String) request.getSession().getAttribute("accessToken");
		Credentials credentials = (Credentials) request.getSession().getAttribute("credentials");

		if (expires == null || expires.before(new Date())) {
			return mapping.findForward("authenticate");
		} else {

System.out.println("pulling location");

			URL url = new URL("https://graph.facebook.com/" + credentials.getLocationId() + "?access_token=" + accessToken);
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			if (connection.getResponseCode() == 400) {
				return mapping.findForward("authenticate");
			} else {
				InputStream inputStream = connection.getInputStream();
				Reader reader = new InputStreamReader(inputStream);

				Gson gson = new GsonBuilder().registerTypeAdapter(Location.class, new LocationDeserializer()).create();
				Location location = gson.fromJson(reader, Location.class);

				request.getSession().setAttribute("location", location);
				saveLocation(credentials.getId(),location);

				return mapping.findForward("default");
			}
		}
	}

	private void saveLocation(String facebookId, Location location) {
		User user = findUserByFacebookId(facebookId);

		user.setFacebookId(facebookId);
		user.setLocationId(location.getId());
		user.setLatitude(location.getLatitude());
		user.setLongitude(location.getLongitude());
		
	}

}
