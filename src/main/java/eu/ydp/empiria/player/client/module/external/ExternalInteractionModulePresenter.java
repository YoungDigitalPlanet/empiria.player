package eu.ydp.empiria.player.client.module.external;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.*;
import eu.ydp.empiria.player.client.module.external.structure.ExternalInteractionModuleBean;
import eu.ydp.empiria.player.client.module.external.view.ExternalInteractionView;

public class ExternalInteractionModulePresenter implements ActivityPresenter<ExternalInteractionResponseModel, ExternalInteractionModuleBean> {

	private final ExternalInteractionView view;

	@Inject
	public ExternalInteractionModulePresenter(ExternalInteractionView view) {
		this.view = view;
	}

	@Override
	public void bindView() {
	}

	@Override
	public void reset() {

	}

	@Override
	public void setModel(ExternalInteractionResponseModel model) {
	}

	@Override
	public void setModuleSocket(ModuleSocket socket) {

	}

	@Override
	public void setBean(ExternalInteractionModuleBean bean) {

	}

	@Override
	public void setLocked(boolean locked) {

	}

	@Override
	public void markAnswers(MarkAnswersType type, MarkAnswersMode mode) {

	}

	@Override
	public void showAnswers(ShowAnswersType mode) {

	}

	@Override
	public Widget asWidget() {
		return view.asWidget();
	}
}
