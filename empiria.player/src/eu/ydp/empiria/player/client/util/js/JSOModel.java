package eu.ydp.empiria.player.client.util.js;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

/**
 * Overlay for generic javascript object 
 *  
 * @author MSamujlo
 */
public class JSOModel extends JavaScriptObject {

	// Overlay types always have protected, zero-arg ctors
	protected JSOModel() {
	}
	
	public static native JavaScriptObject parseJson(String jsonStr) /*-{
	  return eval(jsonStr);
	}-*/;

	public static final JSOModel fromJson( String jsonStr ) {
		JSONValue v = JSONParser.parse( jsonStr );
		JSONObject json = v.isObject();
		JSOModel m = (JSOModel) ((json!=null)? json.getJavaScriptObject().cast() : createObject().cast()); 
		return m;
	}
	
	public final native boolean hasKey(String key) /*-{
		return this[key]!=undefined;
	}-*/;
	
	public final native JsArrayString keys() /*-{
		var a = new Array();
		for (var prop in this) {
			a.push( prop );
		}
		return a;
	}-*/;
	
	public final native String get(String key) /*-{
		return ""+this[ key ];
	}-*/;
	
	public final native void set( String key, String value) /*-{
		this[ key ] = value;
	}-*/;
	
    public final native JSOModel getObject(String key) /*-{
        return this[key];
    }-*/;

    public final native JsArray<JSOModel> getArray(String key) /*-{
        return hasKey( key )? get(key) : new Array();
    }-*/;

}
