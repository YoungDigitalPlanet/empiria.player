package eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress.js;

import com.google.gwt.core.client.JavaScriptObject;

public class ProgressAssetConfigJs extends JavaScriptObject {

	protected ProgressAssetConfigJs() {}
	
	public final native String getAsset()/*-{
		return this.asset;
	}-*/;

	public final native int getWidth()/*-{
		return this.width;
	}-*/;

	public final native int getHeight()/*-{
		return this.height;
	}-*/;

	public final native int getCount()/*-{
		return this.count || 1;
	}-*/;
}
