package eu.ydp.empiria.player.client.module.ordering.view;

import com.google.gwt.user.client.ui.IsWidget;
import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItem;
import eu.ydp.empiria.player.client.module.ordering.structure.OrderInteractionOrientation;

import java.util.List;

public interface OrderInteractionView extends IsWidget {
	void createItem(OrderingItem orderingItem, XMLContent xmlContent, InlineBodyGeneratorSocket bodyGenerator);

	void setChildStyles(OrderingItem item);

	void setChildrenOrder(List<String> childOrder);

	void setOrientation(OrderInteractionOrientation orientation);

	String getMainPanelUniqueCssClass();
}
