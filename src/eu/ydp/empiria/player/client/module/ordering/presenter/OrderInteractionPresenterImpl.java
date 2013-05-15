package eu.ydp.empiria.player.client.module.ordering.presenter;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.ordering.view.OrderInteractionView;

public class OrderInteractionPresenterImpl implements OrderInteractionPresenter {

	@Inject
	private OrderInteractionView interactionView;

	@Override
	public Widget asWidget() {
		return interactionView.asWidget();
	}
}
