package com.philihp.boatswag.action;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.philihp.boatswag.facebook.Credentials;
import com.philihp.boatswag.facebook.GroupMember;
import com.philihp.boatswag.facebook.GroupMembers;
import com.philihp.boatswag.facebook.GroupMembersDeserializer;
import com.philihp.boatswag.facebook.Groups;
import com.philihp.boatswag.facebook.GroupsDeserializer;
import com.philihp.boatswag.facebook.Location;
import com.philihp.boatswag.facebook.LocationDeserializer;
import com.philihp.boatswag.jpa.Connection;
import com.philihp.boatswag.jpa.EntityManagerManager;
import com.philihp.boatswag.jpa.User;

public class RefreshGroup extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Credentials credentials = (Credentials) request.getSession().getAttribute("credentials");
		String accessToken = (String) request.getSession().getAttribute("accessToken");
		String groupId = (String) getServlet().getServletContext().getAttribute("membership.group.id");

		if (credentials == null)
			return mapping.findForward("authenticate");

		URL url = new URL("https://graph.facebook.com/" + groupId + "/members" + "?access_token=" + accessToken);
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		if (conn.getResponseCode() == 400)
			return mapping.findForward("authenticate");
		InputStream inputStream = conn.getInputStream();
		Reader reader = new InputStreamReader(inputStream);

		Gson gson = new GsonBuilder().registerTypeAdapter(GroupMembers.class, new GroupMembersDeserializer()).create();
		GroupMembers groupMembers = gson.fromJson(reader, GroupMembers.class);

		for (GroupMember member : groupMembers.getGroupMembers()) {
			findConnection(groupId, member.getId());
			//the act of finding the connection will create it if it doesn't exist.
			//since we never "clear" the list, people will just be added. but we have a last-updated, kinda... so whatevs bro. good enough!
		}

		return mapping.findForward("default");
	}

}
