package eu.ydp.empiria.player.client.module.ordering.view;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;
import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItem;

public interface OrderInteractionView extends IsWidget {
	public void createItem(String itemId, XMLContent xmlContent, InlineBodyGeneratorSocket bodyGenerator);
	public void setChildStyles(OrderingItem item);
	public void setChildrenOrder(List<String> childOrder);
	public void setClickListener(OrderItemClickListener clickListener);
}
