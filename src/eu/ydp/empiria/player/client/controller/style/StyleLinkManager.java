package eu.ydp.empiria.player.client.controller.style;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;

import eu.ydp.gwtutil.client.collections.QueueSet;

public class StyleLinkManager {
	private final Map<String, JavaScriptObject> solidStyles = new HashMap<String, JavaScriptObject>();

	public void registerAssessmentStyles(QueueSet<String> styleLinks) {
		doRegisterStyleLinks(styleLinks, false);
	}

	public void registerItemStyles(QueueSet<String> styleLinks) {
		doRegisterStyleLinks(styleLinks, true);
	}

	private void doRegisterStyleLinks(QueueSet<String> styleLinks, boolean areRemovable) {
		for (String link : styleLinks) {
			addStyleIfNotPresent(link);
		}
	}

	private void addStyleIfNotPresent(String link) {
		if (!solidStyles.containsKey(link)) {
			JavaScriptObject newLink = appendStyleLink(link);
			solidStyles.put(link, newLink);
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
