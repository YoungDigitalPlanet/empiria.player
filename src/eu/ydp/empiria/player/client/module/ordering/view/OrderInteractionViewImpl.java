package eu.ydp.empiria.player.client.module.ordering.view;

import java.util.List;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItem;
import eu.ydp.empiria.player.client.module.ordering.view.items.OrderInteractionViewItem;
import eu.ydp.empiria.player.client.module.ordering.view.items.OrderInteractionViewItemStyles;
import eu.ydp.empiria.player.client.module.ordering.view.items.OrderInteractionViewItems;

public class OrderInteractionViewImpl extends Composite implements OrderInteractionView {

	@Inject
	private OrderInteractionViewItems viewItems;

	@Inject
	private OrderInteractionViewWidget viewWidget;

	@Inject
	private OrderInteractionViewItemStyles interactionViewItemStyles;

	@Override
	public void createItem(OrderingItem orderingItem, XMLContent xmlContent, InlineBodyGeneratorSocket bodyGenerator) {
		Widget widgetItem = bodyGenerator.generateInlineBody(xmlContent.getValue());
		addItem(orderingItem, widgetItem);
	}

	private void addItem(OrderingItem orderingItem, Widget widgetItem) {
		OrderInteractionViewItem viewItem = viewItems.addItem(orderingItem.getId(), widgetItem);
		viewWidget.add(viewItem);
	}

	@Override
	public void setChildrenOrder(List<String> childOrder) {
		List<IsWidget> itemsInOrder = viewItems.getItemsInOrder(childOrder);
		putItemsOnView(itemsInOrder);
	}

	private void putItemsOnView(List<IsWidget> itemsInOrder) {
		viewWidget.putItemsOnView(itemsInOrder);
	}

	@Override
	public void setClickListener(OrderItemClickListener clickListener) {
		viewItems.setItemClickListener(clickListener);
	}

	@Override
	public void setChildStyles(OrderingItem item) {
		interactionViewItemStyles.applyStylesOnWidget(item, viewItems.getItem(item.getId()));

	}

}