package com.challenge.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.challenge.model.ImperialShip;
import com.challenge.model.Satellite;

public class Interceptor {

	public ImperialShip findImperialShip(Map<String, Satellite> satelites) {
		double[] p3 = NavChart.intersectTwoSignals(satelites.get("sato"), satelites.get("kenobi"));
		ImperialShip imperialShip = NavChart.evaluateP3(p3, satelites.get("skywalker"));
		return imperialShip;
	}

	public String discoverMessage(Map<String, Satellite> satellites) {
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
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to determinate Imperial Ship message");
			}

			if (i < (minLen - 1)) {
				msg += " ";
			}
		}

		return msg;
	}

	public List<String> deleteDelay(List<String> msgToCheck, int minLen) {
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
