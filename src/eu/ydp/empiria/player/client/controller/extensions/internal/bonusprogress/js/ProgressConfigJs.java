package eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress.js;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class ProgressConfigJs extends JavaScriptObject {

	protected ProgressConfigJs() {}
	
	public final native int getFrom()/*-{
		return this.from;
	}-*/;

	public final native JsArray<ProgressAssetConfigJs> getAssets()/*-{
		return this.assets;
	}-*/;
}
