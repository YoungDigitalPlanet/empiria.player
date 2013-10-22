package eu.ydp.empiria.player.client.controller.body;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Node;

public interface InlineBodyGeneratorSocket {

	/**
	 * generuje htmla dla dzieci wskazanego wezla i dolacza je do parentElement
	 *
	 * @param node
	 * @param parentElement
	 */
	void generateInlineBody(Node node, Element parentElement);
	void generateInlineBody(String node, Element parentElement);

	/**
	 * generuje htmla dla dzieci wskazanego wezla
	 *
	 * @param mainNode
	 * @return
	 */
	Widget generateInlineWidget(Node mainNode);

	/**
	 * Generuje htmla dla wskazanego wezla  w postaci hierarchi widgetow
	 *
	 * @param mainNode
	 * @return
	 */
	Widget generateInlinePanelWidget(Node mainNode);
}
