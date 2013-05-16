package eu.ydp.empiria.player.client.module.ordering.view;

import java.util.List;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.ordering.model.OrderingItem;

public class OrderInteractionViewImpl implements OrderInteractionView {

	@Override
	public void createItem(OrderingItem orderingItem, XMLContent xmlContent, InlineBodyGeneratorSocket bodyGenerator) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setChildrenOrder(List<String> childOrder) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setClickListener(OrderItemClickListener clickListener) {
		// TODO Auto-generated method stub
	}

	@Override
	public Widget asWidget() {
		return new Label(getClass().getName());
	}

	@Override
	public void setChildStyles(OrderingItem item) {
		// TODO Auto-generated method stub
		
	}

}
