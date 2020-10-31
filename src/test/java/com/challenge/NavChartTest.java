package com.challenge;

import com.challenge.model.Satellite;
import com.challenge.service.NavChart;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class NavChartTest {

    private Satellite satA;
    private Satellite satB;
    private Satellite satC;
    private Map<String, Satellite> satellites;

    void setupSatellites() {
        this.satA = new Satellite("kenobi", new double[]{-500, -200});
        this.satB = new Satellite("skywalker", new double[]{100, -100});
        this.satC = new Satellite("sato", new double[]{500, 100});
    }

    @Test
    public void test_evaluateDistances_validDistances_returnTrue() {
        setupSatellites();
        this.satA.setDistance(538.51648071345);
        this.satB.setDistance(565.685424949238);
        this.satC.setDistance(824.621125123532);

        satellites = getSatelliteMap();

        assertTrue(NavChart.evaluateDistances(satellites));
    }

    @Test
    public void test_evaluateDistances_invalidDistances_returnFalse() {
        setupSatellites();
        satA.setDistance(538.51648071345);
        satB.setDistance(100); // Non related distance
        satC.setDistance(824.621125123532);

        satellites = getSatelliteMap();

        assertFalse(NavChart.evaluateDistances(satellites));
    }

    private Map<String, Satellite> getSatelliteMap() {
        Map<String, Satellite> satellites = new HashMap<String, Satellite>();
        satellites.put("kenobi", this.satA);
        satellites.put("skywalker", this.satB);
        satellites.put("sato", this.satC);
        return satellites;
    }
}
