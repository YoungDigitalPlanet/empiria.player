package eu.ydp.empiria.player.client.util.position;

public class Point {
	private final int xPos;
	private final int yPos;

	public Point(int xPos,int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}

	public int getX(){
		return xPos;
	}

	public int getY(){
		return yPos;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Point [xPos=").append(xPos).append(", yPos=").append(yPos).append("]");
		return builder.toString();
	}


}
