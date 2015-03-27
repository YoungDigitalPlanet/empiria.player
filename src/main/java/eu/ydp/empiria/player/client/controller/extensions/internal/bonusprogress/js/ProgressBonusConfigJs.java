package eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress.js;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class ProgressBonusConfigJs extends JavaScriptObject {

	protected ProgressBonusConfigJs() {
	}

	public final native JsArray<ProgressConfigJs> getProgresses()/*-{
																	return this.progresses;
																	}-*/;
}
