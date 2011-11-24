package eu.ydp.empiria.player.client.style;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;

import eu.ydp.empiria.player.client.util.js.JSOModel;

public class StyleDocument {

	protected JavaScriptObject styleSheet;
	protected String basePath;
	
	public StyleDocument(JavaScriptObject styleSheet, String basePath){
		this.styleSheet = styleSheet;
		int i1 = basePath.lastIndexOf("\\");
		int i2 = basePath.lastIndexOf("/");
		if (i1 > i2){
			this.basePath = basePath.substring(0, i1+1);
		} else if (i2 > i1) {
			this.basePath = basePath.substring(0, i2+1);
		} else {
			this.basePath = basePath;
		}
	}

	public JSOModel getDeclarationsForSelector(String selector, JSOModel result){
		JsCssModel jsSheet = styleSheet.cast();
		result = jsSheet.getDeclarationsForSelector(selector, result);
		JsArrayString keys = result.keys();
		for (int i = 0; i < keys.length(); i++) {
			String key = keys.get(i);
			String value = result.get(key);
			if (value.trim().toLowerCase().startsWith("url(")){
				String value2 = value.trim().toLowerCase();
				String path = value2.substring(value2.indexOf("url(")+4, value2.lastIndexOf(")") );
				path = path.trim();
				if ( (path.startsWith("\"")  &&  path.endsWith("\""))  ||
					 (path.startsWith("/")  &&  path.endsWith("/"))){
					path = path.substring(1, path.length()-1);
				}
				path = basePath + path;
				result.set(key, path);
			}
		}
		return result;
	}
	
}
