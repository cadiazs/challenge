package com.challenge.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Received split message")
@Entity
public class EntryMessage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "serial")
	@ApiModelProperty(value = "Entry message id", required = false)
	private Integer id;

	@Column
	@ApiModelProperty(value = "Satellite's name which received the message", required = true)
	private String satellite;

	@Column
	@ApiModelProperty(value = "Distance from Satellite to message transmitter", required = true)
	private Double distance;

	@Column
	@ApiModelProperty(value = "Message received", required = true)
	private ArrayList<String> message;
	
	@Column(columnDefinition="tinyint(1) default 0")
	@ApiModelProperty(value = "Determines if its entry message was used or not", required = true)
	private Boolean used = false;
	
	@CreationTimestamp
	@ApiModelProperty(value = "Entry Message creation time", required = true)
	private Date createdAt;
	
	@UpdateTimestamp
	@ApiModelProperty(value = "Entry Message last update time", required = false)
	private Date updatedAt;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSatellite() {
		return satellite;
	}

	public void setSatellite(String satellite) {
		this.satellite = satellite;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public ArrayList<String> getMessage() {
		return message;
	}

	public void setMessage(ArrayList<String> message) {
		this.message = message;
	}

	public Boolean getUsed() {
		return used;
	}

	public void setUsed(Boolean used) {
		this.used = used;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
}
