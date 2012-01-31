package com.philihp.boatswag.facebook;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class LocationDeserializer implements JsonDeserializer<Location> {

	@Override
	public Location deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext ctx) throws JsonParseException {
		Location location = new Location();
		JsonObject obj = json.getAsJsonObject();
		for (Map.Entry<String, JsonElement> entry : obj.entrySet()) {
			if ("id".equals(entry.getKey()))
				location.setId(entry.getValue().getAsString());
			else if ("name".equals(entry.getKey()))
				location.setName(entry.getValue().getAsString());
			else if ("link".equals(entry.getKey()))
				location.setLink(entry.getValue().getAsString());
			else if ("location".equals(entry.getKey())) {
				JsonObject coordinates = entry.getValue().getAsJsonObject();
				location.setLatitude(coordinates.get("latitude").getAsDouble());
				location.setLongitude(coordinates.get("longitude").getAsDouble());
			}
		}
		return location;
	}
}
