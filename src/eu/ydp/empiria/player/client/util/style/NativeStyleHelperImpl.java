package eu.ydp.empiria.player.client.util.style;

import com.google.gwt.core.client.JavaScriptObject;

public class NativeStyleHelperImpl implements NativeStyleHelper {

	@Override
	public native void applyProperty(JavaScriptObject element, String propertyName, String value)/*-{
																									element[propertyName] = value;
																									}-*/;

	// FIXME dodac obsluge funkcji
	@Override
	public native void callFunction(JavaScriptObject element, String functionName, String values)/*-{

																									}-*/;
}
