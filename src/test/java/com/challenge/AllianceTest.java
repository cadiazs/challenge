package com.challenge;

import com.challenge.dao.EntryMessageDAO;
import com.challenge.service.Alliance;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class AllianceTest {

    @Test
    public void test_getMessage_messagesWithoutDelay_returnString() {
        Alliance alliance = new Alliance(any(EntryMessageDAO.class));

        Map<String, List<String>> messageSegments = new HashMap<String, List<String>>();
        messageSegments.put("kenobi", Arrays.asList("", "es", "", "mensaje", "")); // Delayed message
        messageSegments.put("skywalker", Arrays.asList("Este", "", "", "", "secreto")); // Delayed message
        messageSegments.put("sato", Arrays.asList("Este", "", "un", "", "secreto")); // Non delayed message

        Assert.assertEquals(alliance.GetMessage(messageSegments), "Este es un mensaje secreto");
    }

    @Test
    public void test_getMessage_messagesWithDelay_returnString() {
        Alliance alliance = new Alliance(any(EntryMessageDAO.class));

        Map<String, List<String>> messageSegments = new HashMap<String, List<String>>();
        messageSegments.put("kenobi", Arrays.asList("", "", "es", "", "mensaje", "")); // Delayed message
        messageSegments.put("skywalker", Arrays.asList("", "Este", "", "", "", "secreto")); // Delayed message
        messageSegments.put("sato", Arrays.asList("Este", "", "un", "", "secreto")); // Non delayed message

        Assert.assertEquals(alliance.GetMessage(messageSegments), "Este es un mensaje secreto");
    }
}
