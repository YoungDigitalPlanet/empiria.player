package eu.ydp.empiria.player.client.util.position;

public class Point {
    private final int xPos;
    private final int yPos;

    public Point(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public int getX() {
        return xPos;
    }

    public int getY() {
        return yPos;
    }

    public double distance(Point pt) {
        int dx = pt.getX() - this.getX();
        int dy = pt.getY() - this.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Point [xPos=").append(xPos).append(", yPos=").append(yPos).append("]");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + xPos;
        result = prime * result + yPos;
        return result;
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
        Point other = (Point) obj;
        if (xPos != other.xPos) {
            return false;
        }
        if (yPos != other.yPos) {
            return false;
        }
        return true;
    }

}
