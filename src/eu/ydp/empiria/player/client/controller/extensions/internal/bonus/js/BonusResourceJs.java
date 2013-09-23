package eu.ydp.empiria.player.client.controller.extensions.internal.bonus.js;

import com.google.gwt.core.client.JavaScriptObject;

public class BonusResourceJs extends JavaScriptObject {

	protected BonusResourceJs() {};
	
	public final native String getAsset()/*-{
		return this.asset;
	}-*/;
	
	public final native String getType()/*-{
		return this.type;
	}-*/;
	
	public final native int getWidth()/*-{
		return this.width;
	}-*/;
	
	public final native int getHeight()/*-{
		return this.height;
	}-*/;
}
