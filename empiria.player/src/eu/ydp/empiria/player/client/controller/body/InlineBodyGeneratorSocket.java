package eu.ydp.empiria.player.client.controller.body;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Node;

public interface InlineBodyGeneratorSocket {

	public void generateInlineBody(Node node, Element parentElement);
	public Widget generateInlineBody(Node mainNode);
}
