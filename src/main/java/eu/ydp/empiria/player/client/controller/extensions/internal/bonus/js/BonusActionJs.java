package eu.ydp.empiria.player.client.controller.extensions.internal.bonus.js;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class BonusActionJs extends JavaScriptObject {

	protected BonusActionJs() {
	}

	public final native String getType() /*-{
											return this.type;
											}-*/;

	public final native JsArray<BonusResourceJs> getBonuses() /*-{
																return this.bonuses;
																}-*/;
}
