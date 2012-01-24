package com.philihp.boatswag.util;

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

public class FacebookGroupsDeserializer implements JsonDeserializer<FacebookGroups> {

	@Override
	public FacebookGroups deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext ctx)
			throws JsonParseException {
		FacebookGroups groups = new FacebookGroups();

		JsonObject obj = json.getAsJsonObject();
		JsonArray data = obj.get("data").getAsJsonArray();
		for (JsonElement dataElement : data) {
			JsonObject dataObj = dataElement.getAsJsonObject();
			FacebookGroup group = new FacebookGroup();
			for (Map.Entry<String, JsonElement> entry : dataObj.entrySet()) {
				switch (entry.getKey()) {
				case "version":
					group.setVersion(entry.getValue().getAsInt());
					break;
				case "name":
					group.setName(entry.getValue().getAsString());
					break;
				case "id":
					group.setId(entry.getValue().getAsString());
					break;
				case "unread":
					group.setUnread(entry.getValue().getAsInt());
					break;
				case "bookmark_order":
					group.setBookmarkOrder(entry.getValue().getAsInt());
					break;
				}
			}
			groups.getGroups().add(group);
		}

		return groups;
	}
}
