package com.challenge.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.domain.Example;

import com.challenge.dao.EntryMessageDAO;
import com.challenge.model.DiscoveredMessage;
import com.challenge.model.EntryMessage;
import com.challenge.model.ImperialShip;
import com.challenge.model.Satellite;

@Service
public class Alliance {

	private Map<String, Satellite> satellites;

	private EntryMessageDAO entryMessageDAO;

	private DiscoveredMessage discoveredMessage;

	public Alliance(EntryMessageDAO entryMessageDAO) {
		this.entryMessageDAO = entryMessageDAO;
		this.discoveredMessage = null;
		this.satellites = this.getSatellites();
	}

	private Map<String, Satellite> getSatellites() {
		Map<String, Satellite> satellites = new HashMap<String, Satellite>();
		Satellite satellite;

		satellite = new Satellite("kenobi", new double[] { -500, -200 });
		satellites.put(satellite.getName(), satellite);
		satellite = new Satellite("skywalker", new double[] { 100, -100 });
		satellites.put(satellite.getName(), satellite);
		satellite = new Satellite("sato", new double[] { 500, 100 });
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

	public DiscoveredMessage analizeTopSecretMessage(Map<String, Double> distances,
			Map<String, List<String>> messages) {
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

	public void receiveMessage(String satellite, EntryMessage entryMessage) {
		EntryMessage validatorExample = new EntryMessage();
		Example<EntryMessage> example;
		Optional<EntryMessage> result;

		List<String> allowedSatellites = Arrays.asList("kenobi", "skywalker", "sato");

		if (allowedSatellites.indexOf(satellite) == -1) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not identified satellite");
		}

		validatorExample.setSatellite(satellite);
		validatorExample.setUsed(false);

		try {
			example = Example.of(validatorExample);
			result = entryMessageDAO.findOne(example);
		} catch (IncorrectResultSizeDataAccessException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to read old messages");
		}

		if (result.isPresent()) {
			entryMessage.setId(result.get().getId());
			entryMessage.setCreatedAt(result.get().getCreatedAt());
		}

		entryMessage.setSatellite(satellite);
		entryMessage.setUsed(false);

		try {
			entryMessageDAO.save(entryMessage);
		} catch (IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to storage the message");
		}
	}

	public boolean buildMessages() {
		ArrayList<EntryMessage> entryMessageList;
		try {
			entryMessageList = this.loadStoredMessages();
		} catch (Exception e) {
			return false;
		}

		Map<String, Double> distances = this.extractDistances(entryMessageList);
		Map<String, List<String>> messages = this.extractMessages(entryMessageList);

		try {
			this.GetLocation(distances);
			this.GetMessage(messages);
		} catch (Exception e) {
			return false;
		}

		this.discoveredMessage = new DiscoveredMessage(this.GetLocation(distances), this.GetMessage(messages));

		return true;
	}

	private ArrayList<EntryMessage> loadStoredMessages() throws Exception {
		ArrayList<EntryMessage> storedMessages = null;

		try {
			storedMessages = this.entryMessageDAO.findByUsed(false);
		} catch (Exception e) {
			throw new Exception("Unable to load received messages");
		}

		if (storedMessages.size() != 3) {
			throw new Exception("We do not have enough messages");
		}

		return storedMessages;
	}

	private Map<String, Double> extractDistances(ArrayList<EntryMessage> entryMessageList) {
		Map<String, Double> distances = new HashMap<String, Double>();

		for (int i = 0; i < entryMessageList.size(); i++) {
			distances.put(entryMessageList.get(i).getSatellite(), entryMessageList.get(i).getDistance());
		}

		return distances;
	}

	private Map<String, List<String>> extractMessages(ArrayList<EntryMessage> entryMessageList) {
		Map<String, List<String>> messages = new HashMap<String, List<String>>();

		for (int i = 0; i < entryMessageList.size(); i++) {
			messages.put(entryMessageList.get(i).getSatellite(), entryMessageList.get(i).getMessage());
		}

		return messages;
	}

	public DiscoveredMessage getDiscoveredMessage() {
		ArrayList<EntryMessage> storedMessages = null;

		try {
			storedMessages = this.entryMessageDAO.findByUsed(false);

			for (int i = 0; i < storedMessages.size(); i++) {
				storedMessages.get(i).setUsed(true);
				this.entryMessageDAO.save(storedMessages.get(i));
			}

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to use received messages");
		}

		return this.discoveredMessage;
	}

}
