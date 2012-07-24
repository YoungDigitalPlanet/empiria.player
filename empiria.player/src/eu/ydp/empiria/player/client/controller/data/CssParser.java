package eu.ydp.empiria.player.client.controller.data;

import com.google.gwt.core.client.JavaScriptObject;

public abstract class CssParser {

	public static native JavaScriptObject parseCss(String css) /*-{
		var parser = new $wnd.CSSParser();
		return parser.parse(css, false, true);
	}-*/;
}
