package eu.ydp.empiria.player.client.util.events.pagechange;

import com.google.common.base.Optional;
import com.google.gwt.core.client.JavaScriptObject;

public class EventDispatcher {
	private Optional<JavaScriptFunction> callbackFunction = Optional.absent();

	public void setCallbackFunction(JavaScriptFunction callbackFunction) {
		this.callbackFunction = Optional.of(callbackFunction);
	}

	public void dispatch(Event event) {
		if (callbackFunction.isPresent()) {
			JavaScriptFunction javaScriptFunction = callbackFunction.get();
			JavaScriptObject jsObject = event.getJSObject();
			javaScriptFunction.callback(jsObject);
		}
	}
}
