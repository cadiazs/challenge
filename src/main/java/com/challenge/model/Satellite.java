package com.challenge.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@ApiModel("Satellite")
public class Satellite implements ISpaceship {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "serial")
	@ApiModelProperty(value = "Satellite's id", required = false)
	private Integer id;
	
	@Column
	@ApiModelProperty(value = "Name", required = true)
	private String name;
	
	@Column
	@ApiModelProperty(value = "Spatial coordinates [X,Y]", required = true)
	private double[] position;
	
	@Column
	@ApiModelProperty(value = "Fragments of received message", required = false)
	private ArrayList<String> receivedMessage;
	
	@Column
	@ApiModelProperty(value = "Detected distance from message transmitter", required = false)
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
