package eu.ydp.empiria.player.client.controller.extensions.internal.stickies;

import com.google.gwt.core.client.JavaScriptObject;

public class StickieProperties extends JavaScriptObject implements IStickieProperties {

	protected StickieProperties(){}
	
	public static StickieProperties newInstance() {
		StickieProperties sp = JavaScriptObject.createObject().cast();
		sp.setColorIndex(-1);
		sp.setMinimized(false);
		sp.setStickieTitle("");
		sp.setStickieContent("");
		sp.setX(0);
		sp.setY(0);
		return sp;
	}

	public final native int getColorIndex() /*-{
		return this.colorIndex;
	}-*/;

	public final native void setColorIndex(int colorIndex) /*-{
		this.colorIndex = colorIndex;
	}-*/;

	public final native String getStickieTitle() /*-{
		return this.stickieTitle;
	}-*/;

	public final native void setStickieTitle(String stickieTitle) /*-{
		this.stickieTitle = stickieTitle;
	}-*/;

	public final native String getStickieContent() /*-{
		return this.stickieContent;
	}-*/;

	public final native void setStickieContent(String bookmarkContent) /*-{
		this.stickieContent = bookmarkContent;
	}-*/;

	public final native int getX() /*-{
		return this.x;
	}-*/;

	public final native void setX(int x) /*-{
		this.x = x;
	}-*/;

	public final native int getY() /*-{
		return this.y;
	}-*/;

	public final native void setY(int y) /*-{
		this.y = y;
	}-*/;

	public final native boolean isMinimized() /*-{
		return this.minimized;
	}-*/;

	public final native void setMinimized(boolean minimized) /*-{
		this.minimized = minimized;
	}-*/;

	public final native void updateTimestamp() /*-{
		this.timestamp = new Date().getTime();
	}-*/;
	
	public final native void setTimestamp(double ts)/*-{
		this.timestamp = ts;
	}-*/;
	
	public final native double getTimestamp()/*-{
		return this.timestamp;
	}-*/;

}

