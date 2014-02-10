package eu.ydp.empiria.player.client.util.style;

import com.google.gwt.core.client.JavaScriptObject;

public interface NativeStyleHelper {
	public void applyProperty(JavaScriptObject element, String propertyName, String value);

	public void callFunction(JavaScriptObject element, String functionName, String values);
}
