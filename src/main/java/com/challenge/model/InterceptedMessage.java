package com.challenge.model;

import java.util.Map;

public class InterceptedMessage {

	public Map<String, Double> position;

	public String message;

	public InterceptedMessage(double[] position, String message) {
		this.message = message;
		this.position = Map.ofEntries(Map.entry("x", position[0]), Map.entry("y", position[1]));
	}
}
