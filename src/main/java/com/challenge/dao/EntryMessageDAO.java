package com.challenge.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.challenge.model.EntryMessage;

public interface EntryMessageDAO extends JpaRepository<EntryMessage, Integer> {
	List<EntryMessage> findBySatelliteAndUsed(String satellite, boolean used);
	
//	@Query("select m from entry_message u where u.used = 0")
//	List<EntryMessage> findUnusedMessages();
	
	ArrayList<EntryMessage> findByUsed(boolean used);
}
