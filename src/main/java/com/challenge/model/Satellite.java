package com.challenge.model;

import java.util.List;

public class Satellite implements ISpaceship {

	private String name;
	private double[] position;
	private List<String> receivedMessage;
	private double distance;

	public Satellite(String name, double posX, double posY) {
		this.name = name;
		this.position = new double[] { posX, posY };
	}

	public Satellite(String name, double[] pos) {
		this.name = name;
		this.position = pos;
	}

	public String getName() {
		return this.name;
	}

	public double[] getPosition() {
		return this.position;
	}

	public List<String> getReceivedMessage() {
		return receivedMessage;
	}

	public void setReceivedMessage(List<String> receivedMessage) {
		this.receivedMessage = receivedMessage;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

}
