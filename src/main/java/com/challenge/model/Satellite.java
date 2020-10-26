package com.challenge.model;

import java.util.List;

//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;

//@ApiModel("Model Satellite")
public class Satellite implements ISpaceship {

//	@ApiModelProperty(value = "Satelllite's name", required = true)
	private String name;
	
//	@ApiModelProperty(value = "Satelllite's position", required = false)
	private double[] position;
	
//	@ApiModelProperty(value = "Received message", required = true)
	private List<String> receivedMessage;
	
//	@ApiModelProperty(value = "Distance", required = true)
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
