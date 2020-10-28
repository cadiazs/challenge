package com.challenge.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.swagger.annotations.ApiModel;

@Entity
@ApiModel("Satellite")
public class Satellite implements ISpaceship {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "serial")
	private Integer id;
	
	@Column
	private String name;
	
	@Column
	private double[] position;
	
	@Column
	private ArrayList<String> receivedMessage;
	
	@Column
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
		this.receivedMessage = new ArrayList<String>(receivedMessage);
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

}
