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
		
		if(request.getSession().getAttribute("credentials") == null) {
			return mapping.findForward("authenticate");
		}
		else {
			return mapping.findForward("default");
		}

	}

}
