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
	public void generateInlineBody(Node node, Element parentElement);
	
	public void generateInlineBody(String node, Element parentElement);

	/**
	 * generuje htmla dla dzieci wskazanego wezla
	 *
	 * @param mainNode
	 * @return
	 */
	public Widget generateInlineBody(Node mainNode);

	/**
	 * Generuje htmla dla wskazanego wezla.
	 *
	 * @param mainNode
	 * @return
	 */
	public Widget generateInlineBodyForNode(Node mainNode);
	/**
	 * generuje htmla dla dzieci wskazanego wezla w postaci hierarchi widgetow
	 *
	 * @param mainNode
	 * @return
	 */
	Widget generateInlineBodyForNode(Node mainNode, boolean allAsWidget);
	/**
	 * Generuje htmla dla wskazanego wezla  w postaci hierarchi widgetow
	 *
	 * @param mainNode
	 * @return
	 */
	Widget generateInlineBody(Node mainNode, boolean allAsWidget);
	
	Widget generateInlineBody(String mainNode, boolean allAsWidget);
}
