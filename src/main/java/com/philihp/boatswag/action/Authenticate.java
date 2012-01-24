package com.philihp.boatswag.action;

import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import antlr.StringUtils;

import com.philihp.boatswag.util.Facebook;

public class Authenticate extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String code = (String) request.getParameter("code");

		if (code != null && code.equals("") == false) {
			URL url = new URL(
					"https://graph.facebook.com/oauth/access_token?client_id="
							+ Facebook.client_id + "&redirect_uri="
							+ Facebook.redirect_uri + "&client_secret="
							+ Facebook.client_secret + "&code=" + code);
			try {
				String result = readURL(url);
				String accessToken = null;
				Integer expires = null;
				String[] pairs = result.split("&");
				for (String pair : pairs) {
					String[] kv = pair.split("=");
					if (kv.length != 2) {
						throw new RuntimeException("Unexpected auth response");
					} else {
						if (kv[0].equals("access_token")) {
							accessToken = kv[1];
						}
						if (kv[0].equals("expires")) {
							expires = Integer.valueOf(kv[1]);
						}
					}
				}
				
				Date expiresDate = new Date((new Date().getTime()) + expires*1000);
				request.getSession().setAttribute("accessToken", accessToken);
				request.getSession().setAttribute("accessExpires", expiresDate);
				
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			return mapping.findForward("default");
		}
		else {
			ActionForward forward = mapping.findForward("facebook");
			String path = forward.getPath();
			path = path.replaceAll("REDIRECT_URI", Facebook.redirect_uri);
			return new ActionForward(path,forward.getRedirect());
		}
	}

	private String readURL(URL url) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream is = url.openStream();
		int r;
		while ((r = is.read()) != -1) {
			baos.write(r);
		}
		return new String(baos.toByteArray());
	}
}
