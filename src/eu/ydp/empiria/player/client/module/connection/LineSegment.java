package eu.ydp.empiria.player.client.module.connection;

import eu.ydp.empiria.player.client.util.position.Point;

public class LineSegment {

	private final Point pointStart;
	private final Point pointEnd;

	public LineSegment(Point pointStart, Point pointEnd) {
		this.pointStart = pointStart;
		this.pointEnd = pointEnd;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pointEnd == null) ? 0 : pointEnd.hashCode());
		result = prime * result + ((pointStart == null) ? 0 : pointStart.hashCode());
		return result;
	}

	public Point getPointStart() {
		return pointStart;
	}

	public Point getPointEnd() {
		return pointEnd;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		LineSegment other = (LineSegment) obj;
		if (pointEnd == null) {
			if (other.pointEnd != null) {
				return false;
			}
		} else if (!pointEnd.equals(other.pointEnd)) {
			return false;
		}
		if (pointStart == null) {
			if (other.pointStart != null) {
				return false;
			}
		} else if (!pointStart.equals(other.pointStart)) {
			return false;
		}
		return true;
	}

}
