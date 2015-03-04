package eu.ydp.empiria.player.client.module.ordering.view.items;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class OrderInteractionViewItemImpl extends Composite implements OrderInteractionViewItem {

	private static OrderInrteractionViewItemUiBinder uiBinder = GWT.create(OrderInrteractionViewItemUiBinder.class);

	interface OrderInrteractionViewItemUiBinder extends UiBinder<Widget, OrderInteractionViewItemImpl> {
	}

	@UiField
	protected FlowPanel mainPanel;

	private final String itemId;

	@Inject
	public OrderInteractionViewItemImpl(@Assisted IsWidget body, @Assisted String itemId) {
		this.itemId = itemId;
		initWidget(uiBinder.createAndBindUi(this));
		mainPanel.add(body);
	}

	@Override
	public String getItemId() {
		return itemId;
	}

}
