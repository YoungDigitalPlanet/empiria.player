package eu.ydp.empiria.player.client.controller.extensions.internal.stickies;

public interface IStickieProperties {

	int getColorIndex();
	void setColorIndex(int colorIndex);
	String getStickieTitle();
	void setStickieTitle(String stickieTitle);
	String getStickieContent();
	void setStickieContent(String bookmarkContent);
	int getX();
	void setX(int x);
	int getY();
	void setY(int y);
	boolean isMinimized();
	void setMinimized(boolean minimized);
	void updateTimestamp();
	
}
