package com.philihp.boatswag.action;

import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import antlr.StringUtils;

public class Authenticate extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
System.out.println("authenticating");

		ServletContext servletContext = getServlet().getServletContext();

		String code = (String) request.getParameter("code");

		if (code != null && code.equals("") == false) {

			URL url = new URL(
					"https://graph.facebook.com/oauth/access_token?client_id="
							+ servletContext.getAttribute("facebook.id")
							+ "&redirect_uri="
							+ servletContext.getAttribute("facebook.redirect")
							+ "&client_secret="
							+ servletContext.getAttribute("facebook.secret")
							+ "&code=" + code);
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
				
				Date expiresDate;
				if(expires != null) {
					expiresDate = new Date(new Date().getTime() + expires*1000);
				}
				else {
					expiresDate = new Date(187103883600000l); //5999-01-01 
				}
				
				request.getSession().setAttribute("accessToken", accessToken);
				request.getSession().setAttribute("accessExpires", expiresDate);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			return mapping.findForward("default");
		} else {
			ActionForward forward = mapping.findForward("facebook");
			String path = forward.getPath();
			if((path.indexOf("${airbnb.id}") > 0) || (path.indexOf("${airbnb.redirect}") > 0)) {
				//this is a hack because m2eclipse doesn't do filtering in wtp
				path = path.replaceAll("\\$\\{airbnb.id\\}", (String)servletContext.getAttribute("airbnb.id"));
				path = path.replaceAll("\\$\\{airbnb.secret\\}", (String)servletContext.getAttribute("airbnb.secret"));
				path = path.replaceAll("\\$\\{airbnb.redirect\\}", (String)servletContext.getAttribute("airbnb.redirect"));
				forward = new ActionForward(path, forward.getRedirect());
			}
			return forward;
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
