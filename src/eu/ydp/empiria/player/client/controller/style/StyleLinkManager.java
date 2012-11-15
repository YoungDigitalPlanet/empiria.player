package eu.ydp.empiria.player.client.controller.style;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;

import eu.ydp.gwtutil.client.collections.QueueSet;

public class StyleLinkManager {

	public StyleLinkManager(){
		solidStyles = new HashMap<String, JavaScriptObject>();
		removableStyles = new ArrayList<JavaScriptObject>();
	}

	private final Map<String, JavaScriptObject> solidStyles;
	private final List<JavaScriptObject> removableStyles;

	public void registerAssessmentStyles(QueueSet<String> styleLinks){
		doRegisterStyleLinks(styleLinks, false);
	}

	public void registerItemStyles(QueueSet<String> styleLinks){
		doRegisterStyleLinks(styleLinks, true);
	}

	private void doRegisterStyleLinks(QueueSet<String> styleLinks, boolean areRemovable){

		if (areRemovable){
			for (JavaScriptObject currLink : removableStyles){
				//removeStyleLink(currLink);
			}
			removableStyles.clear();
		}

		for (String link : styleLinks){
			if (solidStyles.containsKey(link)) {
				JavaScriptObject styleLink = solidStyles.get(link);
				if (styleLink !=  null) {
					removeStyleLink(styleLink);
				}
				solidStyles.remove(link);
				JavaScriptObject newLink = appendStyleLink(link);
				solidStyles.put(link, newLink);
			} else if (!areRemovable) {
				JavaScriptObject newLink = appendStyleLink(link);
				solidStyles.put(link, newLink);
			} else {
				JavaScriptObject newLink = appendStyleLink(link);
				removableStyles.add(newLink);
			}
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
