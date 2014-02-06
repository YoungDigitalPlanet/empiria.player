package eu.ydp.empiria.player.client.controller.extensions.internal.stickies;

import eu.ydp.gwtutil.client.geom.Point;

public class StickiePropertiesTestable implements IStickieProperties {

	private int colorIndex = 0;
	private String stickieTitle = "";
	private String stickieContent = "";
	private Point<Integer> position = new Point<Integer>(0, 0);
	private boolean minimized = false;

	@Override
	public int getColorIndex() {
		return colorIndex;
	}

	@Override
	public void setColorIndex(int colorIndex) {
		this.colorIndex = colorIndex;
	}

	@Override
	public String getStickieTitle() {
		return stickieTitle;
	}

	@Override
	public void setStickieTitle(String stickieTitle) {
		this.stickieTitle = stickieTitle;
	}

	@Override
	public String getStickieContent() {
		return stickieContent;
	}

	@Override
	public void setStickieContent(String bookmarkContent) {
		this.stickieContent = bookmarkContent;
	}

	@Override
	public int getX() {
		return position.getX();
	}

	@Override
	public void setX(int x) {
		position = new Point<Integer>(x, position.getY());
	}

	@Override
	public int getY() {
		return position.getY();
	}

	@Override
	public void setY(int y) {
		position = new Point<Integer>(position.getX(), y);
	}

	@Override
	public Point<Integer> getPosition() {
		return position;
	}

	@Override
	public void setPosition(Point<Integer> newPosition) {
		this.position = newPosition;
	}

	@Override
	public boolean isMinimized() {
		return minimized;
	}

	@Override
	public void setMinimized(boolean minimized) {
		this.minimized = minimized;
	}

	@Override
	public void updateTimestamp() {

	}

}
