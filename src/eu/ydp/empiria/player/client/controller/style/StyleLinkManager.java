package eu.ydp.empiria.player.client.controller.style;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;

public class StyleLinkManager {

	private final List<StyleModel> solidStyles = new ArrayList<StyleModel>();

	public void registerAssessmentStyles(List<String> styleLinks) {
		doRegisterStyleLinks(styleLinks, false);
	}

	public void registerItemStyles(List<String> styleLinks) {
		doRegisterStyleLinks(styleLinks, true);
	}

	private void doRegisterStyleLinks(List<String> styleLinks, boolean areRemovable) {
		for (String link : styleLinks) {
			addStyleIfNotPresent(link);
		}
	}

	private void addStyleIfNotPresent(String link) {
		if (!containsStyle(link)) {
			JavaScriptObject newLink = appendStyleLink(link);

			solidStyles.add(new StyleModel(link, newLink));
		}
	}

	private boolean containsStyle(String link) {
		for (StyleModel styleModel : solidStyles) {
			if (link.equals(styleModel.getName())) {
				return true;
			}
		}
		return false;
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
