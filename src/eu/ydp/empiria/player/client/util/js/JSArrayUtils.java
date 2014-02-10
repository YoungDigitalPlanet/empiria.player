package eu.ydp.empiria.player.client.util.js;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class JSArrayUtils {

	public static native JsArray<JavaScriptObject> createArray(int length)/*-{
																			return new Array(length);
																			}-*/;

	public static native JavaScriptObject fillArray(JavaScriptObject array, int index, int item)/*-{
																								array[index] = item;
																								return array;
																								}-*/;

	public static native JavaScriptObject fillArray(JavaScriptObject array, int index, String item)/*-{
																									array[index] = item;
																									return array;
																									}-*/;

	public static native JavaScriptObject fillArray(JavaScriptObject array, int index, JavaScriptObject item)/*-{
																												array[index] = item;
																												return array;
																												}-*/;

	public static native JavaScriptObject fillArray(JavaScriptObject array, String index, String item)/*-{
																										array[index] = item;
																										return array;
																										}-*/;

	public static native JavaScriptObject fillArray(JavaScriptObject array, String index, boolean item)/*-{
																										array[index] = item;
																										return array;
																										}-*/;

	public static native JavaScriptObject fillArray(JavaScriptObject array, String index, JavaScriptObject item)/*-{
																												array[index] = item;
																												return array;
																												}-*/;

}
