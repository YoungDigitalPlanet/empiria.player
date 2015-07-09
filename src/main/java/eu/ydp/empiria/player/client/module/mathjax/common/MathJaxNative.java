package eu.ydp.empiria.player.client.module.mathjax.common;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;

public class MathJaxNative {

	public void renderMath() {
		renderMathNative();
	}

	public void addElementToRender(Element element) {
		addElementNative(element);
	}

	private native void addElementNative(Element element) /*-{
        $wnd.MathJax.Hub.yElements.push(element);
    }-*/;

	private native void renderMathNative() /*-{
        var mathJax = $wnd.MathJax;
        if (mathJax) {
            mathJax.Hub.yProcessElements();
        }
    }-*/;

	public void renderMath(JavaScriptObject jso) {
		renderMathNative(jso);
	}

	private native void renderMathNative(JavaScriptObject jso) /*-{
        var mathJax = $wnd.MathJax;
        if (mathJax) {
            mathJax.Hub.yProcessElements(jso);
        }
    }-*/;
}