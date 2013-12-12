package eu.ydp.empiria.player.client.module.ordering.presenter;

import eu.ydp.empiria.player.client.module.ordering.view.OrderItemClickListener;

public class OrderItemClickListenerImpl implements OrderItemClickListener{

	private final OrderInteractionPresenter orderInteractionPresenter;
	
	public OrderItemClickListenerImpl(OrderInteractionPresenter orderInteractionPresenter) {
		this.orderInteractionPresenter = orderInteractionPresenter;
	}

	@Override
	public void itemClicked(String childId) {
		orderInteractionPresenter.itemClicked(childId);
	}

}
