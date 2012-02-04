package com.philihp.boatswag.facebook;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class GroupMembersDeserializer implements
		JsonDeserializer<GroupMembers> {

	@Override
	public GroupMembers deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext ctx) throws JsonParseException {
		
		GroupMembers groupMembers = new GroupMembers();

		JsonObject obj = json.getAsJsonObject();
		JsonArray data = obj.get("data").getAsJsonArray();
		for (JsonElement dataElement : data) {
			GroupMember member = new GroupMember();
			JsonObject dataObj = dataElement.getAsJsonObject();
			member.setId(dataObj.get("id").getAsString());
			member.setName(dataObj.get("name").getAsString());
			groupMembers.getGroupMembers().add(member);
		}

		
		groupMembers.setNext(getIfExists(obj,"next"));		
		groupMembers.setPrevious(getIfExists(obj,"previous"));
		
		return groupMembers;
	}
	
	private String getIfExists(JsonObject obj, String pagingProperty) {
		JsonElement e = obj.get("paging");
		if(e == null) return null;
		e = e.getAsJsonObject().get(pagingProperty);
		if(e == null) return null;
		return e.getAsString();
		
	}
}
