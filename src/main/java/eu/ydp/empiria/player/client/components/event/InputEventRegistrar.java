package eu.ydp.empiria.player.client.components.event;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.IsWidget;

public class InputEventRegistrar {

	public void registerInputHandler(IsWidget widget, InputEventListener listener) {
		InputEventListenerJsWrapper listenerJs = new InputEventListenerJsWrapper(listener);
		registerInputHandler(widget.asWidget().getElement(), listenerJs.getJavaScriptObject());
	}

	private native void registerInputHandler(Element element, JavaScriptObject listenerJs)/*-{
																							var self = this;
																							element.oninput = function(){
																							listenerJs.onInput();
																							}
																							}-*/;
}
