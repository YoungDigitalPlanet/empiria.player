package eu.ydp.empiria.player.client.controller.extensions.internal.jsonreport;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;

import eu.ydp.empiria.player.client.overlaytypes.OverlayTypesParser;

public class ItemJsonReport extends JavaScriptObject {

	protected ItemJsonReport() {

	}

	public static ItemJsonReport create() {
		return new OverlayTypesParser().get();
	}

	public static ItemJsonReport create(String json) {
		return new OverlayTypesParser().get(json);
	}

	public final native void setIndex(int index)/*-{
												this.index = index;		
												}-*/;

	public final native void setTitle(String title)/*-{
													this.title = title;
													}-*/;

	public final native void setResult(ResultJsonReport result)/*-{
																this.result = result;
																}-*/;

	public final native void setHints(HintJsonReport hints)/*-{
															this.hints = hints;
															}-*/;

	public final String getJSONString() {
		return new JSONObject(this).toString();
	}

}
