package eu.ydp.empiria.player.client.module.colorfill.fill.stack;

import com.google.gwt.core.client.JavaScriptObject;

import eu.ydp.empiria.player.client.overlaytypes.OverlayTypesParser;

public class JsPoint extends JavaScriptObject {
	private static final OverlayTypesParser typesParser = new OverlayTypesParser();

	protected JsPoint() {
		//
	}

	public static JsPoint newPoint(final int x, final int y) { // NOPMD
		return typesParser.get("{\"x\":" + x + ",\"y\":" + y + "}");
	}

	public final native int getX()/*-{
									return this.x;
									}-*/;

	public final native int getY()/*-{
									return this.y;
									}-*/;

}
