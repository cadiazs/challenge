package com.challenge.model;

import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Discovered message")
public class DiscoveredMessage {

	@ApiModelProperty(value = "Imperial Ship Position", required = false)
	private Map<String, Double> position;

	@ApiModelProperty(value = "Intercepted message", required = false)
	private String message;

	public DiscoveredMessage() {
		this.message = null;
		this.position = null;
	}
	
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
