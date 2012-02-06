package com.philihp.boatswag.action;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;
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

public class AuthenticateGetGroups extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Date expires = (Date) request.getSession().getAttribute("accessExpires");

		if (expires == null || expires.before(new Date())) {
			return mapping.findForward("authenticate");
		} else {
			String accessToken = (String) request.getSession().getAttribute("accessToken");
			URL url = new URL("https://graph.facebook.com/me/groups/?access_token=" + accessToken);
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			if (connection.getResponseCode() == 400) {
				return mapping.findForward("authenticate");
			} else {
				InputStream inputStream = connection.getInputStream();
				Reader reader = new InputStreamReader(inputStream);

				Gson gson = new GsonBuilder().registerTypeAdapter(Groups.class, new GroupsDeserializer())
						.create();
				Groups groups = gson.fromJson(reader, Groups.class);

				request.getSession().setAttribute("groups", groups.getGroups());

				return mapping.findForward("default");
			}
		}
	}

}
