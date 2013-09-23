package eu.ydp.empiria.player.client.controller.extensions.internal.bonus.js;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class BonusConfigJs extends JavaScriptObject {

	protected BonusConfigJs() {}
	
	public final native JsArray<BonusActionJs> getActions()/*-{
		return this.actions;
	}-*/;
	
}
