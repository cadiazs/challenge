package com.challenge.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;

import com.challenge.model.InterceptedMessage;
import com.challenge.service.Alliance;

@org.springframework.web.bind.annotation.RestController
public class RestController {

	@GetMapping("/topsecret")
	public InterceptedMessage locateShip() {
		Map<String, double[]> satPositions = new HashMap<String, double[]>();
		Map<String, Double> distances = new HashMap<String, Double>();
		Map<String, List<String>> messages = new HashMap<String, List<String>>();

		// Assigning satellites positions
		satPositions.put("kenobi", new double[] { 200, -200 });
		satPositions.put("skywalker", new double[] { 200, -800 });
		satPositions.put("sato", new double[] { 800, -800 });

		// Assigning distances
		distances.put("kenobi", 797.947251916682);
		distances.put("skywalker", 580.147972194495);
		distances.put("sato", 1179.0554141521);

		// Assigning messages
		messages.put("kenobi", Arrays.asList("", "este", "", "un", "mensaje"));
		messages.put("skywalker", Arrays.asList("este", "", "un", "mensaje"));
		messages.put("sato", Arrays.asList("", "", "es", "un", "mensaje"));

		Alliance alliance = new Alliance(satPositions);

//		alliance.GetLocation(distances);
//
//		alliance.GetMessage(messages);

		return new InterceptedMessage(alliance.GetLocation(distances), alliance.GetMessage(messages));
	}
}
