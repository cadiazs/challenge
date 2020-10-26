package com.challenge.controller;

import org.json.JSONObject;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.challenge.model.DiscoveredMessage;
import com.challenge.service.Alliance;

@org.springframework.web.bind.annotation.RestController
public class RestController {

	@PostMapping("/topsecret")
	public DiscoveredMessage locateShip(@RequestBody String topSecretMessageJson) {
		
		JSONObject topSecretMessage = new JSONObject(topSecretMessageJson);
		Alliance alliance = new Alliance();
		return alliance.analizeTopSecretMessage(topSecretMessage);
	}
}
