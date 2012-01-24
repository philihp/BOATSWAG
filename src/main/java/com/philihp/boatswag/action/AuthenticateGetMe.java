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

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import antlr.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.philihp.boatswag.util.Facebook;
import com.philihp.boatswag.util.FacebookCredentials;
import com.philihp.boatswag.util.FacebookCredentialsDeserializer;

public class AuthenticateGetMe extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Date expires = (Date) request.getSession().getAttribute("expires");

		if (expires != null && expires.before(new Date())) {
			return mapping.findForward("authenticate");
		} else {
			String accessToken = (String) request.getSession().getAttribute("accessToken");
			System.out.println("access token: "+accessToken);
			URL url = new URL("https://graph.facebook.com/me/?access_token=" + accessToken);
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			if (connection.getResponseCode() == 400) {
				return mapping.findForward("authenticate");
			} else {
				InputStream inputStream = connection.getInputStream();
				Reader reader = new InputStreamReader(inputStream);

				Gson gson = new GsonBuilder().registerTypeAdapter(FacebookCredentials.class,
						new FacebookCredentialsDeserializer()).create();
				FacebookCredentials credentials = gson.fromJson(reader, FacebookCredentials.class);

				request.getSession().setAttribute("facebook", credentials);

				return mapping.findForward("default");
			}
		}
	}

}
