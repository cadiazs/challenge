package com.challenge.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.challenge.dao.EntryMessageDAO;
import com.challenge.model.DiscoveredMessage;
import com.challenge.model.EntryMessage;
import com.challenge.service.Alliance;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@org.springframework.web.bind.annotation.RestController
@CrossOrigin
@Api(value = "Alliance Intelligent Agency", description = "This API provides tools for message interception and its emitter localization.")
public class RestController {

	@Autowired
	private EntryMessageDAO entryMessageDAO;
	
	@GetMapping("/")
	@ApiOperation(value = "Health check API")
	public String healthCheck() {
		return "OK";
	}

	@PostMapping("/topsecret")
	@ApiOperation(value = "Based on 3 message fragments, determines original message and its emitter localization.", notes = "Return original message and emitter X,Y coords" )
	public DiscoveredMessage locateShip(@RequestBody String topSecretMessageJson) {
		JSONObject topSecretMessages = new JSONObject(topSecretMessageJson);
		Alliance alliance = new Alliance(this.entryMessageDAO);
		return alliance.analizeTopSecretMessage(topSecretMessages);
	}

	@PostMapping("/topsecret_split/{satellite}")
	@ApiOperation(value = "Receives a message fragment from a satellite")
	public void stageMessage(@RequestBody EntryMessage entryMessage, @PathVariable("satellite") String satellite) {
		Alliance alliance = new Alliance(this.entryMessageDAO);
		alliance.receiveMessage( satellite, entryMessage);
	}

	@GetMapping("/topsecret_split")
	@ApiOperation(value = "Retrieve original message and its emitter localization if 3 valid message fragments were provided through /topsecret split/{satellite}")
	public DiscoveredMessage joinMessages() {
		Alliance alliance = new Alliance(this.entryMessageDAO);
		
		if (alliance.buildMessages()) {
			return alliance.getDiscoveredMessage();
		} else {
			DiscoveredMessage notEnoughInfo = new DiscoveredMessage();
			notEnoughInfo.setMessage("We do not have enough info for Imperial Ship localization");
			return notEnoughInfo;
		}

	}

}
