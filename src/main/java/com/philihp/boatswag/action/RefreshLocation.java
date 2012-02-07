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
import com.philihp.boatswag.facebook.FBUser;
import com.philihp.boatswag.facebook.Groups;
import com.philihp.boatswag.facebook.GroupsDeserializer;
import com.philihp.boatswag.facebook.FBLocation;
import com.philihp.boatswag.facebook.FBLocationDeserializer;
import com.philihp.boatswag.jpa.EntityManagerManager;
import com.philihp.boatswag.jpa.User;

public class RefreshLocation extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String accessToken = (String) request.getSession().getAttribute("accessToken");
		FBUser credentials = (FBUser) request.getSession().getAttribute("credentials");
		
		try {
			if(credentials == null) throw new AuthenticationException();

			if(credentials.getLocationId() != null) {
				System.out.println("pulling location for " + credentials.getId());
				FBLocation location = fetchLocation(accessToken, credentials.getLocationId());
				saveLocation(credentials.getId(), location);
			}
			else {
				System.out.println("pulling location for "+credentials.getId() + " -- no location!");
			}

			return mapping.findForward("default");
		}
		catch(AuthenticationException e) {
			return mapping.findForward("authenticate");
		}
	}

	private void saveLocation(String facebookId, FBLocation location) {
		User user = findUserByFacebookId(facebookId);

		user.setFacebookId(facebookId);
		user.setLocationId(location.getId());
		user.setLatitude(location.getLatitude());
		user.setLongitude(location.getLongitude());
	}

}
