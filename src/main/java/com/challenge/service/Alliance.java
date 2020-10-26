package com.challenge.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.challenge.model.ImperialShip;
import com.challenge.model.Satellite;

public class Alliance {

	private Map<String, Satellite> satelites;

	public Alliance(Map<String, double[]> satPositions) {
		this.createSatellites(satPositions);
	}

	public double[] GetLocation(Map<String, Double> distances) {

		this.setSatDistances(distances);

		Interceptor interceptor = new Interceptor();

		if (!interceptor.evaluateDistances(this.satelites)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Distances are not related");
//			System.out.println("Se salio de Alliance.getResponse");
//			return null;
		}

		ImperialShip imperialShip = interceptor.findImperialShip(this.satelites);

		return imperialShip.getPosition();
	}

	public String GetMessage(Map<String, List<String>> messages) {
		this.setSatMessages(messages);

		Interceptor interceptor = new Interceptor();
		
		return interceptor.discoverMessage(this.satelites);

	}

	private void setSatDistances(Map<String, Double> distances) {
		this.satelites.get("kenobi").setDistance(distances.get("kenobi"));
		this.satelites.get("skywalker").setDistance(distances.get("skywalker"));
		this.satelites.get("sato").setDistance(distances.get("sato"));
	}

	private void createSatellites(Map<String, double[]> satPositions) {
		this.satelites = new HashMap<String, Satellite>();

		this.satelites.put("kenobi",
				new Satellite("kenobi", satPositions.get("kenobi")[0], satPositions.get("kenobi")[1]));
		this.satelites.put("skywalker",
				new Satellite("skywalker", satPositions.get("skywalker")[0], satPositions.get("skywalker")[1]));
		this.satelites.put("sato", new Satellite("sato", satPositions.get("sato")[0], satPositions.get("sato")[1]));
	}

	private void setSatMessages(Map<String, List<String>> messages) {
		this.satelites.get("kenobi").setReceivedMessage(messages.get("kenobi"));
		this.satelites.get("skywalker").setReceivedMessage(messages.get("skywalker"));
		this.satelites.get("sato").setReceivedMessage(messages.get("sato"));
	}

}
