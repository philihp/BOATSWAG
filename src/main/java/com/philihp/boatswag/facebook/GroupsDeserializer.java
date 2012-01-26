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

public class GroupsDeserializer implements
		JsonDeserializer<Groups> {

	@Override
	public Groups deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext ctx) throws JsonParseException {
		Groups groups = new Groups();

		JsonObject obj = json.getAsJsonObject();
		JsonArray data = obj.get("data").getAsJsonArray();
		for (JsonElement dataElement : data) {
			JsonObject dataObj = dataElement.getAsJsonObject();
			Group group = new Group();
			for (Map.Entry<String, JsonElement> entry : dataObj.entrySet()) {
				if ("version".equals(entry.getKey()))
					group.setVersion(entry.getValue().getAsInt());
				else if ("name".equals(entry.getKey()))
					group.setName(entry.getValue().getAsString());
				else if ("id".equals(entry.getKey()))
					group.setId(entry.getValue().getAsString());
				else if ("unread".equals(entry.getKey()))
					group.setUnread(entry.getValue().getAsInt());
				else if ("bookmark_order".equals(entry.getKey()))
					group.setBookmarkOrder(entry.getValue().getAsInt());
			}
			groups.getGroups().add(group);
		}

		return groups;
	}
}
