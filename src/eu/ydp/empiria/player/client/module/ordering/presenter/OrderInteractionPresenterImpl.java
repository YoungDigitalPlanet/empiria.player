package eu.ydp.empiria.player.client.module.ordering.presenter;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.MarkAnswersMode;
import eu.ydp.empiria.player.client.module.MarkAnswersType;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.ShowAnswersType;
import eu.ydp.empiria.player.client.module.ordering.OrderInteractionModuleModel;
import eu.ydp.empiria.player.client.module.ordering.structure.OrderInteractionBean;
import eu.ydp.empiria.player.client.module.ordering.view.OrderInteractionView;

public class OrderInteractionPresenterImpl implements OrderInteractionPresenter {

	@Inject
	private OrderInteractionView interactionView;

	@Override
	public Widget asWidget() {
		return interactionView.asWidget();
	}

	@Override
	public void bindView() {
		// TODO Auto-generated method stub

	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setModel(OrderInteractionModuleModel model) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setModuleSocket(ModuleSocket socket) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setBean(OrderInteractionBean bean) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLocked(boolean locked) {
		// TODO Auto-generated method stub

	}

	@Override
	public void markAnswers(MarkAnswersType type, MarkAnswersMode mode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showAnswers(ShowAnswersType mode) {
		// TODO Auto-generated method stub

	}
}
