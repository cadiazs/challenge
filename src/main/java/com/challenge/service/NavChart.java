package com.challenge.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.challenge.model.ISpaceship;
import com.challenge.model.ImperialShip;
import com.challenge.model.Satellite;

public class NavChart {

	public static final double EPSILON = 0.001;

	public static double distanceSpaceships(ISpaceship shipA, ISpaceship shipB) {

		return NavChart.round(Math.hypot(Math.abs(shipA.getPosition()[0] - shipB.getPosition()[0]),
				Math.abs(shipA.getPosition()[1] - shipB.getPosition()[1])));

	}

	/**
	 * Based on http://paulbourke.net/geometry/circlesphere/ "Intersection of two
	 * circles"
	 */
	public static double[] intersectTwoMessages(Satellite satA, Satellite satB) {
		double distanceSatASatB = NavChart.distanceSpaceships(satA, satB);
		double a = NavChart.calcA(satA, satB, distanceSatASatB);

		double[] p2 = NavChart.calcP2(satA, satB, distanceSatASatB, a);

		double[] p3 = NavChart.calcP3(satA, satB, distanceSatASatB, a, p2);

		return p3;
	}

	private static double[] calcP3(Satellite satA, Satellite satB, double d, double a, double[] p2) {
		double p0x = satA.getPosition()[0];
		double p0y = satA.getPosition()[1];

		double p1x = satB.getPosition()[0];
		double p1y = satB.getPosition()[1];

		double r0 = satA.getDistance();

		double h = Math.sqrt((r0 * r0) - (a * a));

		// Option A
		double p3Ax = NavChart.round(p2[0] + (h * (p1y - p0y) / d));
		double p3Ay = NavChart.round(p2[1] + (h * (p1x - p0x) / d));

		// Option B
		double p3Bx = NavChart.round(p2[0] - (h * (p1y - p0y) / d));
		double p3By = NavChart.round(p2[1] - (h * (p1x - p0x) / d));

		return new double[] { p3Ax, p3Ay, p3Bx, p3By };
	}

	private static double[] calcP2(Satellite satA, Satellite satB, double d, double a) {
		double p0x = satA.getPosition()[0];
		double p0y = satA.getPosition()[1];

		double p1x = satB.getPosition()[0];
		double p1y = satB.getPosition()[1];

		double p2x = p0x + (a * (p1x - p0x) / d);
		double p2y = p0y + (a * (p1y - p0y) / d);

		return new double[] { p2x, p2y };
	}

	protected static double calcA(Satellite satA, Satellite satB, double distanceSatASatB) {
		double r0 = satA.getDistance();
		double r1 = satB.getDistance();

		double a = ((r0 * r0) - (r1 * r1) + (distanceSatASatB * distanceSatASatB)) / (2.0 * distanceSatASatB);

		return a;
	}

	public static ImperialShip evaluateP3(double[] p3, Satellite satC) {
		// Option A
		ImperialShip optA = new ImperialShip("Cargo Ship [Option A]", p3[0], p3[1]);

		// Option B
		ImperialShip optB = new ImperialShip("Cargo Ship [Option B]", p3[2], p3[3]);

		// Option C
		ImperialShip optC = new ImperialShip("Cargo Ship [Option C]", p3[2], p3[1]);

		// Option D
		ImperialShip optD = new ImperialShip("Cargo Ship [Option D]", p3[0], p3[3]);

		double diffA = Math.abs(NavChart.distanceSpaceships(satC, optA) - satC.getDistance());
		double diffB = Math.abs(NavChart.distanceSpaceships(satC, optB) - satC.getDistance());
		double diffC = Math.abs(NavChart.distanceSpaceships(satC, optC) - satC.getDistance());
		double diffD = Math.abs(NavChart.distanceSpaceships(satC, optD) - satC.getDistance());

		if (diffA <= diffB && diffA <= diffC && diffA <= diffD && diffA < NavChart.EPSILON) {
			return optA;
		} else if (diffB <= diffC && diffB <= diffD && diffB < NavChart.EPSILON) {
			return optB;
		} else if (diffC <= diffD && diffC < NavChart.EPSILON) {
			return optC;
		} else if (diffD < NavChart.EPSILON) {
			return optD;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to determinate Imperial Ship position");
//			throw new Exception("Unable to determinate Imperial Ship Position");
//			System.out.println("Se salio de NavChart.evaluateP3");
//			// TODO: Launch exception
//			return null;
		}
	}

	public static double round(double number) {
		return (double) Math.round(number * 1000000d) / 1000000d;
	}
}
