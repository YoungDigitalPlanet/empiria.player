package eu.ydp.empiria.player.client.module.colorfill.fill.stack;

import com.google.gwt.core.client.JavaScriptObject;

import eu.ydp.empiria.player.client.overlaytypes.OverlayTypesParser;

public class JsStack extends JavaScriptObject {

	public static JsStack newJsStack() {
		JsStack jsStack = new OverlayTypesParser().get();
		jsStack.init();
		return jsStack;
	}

	private final native void init()/*-{
		this.stack = [];
	}-*/;

	protected JsStack() {
		//
	}

	public final native void push(JsPoint point)/*-{
			this.stack.push(point);
	}-*/;

	public final native JsPoint pop()/*-{
		return this.stack.pop();
	}-*/;

	public final native boolean isEmpty()/*-{
		return this.stack.length == 0;
	}-*/;

}
