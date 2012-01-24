package com.philihp.boatswag.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.philihp.boatswag.util.FacebookGroup;

public class ShowBoard extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		boolean isInSurvivors = false;
		@SuppressWarnings("unchecked")
		List<FacebookGroup> groups = (List<FacebookGroup>)request.getSession().getAttribute("groups");
		for(FacebookGroup group : groups) {
			if("298673206843160".equals(group.getId())) {
				isInSurvivors = true;
			}
		}
		
		request.setAttribute("isInSurvivors", isInSurvivors);
		
		return mapping.findForward("default");
	}

}
