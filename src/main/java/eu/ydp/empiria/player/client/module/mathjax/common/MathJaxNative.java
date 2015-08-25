package eu.ydp.empiria.player.client.module.mathjax.common;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.inject.Singleton;

@Singleton
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
        if (mathJax && typeof(mathJax.Hub.yProcessElements) === 'function') {
            mathJax.Hub.yProcessElements();
        }
    }-*/;

    public void renderMath(JavaScriptObject callback) {
        renderMathNative(callback);
    }

    private native void renderMathNative(JavaScriptObject callback) /*-{
        var mathJax = $wnd.MathJax;
        if (mathJax && typeof(mathJax.Hub.yProcessElements) === 'function') {
            mathJax.Hub.yProcessElements(callback);
        }
    }-*/;

	public void rerenderMathElement(String divId) {
		rerenderMathElementNative(divId);
	}

	private native void rerenderMathElementNative(String divId) /*-{
		var mathJax = $wnd.MathJax;
		if (mathJax && typeof(mathJax.Hub.yRerenderElement) === 'function') {
			mathJax.Hub.yRerenderElement(divId);
		}
	}-*/;
}
