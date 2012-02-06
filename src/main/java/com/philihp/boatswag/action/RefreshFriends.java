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
import com.philihp.boatswag.facebook.FBUser;
import com.philihp.boatswag.facebook.GroupMember;
import com.philihp.boatswag.facebook.GroupMembers;
import com.philihp.boatswag.facebook.GroupMembersDeserializer;
import com.philihp.boatswag.facebook.Groups;
import com.philihp.boatswag.facebook.GroupsDeserializer;
import com.philihp.boatswag.facebook.FBLocation;
import com.philihp.boatswag.facebook.FBLocationDeserializer;
import com.philihp.boatswag.jpa.Connection;
import com.philihp.boatswag.jpa.EntityManagerManager;
import com.philihp.boatswag.jpa.User;

public class RefreshFriends extends BaseAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {

			FBUser credentials = (FBUser) request.getSession().getAttribute("credentials");
			String accessToken = (String) request.getSession().getAttribute("accessToken");
			String groupId = (String) getServlet().getServletContext().getAttribute("membership.group.id");
			List<Connection> groupConnections = findConnectionsBySubjectId(groupId);

			if (credentials == null)
				throw new AuthenticationException();

			fetchFriends(credentials.getId(), accessToken, groupConnections);

			System.out.println("Done refershing");

			return mapping.findForward("default");
		} catch (AuthenticationException e) {
			return mapping.findForward("authenticate");
		}

	}

	private List<User> fetchFriends(String facebookId, String accessToken, List<Connection> groupList)
			throws AuthenticationException, Exception {
		List<User> users = new LinkedList<User>();

		System.out.println("Fetching friends...");
		URL url = new URL("https://graph.facebook.com/" + facebookId + "/friends" + "?access_token=" + accessToken);
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		if (conn.getResponseCode() == 400)
			throw new AuthenticationException();
		InputStream inputStream = conn.getInputStream();
		Reader reader = new InputStreamReader(inputStream);

		Gson gson = new GsonBuilder().registerTypeAdapter(GroupMembers.class, new GroupMembersDeserializer()).create();
		GroupMembers groupMembers = gson.fromJson(reader, GroupMembers.class);

		System.out.println("Fetched! number: "+ groupMembers.getGroupMembers().size());
		for (GroupMember member : groupMembers.getGroupMembers()) {
			String memberFacebookId = member.getId();

			for (Connection connection : groupList) {
				if (memberFacebookId != null && memberFacebookId.equals(connection.getFacebookPredicateId())) {
					System.out.println("Fetching " + memberFacebookId);
					User user = fetchUser(accessToken, memberFacebookId);
					if (user.getLocationId() != null) {
						FBLocation location = fetchLocation(accessToken, user.getLocationId());
						user.setLatitude(location.getLatitude());
						user.setLongitude(location.getLongitude());
					}
					users.add(user);
					break;
				}
			}
		}

		return users;
	}

}
