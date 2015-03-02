package eu.ydp.empiria.player.client.controller.style;

import com.google.gwt.core.client.JavaScriptObject;

public class StyleModel {

	private String name;
	private JavaScriptObject javaScriptObject;

	public StyleModel(String name, JavaScriptObject javaScriptObject) {
		this.name = name;
		this.javaScriptObject = javaScriptObject;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public JavaScriptObject getJavaScriptObject() {
		return javaScriptObject;
	}

	public void setJavaScriptObject(JavaScriptObject javaScriptObject) {
		this.javaScriptObject = javaScriptObject;
	}
}