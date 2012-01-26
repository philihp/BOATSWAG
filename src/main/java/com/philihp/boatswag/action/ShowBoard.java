package com.philihp.boatswag.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.philihp.boatswag.facebook.Group;

public class ShowBoard extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		boolean isInSurvivors = false;
		@SuppressWarnings("unchecked")
		List<Group> groups = (List<Group>) request.getSession()
				.getAttribute("groups");

		if (groups == null) {
			return mapping.findForward("login");
		}
		for (Group group : groups) {
			if ("298673206843160".equals(group.getId())) {
				isInSurvivors = true;
			}
		}

		if (isInSurvivors) {
			return mapping.findForward("survivorYes");
		} else {
			return mapping.findForward("survivorNo");
		}

	}

}
