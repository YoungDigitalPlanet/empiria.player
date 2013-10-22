package eu.ydp.empiria.player.client.module.connection;

import eu.ydp.empiria.player.client.util.position.Point;

public class DistanceCalculator {

	public double calculateDistanceBetween(LineSegment lineSegment, Point point) {
		// Adjust vectors relative to x1,y1
		// x2,y2 becomes relative vector from x1,y1 to end of segment
		int x2 = lineSegment.getPointEnd().getX() - lineSegment.getPointStart().getX();
		int y2 = lineSegment.getPointEnd().getY() - lineSegment.getPointStart().getY();
		// px,py becomes relative vector from x1,y1 to test point
		int px = point.getX() - lineSegment.getPointStart().getX();
		int py = point.getY() - lineSegment.getPointStart().getY();
		double dotprod = px * x2 + py * y2;
		// dotprod is the length of the px,py vector
		// projected on the x1,y1=>x2,y2 vector times the
		// length of the x1,y1=>x2,y2 vector
		double projlenSq = dotprod * dotprod / (x2 * x2 + y2 * y2);
		// Distance to line is now the length of the relative point
		// vector minus the length of its projection onto the line
		double lenSq = px * px + py * py - projlenSq;
		if (lenSq < 0) {
			lenSq = 0;
		}

		return Math.sqrt(lenSq);
	}
}
