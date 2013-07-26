package eu.ydp.empiria.player.client.module.selection.view;

import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;

public interface SelectionElementGenerator {
	void setInlineBodyGenerator(InlineBodyGeneratorSocket inlineBodyGeneratorSocket);
	
	SelectionButtonGridElement createChoiceButtonElement(String styleName);
	
	SelectionItemLabelGridElement createItemDisplayElement(Element item);
	
	SelectionChoiceLabelGridElement createChoiceDisplayElement(Element item);
}
