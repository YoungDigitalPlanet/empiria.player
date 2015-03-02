package eu.ydp.empiria.player.client.controller.extensions.internal.jsonreport;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;

import eu.ydp.empiria.player.client.overlaytypes.OverlayTypesParser;

public class HintJsonReport extends JavaScriptObject {

	protected HintJsonReport() {

	}

	public static HintJsonReport create() {
		return new OverlayTypesParser().get();
	}

	public static HintJsonReport create(String json) {
		return new OverlayTypesParser().get(json);
	}

	public final native void setChecks(int value)/*-{
													this.checks = value;
													}-*/;

	public final native void setMistakes(int value)/*-{
													this.mistakes = value;
													}-*/;

	public final native void setShowAnswers(int value)/*-{
														this.showAnswers = value;		
														}-*/;

	public final native void setReset(int value)/*-{
												this.reset = value;		
												}-*/;

	public final String getJSONString() {
		return new JSONObject(this).toString();
	}

}
