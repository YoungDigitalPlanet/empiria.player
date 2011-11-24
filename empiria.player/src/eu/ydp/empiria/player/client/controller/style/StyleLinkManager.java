package eu.ydp.empiria.player.client.controller.style;

import java.util.Vector;

import com.google.gwt.core.client.JavaScriptObject;

public class StyleLinkManager {

	public StyleLinkManager(){
		currentAssessmentStyles = new Vector<JavaScriptObject>();
		currentItemStyles = new Vector<JavaScriptObject>();
	}
	
	private Vector<JavaScriptObject> currentAssessmentStyles;
	private Vector<JavaScriptObject> currentItemStyles;

	public void registerAssessmentStyles(Vector<String> styleLinks){
		registerStyleLinks(styleLinks, currentAssessmentStyles);
	}

	public void registerItemStyles(Vector<String> styleLinks){
		registerStyleLinks(styleLinks, currentItemStyles);
	}
	
	private void registerStyleLinks(Vector<String> styleLinks, Vector<JavaScriptObject> storedLinks){

		while (storedLinks.size() > 0){
			removeStyleLink(storedLinks.get(0));
			storedLinks.remove(0);
		}
		
		for (int s = 0 ; s < styleLinks.size() ; s ++){
			JavaScriptObject newLink = appendStyleLink(styleLinks.get(s));
			storedLinks.add(newLink);
		}
	}
	
	public native String getUserAgent() /*-{
		return navigator.userAgent;
	}-*/;

	private native JavaScriptObject appendStyleLink(String link) /*-{
		var headID = $wnd.document.getElementsByTagName("head")[0];         
		var cssNode = $wnd.document.createElement('link');
		cssNode.type = 'text/css';
		cssNode.rel = 'stylesheet';
		cssNode.href = link;
		cssNode.media = 'screen';
		headID.appendChild(cssNode);
		return cssNode;
	}-*/; 

	private native void removeStyleLink(JavaScriptObject cssNode) /*-{
		try {
			var headID = $wnd.document.getElementsByTagName("head")[0];        
			headID.removeChild(cssNode);
        } catch (e) {
			cssNode.parentNode.removeChild(cssNode);
		} 
	}-*/; 
	
}
