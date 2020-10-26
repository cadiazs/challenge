package com.challenge.model;

import java.util.Map;

public class DiscoveredMessage {

	public Map<String, Double> position;

	public String message;

	public DiscoveredMessage(double[] position, String message) {
		this.message = message;
		this.position = Map.ofEntries(Map.entry("x", position[0]), Map.entry("y", position[1]));
	}

	public Map<String, Double> getPosition() {
		return position;
	}

	public void setPosition(Map<String, Double> position) {
		this.position = position;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
