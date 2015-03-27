package eu.ydp.empiria.player.client.controller.extensions.internal.stickies;

import com.google.gwt.core.client.JavaScriptObject;

import eu.ydp.gwtutil.client.geom.Point;

public class StickieProperties extends JavaScriptObject implements IStickieProperties {

	private static final int OUT_OF_THE_SCREEN_COORDINATE = -2000;

	protected StickieProperties() {
	}

	public static StickieProperties newInstance() {
		StickieProperties sp = JavaScriptObject.createObject().cast();
		sp.setColorIndex(-1);
		sp.setMinimized(false);
		sp.setStickieTitle("");
		sp.setStickieContent("");
		sp.setPosition(OUT_OF_THE_SCREEN_COORDINATE, OUT_OF_THE_SCREEN_COORDINATE);
		return sp;
	}

	@Override
	public final native int getColorIndex() /*-{
											return this.colorIndex;
											}-*/;

	@Override
	public final native void setColorIndex(int colorIndex) /*-{
															this.colorIndex = colorIndex;
															}-*/;

	@Override
	public final native String getStickieTitle() /*-{
													return this.stickieTitle;
													}-*/;

	@Override
	public final native void setStickieTitle(String stickieTitle) /*-{
																	this.stickieTitle = stickieTitle;
																	}-*/;

	@Override
	public final native String getStickieContent() /*-{
													return this.stickieContent;
													}-*/;

	@Override
	public final native void setStickieContent(String bookmarkContent) /*-{
																		this.stickieContent = bookmarkContent;
																		}-*/;

	@Override
	public final native int getX() /*-{
									return this.x;
									}-*/;

	@Override
	public final native void setX(int x) /*-{
											this.x = x;
											}-*/;

	@Override
	public final native int getY() /*-{
									return this.y;
									}-*/;

	@Override
	public final native void setY(int y) /*-{
											this.y = y;
											}-*/;

	@Override
	public final native boolean isMinimized() /*-{
												return this.minimized;
												}-*/;

	@Override
	public final native void setMinimized(boolean minimized) /*-{
																this.minimized = minimized;
																}-*/;

	public final native void setTimestamp(double ts)/*-{
													this.timestamp = ts;
													}-*/;

	public final native double getTimestamp()/*-{
												return this.timestamp;
												}-*/;

	@Override
	public final native void updateTimestamp() /*-{
												this.timestamp = new Date().getTime();
												}-*/;

	public final native void setPosition(int x, int y) /*-{
														this.x = x;
														this.y = y;
														}-*/;

	@Override
	public final Point<Integer> getPosition() {
		return new Point<Integer>(getX(), getY());
	}

	@Override
	public final void setPosition(Point<Integer> newPosition) {
		setX(newPosition.getX());
		setY(newPosition.getY());
	}

}
