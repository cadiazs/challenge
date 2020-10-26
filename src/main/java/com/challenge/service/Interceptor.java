package com.challenge.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.challenge.model.ImperialShip;
import com.challenge.model.Satellite;

public class Interceptor {

	public boolean evaluateDistances(Map<String, Satellite> satelites) {
		return this.checkDistances(satelites.get("kenobi"), satelites.get("skywalker"))
				&& this.checkDistances(satelites.get("kenobi"), satelites.get("sato"))
				&& this.checkDistances(satelites.get("sato"), satelites.get("skywalker"));
	}

	private boolean checkDistances(Satellite satA, Satellite satB) {
		boolean result = true;
		double distance = NavChart.distanceSpaceships(satA, satB);

		// Not in the same center?
		result &= (distance > 0);

		// Are not too far?
		result &= (NavChart.round(satA.getDistance() + satB.getDistance()) > distance);

		// Intersection exists?
		result &= (Math.abs(NavChart.round(satA.getDistance() - satB.getDistance())) <= distance);

		return result;
	}

	public ImperialShip findImperialShip(Map<String, Satellite> satelites) {
		double[] p3 = NavChart.intersectTwoMessages(satelites.get("sato"), satelites.get("kenobi"));
		ImperialShip imperialShip = NavChart.evaluateP3(p3, satelites.get("skywalker"));
		return imperialShip;
	}

	public String discoverMessage(Map<String, Satellite> satellites) {
//		for (Map.Entry<String, Satellite> entry : satellites.entrySet()) {
//			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
//		}
		String msg = "";

		List<String> msgKenobi = satellites.get("kenobi").getReceivedMessage();
		List<String> msgSkywalker = satellites.get("skywalker").getReceivedMessage();
		List<String> msgSato = satellites.get("sato").getReceivedMessage();

		int minLen = Math.min(msgKenobi.size(), msgSkywalker.size());
		minLen = Math.min(minLen, msgSato.size());

		msgKenobi = this.deleteDelay(msgKenobi, minLen);
		msgSkywalker = this.deleteDelay(msgSkywalker, minLen);
		msgSato = this.deleteDelay(msgSato, minLen);

		for (int i = 0; i < minLen; i++) {
			if (!msgKenobi.get(i).isEmpty()) {
				msg += msgKenobi.get(i);
			} else if (!msgSkywalker.get(i).isEmpty()) {
				msg += msgSkywalker.get(i);
			} else if (!msgSato.get(i).isEmpty()) {
				msg += msgSato.get(i);
			} else {
				// TODO: Launch exception
				System.out.println("No se puede determinar el mensaje");
			}

			if (i < (minLen - 1)) {
				msg += " ";
			}
		}

		return msg;
	}

	private List<String> deleteDelay(List<String> msgToCheck, int minLen) {
		List<String> msg = new ArrayList<>();

		if (msgToCheck.size() > minLen) {
			for (int i = 1; i < msgToCheck.size(); i++) {
				msg.add(msgToCheck.get(i));
			}

		} else {
			msg = msgToCheck;
		}
		return msg;
	}

}
