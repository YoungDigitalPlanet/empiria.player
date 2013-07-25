package eu.ydp.empiria.player.client.animation.css;

import javax.annotation.PostConstruct;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.StyleElement;
import com.google.gwt.dom.client.Text;
import com.google.gwt.user.client.DOM;

public class StyleAppender {
	private StyleElement style;

	@PostConstruct
	public void postConstruct() {
		style = StyleElement.as(DOM.createElement("style"));
		Document.get().getElementsByTagName("head").getItem(0).appendChild(style);
	}

	public void appendStyleToDocument(String template) {
		Text text = Text.as(Document.get().createTextNode(template));
		style.appendChild(text);
	}
}
