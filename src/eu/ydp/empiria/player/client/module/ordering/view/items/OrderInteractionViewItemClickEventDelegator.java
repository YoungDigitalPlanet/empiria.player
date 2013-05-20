package eu.ydp.empiria.player.client.module.ordering.view.items;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import eu.ydp.empiria.player.client.module.ordering.view.OrderItemClickListener;

public class OrderInteractionViewItemClickEventDelegator {
	public void bind(final OrderInteractionViewItem viewItem, final OrderItemClickListener clickListener) {
		if (clickListener != null) {
			viewItem.asWidget().addDomHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					clickListener.itemClicked(viewItem.getItemId());
				}
			}, ClickEvent.getType());
		}
	}
}
