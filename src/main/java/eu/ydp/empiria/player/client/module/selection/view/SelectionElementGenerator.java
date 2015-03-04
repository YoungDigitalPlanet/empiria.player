package eu.ydp.empiria.player.client.module.selection.view;

import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;

public interface SelectionElementGenerator {
	void setInlineBodyGenerator(InlineBodyGeneratorSocket inlineBodyGeneratorSocket);

	SelectionButtonGridElementImpl createChoiceButtonElement(String styleName);

	SelectionItemGridElementImpl createItemDisplayElement(Element item);

	SelectionChoiceGridElementImpl createChoiceDisplayElement(Element item);
}
