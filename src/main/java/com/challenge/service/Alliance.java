package com.challenge.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.challenge.model.DiscoveredMessage;
import com.challenge.model.ImperialShip;
import com.challenge.model.Satellite;

public class Alliance {

	private Map<String, Satellite> satellites;

	public Alliance() {
		this.satellites = this.getSatellites();
	}

	private Map<String, Satellite> getSatellites() {
		Map<String, Satellite> satellites = new HashMap<String, Satellite>();
		Satellite satellite;

		satellite = new Satellite("kenobi", new double[] { 200, -200 });
		satellites.put(satellite.getName(), satellite);
		satellite = new Satellite("skywalker", new double[] { 200, -800 });
		satellites.put(satellite.getName(), satellite);
		satellite = new Satellite("sato", new double[] { 800, -800 });
		satellites.put(satellite.getName(), satellite);

		return satellites;
	}

	public double[] GetLocation(Map<String, Double> distances) {
		Interceptor interceptor = new Interceptor();
		ImperialShip imperialShip;
		
		this.setSatDistances(distances);

		if (!NavChart.evaluateDistances(this.satellites)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Distances are not related");
		}

		imperialShip = interceptor.findImperialShip(this.satellites);
		return imperialShip.getPosition();
	}

	public String GetMessage(Map<String, List<String>> messages) {
		Interceptor interceptor = new Interceptor();
		
		this.setSatMessages(messages);

		return interceptor.discoverMessage(this.satellites);

	}

	private void setSatDistances(Map<String, Double> distances) {
		this.satellites.get("kenobi").setDistance(distances.get("kenobi"));
		this.satellites.get("skywalker").setDistance(distances.get("skywalker"));
		this.satellites.get("sato").setDistance(distances.get("sato"));
	}

	private void setSatMessages(Map<String, List<String>> messages) {
		this.satellites.get("kenobi").setReceivedMessage(messages.get("kenobi"));
		this.satellites.get("skywalker").setReceivedMessage(messages.get("skywalker"));
		this.satellites.get("sato").setReceivedMessage(messages.get("sato"));
	}

	public DiscoveredMessage analizeTopSecretMessage(JSONObject topSecretMessage) {
		JSONArray array = topSecretMessage.getJSONArray("satellites");

		Map<String, Double> distances = new HashMap<String, Double>();
		Map<String, List<String>> messages = new HashMap<String, List<String>>();

		for (int i = 0; i < array.length(); i++) {
			distances.put(array.getJSONObject(i).getString("name"), array.getJSONObject(i).getDouble("distance"));
			messages.put(array.getJSONObject(i).getString("name"),
					this.jsonArrayToList(array.getJSONObject(i).getJSONArray("message")));
		}

		// Point #1 of Challenge
		return new DiscoveredMessage(this.GetLocation(distances), this.GetMessage(messages));
	}

	protected List<String> jsonArrayToList(JSONArray jsonArray) {
		List<String> stringList = new ArrayList<>();
		for (int i = 0; i < jsonArray.length(); i++) {
			stringList.add(jsonArray.getString(i));
		}
		return stringList;
	}

}
