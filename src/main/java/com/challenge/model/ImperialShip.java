package com.challenge.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Imperial ship")
public class ImperialShip implements ISpaceship {

	@ApiModelProperty(value = "Imperial Ship assigned name", required = true)
	private String name;
	
	@ApiModelProperty(value = "Spatial coordinates [X,Y]", required = true)
	private double[] position;
	
	@ApiModelProperty(value = "Message emitted", required = false)
	private String message;

	public ImperialShip(String name, double posX, double posY) {
		this.name = name;
		this.position = new double[] { posX, posY };
	}
	
	public ImperialShip(String name, double[] pos) {
		this.name = name;
		this.position = pos;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double[] getPosition() {
		return position;
	}

	public void setPosition(double[] position) {
		this.position = position;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
