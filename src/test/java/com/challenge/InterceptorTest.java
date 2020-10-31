package com.challenge;

import com.challenge.service.Interceptor;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class InterceptorTest {

    @Test
    public void test_deleteDelay_withoutDelay_returnListString() {
        Interceptor interceptor = new Interceptor();
        int messageLen = 5;

        List<String> msg = Arrays.asList("", "es", "", "mensaje", "secreto"); // Message without delay, the same after processing

        Assert.assertEquals(interceptor.deleteDelay(msg, messageLen), msg);
    }

    @Test
    public void test_deleteDelay_withDelay_returnListString() {
        Interceptor interceptor = new Interceptor();
        int messageLen = 5;

        List<String> msg = Arrays.asList("", "", "es", "", "mensaje", "secreto"); // Message with delay
        List<String> msgExpectedResult = Arrays.asList("", "es", "", "mensaje", "secreto"); // Expected result

        Assert.assertEquals(interceptor.deleteDelay(msg, messageLen), msgExpectedResult);
    }
}
