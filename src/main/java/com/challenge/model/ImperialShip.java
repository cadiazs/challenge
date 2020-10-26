package com.challenge.model;

public class ImperialShip implements ISpaceship {

	private String name;
	private double[] position;
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
