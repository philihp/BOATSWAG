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

public class AuthenticateLogout extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		request.getSession().setAttribute("facebook", null);
		request.getSession().setAttribute("groups", null);
		request.getSession().setAttribute("accessToken", null);
		request.getSession().setAttribute("accessExpires", null);

		return mapping.findForward("default");
	}

}
