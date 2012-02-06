package com.philihp.boatswag.action;

import java.io.InputStream;

import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.persistence.EntityManager;
import javax.persistence.Query;
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

public class RefreshMyFriendData extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Credentials credentials = (Credentials) request.getSession().getAttribute("facebook");
		if (credentials == null) {
			return mapping.findForward("authenticate");
		}

		EntityManager em = EntityManagerManager.get();
		TypedQuery<Connection> query = em
				.createQuery(
						"SELECT a "
								+ "FROM Connection a, Connection b WHERE a.facebookSubjectId = b.facebookSubjectId AND a.facebookPredicateId = :facebookId AND b.facebookPredicateId = :groupId",
						Connection.class);
		query.setParameter("facebookId", credentials.getId());
		query.setParameter("groupId", getServlet().getServletContext().getAttribute("membership.group.id"));
		List<Connection> connections = query.getResultList();

		for (Connection c : connections) {
			System.out.println(c.getFacebookSubjectId());
		}

		return mapping.findForward("done");
	}

}
