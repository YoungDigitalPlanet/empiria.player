package eu.ydp.empiria.player.client.module.external;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.*;
import eu.ydp.empiria.player.client.module.external.structure.ExternalInteractionModuleBean;
import eu.ydp.empiria.player.client.module.external.view.ExternalInteractionView;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class ExternalInteractionModulePresenter implements ActivityPresenter<ExternalInteractionResponseModel, ExternalInteractionModuleBean> {

	private final ExternalInteractionView view;
	private final ExternalInteractionResponseModel externalInteractionResponseModel;
	private final ExternalInteractionView externalInteractionView;
	private ExternalInteractionModuleBean externalInteractionModuleBean;

	@Inject
	public ExternalInteractionModulePresenter(
			ExternalInteractionView view,
			@ModuleScoped ExternalInteractionResponseModel externalInteractionResponseModel,
			@ModuleScoped ExternalInteractionView externalInteractionView) {
		this.view = view;
		this.externalInteractionResponseModel = externalInteractionResponseModel;
		this.externalInteractionView = externalInteractionView;
	}

	@Override
	public void bindView() {
		String src = externalInteractionModuleBean.getSrc();
		view.setUrl(src);
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
	public void setBean(ExternalInteractionModuleBean externalInteractionModuleBean1) {
		this.externalInteractionModuleBean = externalInteractionModuleBean1;
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
