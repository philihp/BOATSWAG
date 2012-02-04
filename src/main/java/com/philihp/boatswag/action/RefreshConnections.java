package com.philihp.boatswag.action;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Date;
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

public class RefreshConnections extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Date expires = (Date) request.getSession().getAttribute("accessExpires");
		
		String subjectId = request.getParameter("subjectId");
		if(subjectId == null) subjectId = (String)request.getAttribute("subjectId");
		String type = request.getParameter("type");
		if(type == null) type = (String)request.getAttribute("type");

		if (expires == null || expires.before(new Date())) {
			return mapping.findForward("authenticate");
		} else {
			String accessToken = (String) request.getSession().getAttribute("accessToken");
			URL url = new URL("https://graph.facebook.com/" + subjectId + "/" + type + "?access_token=" + accessToken);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			if (conn.getResponseCode() == 400) {
				return mapping.findForward("authenticate");
			} else {
				InputStream inputStream = conn.getInputStream();
				Reader reader = new InputStreamReader(inputStream);

				Gson gson = new GsonBuilder().registerTypeAdapter(GroupMembers.class, new GroupMembersDeserializer()).create();
				GroupMembers groupMembers = gson.fromJson(reader, GroupMembers.class);

				//this part isn't too necessary, i wrote it before i realized facebook will give you the first 5000 people
				String nextURL = groupMembers.getNext();
				while(nextURL != null) {
					url = new URL(nextURL);
					conn = (HttpsURLConnection)url.openConnection();
					if(conn.getResponseCode() == 400) {
						return mapping.findForward("authenticate");
					} else {
						inputStream = conn.getInputStream();
						reader = new InputStreamReader(inputStream);

						GroupMembers moreGroupMembers = gson.fromJson(reader, GroupMembers.class);
						groupMembers.getGroupMembers().addAll(moreGroupMembers.getGroupMembers());
						nextURL = moreGroupMembers.getNext();
					}				
				}

				
				EntityManager em = EntityManagerManager.get();
				Connection connection = null;
				for(GroupMember member : groupMembers.getGroupMembers()) {
					TypedQuery<Connection> query = em.createNamedQuery("findGroupMembershipConnection", Connection.class);
					query.setParameter("facebookSubjectId", member.getId());
					query.setParameter("facebookPredicateId", subjectId);
					List<Connection> results = query.getResultList();
					
					if(results.size() == 0) connection = new Connection();
					else connection = results.get(0);
					
					connection.setFacebookSubjectId(member.getId());
					connection.setFacebookPredicateId(subjectId);
					connection.setDateUpdated(new Date());
					
					em.persist(connection);
				}

				return mapping.findForward("default");
			}
		}
	}

}
