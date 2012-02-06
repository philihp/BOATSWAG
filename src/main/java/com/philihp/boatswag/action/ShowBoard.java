package com.philihp.boatswag.action;

import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.philihp.boatswag.facebook.FBUser;
import com.philihp.boatswag.facebook.Group;
import com.philihp.boatswag.jpa.User;

public class ShowBoard extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		FBUser credentials = (FBUser)request.getSession().getAttribute("credentials");
		String groupId = (String) getServlet().getServletContext().getAttribute("membership.group.id");
		
		try {
			if (credentials == null)
				throw new AuthenticationException();
			User me = findUserByFacebookId(credentials.getId());
			request.setAttribute("me", me);
			
			List<User> users = findUsersWithLocations(groupId);
			request.setAttribute("users", users);
			
			return mapping.findForward("default");
		} catch (AuthenticationException e) {
			return mapping.findForward("authenticate");
		}

	}

}
