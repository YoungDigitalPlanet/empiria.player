package eu.ydp.empiria.player.client.module.external;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.*;
import eu.ydp.empiria.player.client.module.external.structure.ExternalInteractionModuleBean;
import eu.ydp.empiria.player.client.module.external.view.ExternalInteractionView;
import eu.ydp.empiria.player.client.resources.EmpiriaPaths;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class ExternalInteractionModulePresenter implements ActivityPresenter<ExternalInteractionResponseModel, ExternalInteractionModuleBean> {

	private final EmpiriaPaths empiriaPaths;
	private final ExternalInteractionResponseModel externalInteractionResponseModel;
	private final ExternalInteractionView externalInteractionView;
	private ExternalInteractionModuleBean externalInteractionModuleBean;

	@Inject
	public ExternalInteractionModulePresenter(
			EmpiriaPaths empiriaPaths, @ModuleScoped ExternalInteractionResponseModel externalInteractionResponseModel,
			@ModuleScoped ExternalInteractionView externalInteractionView) {
		this.empiriaPaths = empiriaPaths;
		this.externalInteractionResponseModel = externalInteractionResponseModel;
		this.externalInteractionView = externalInteractionView;
	}

	@Override
	public void bindView() {
		String src = externalInteractionModuleBean.getSrc();
		String externalModuleFilePath = empiriaPaths.getMediaFilePath(src);
		externalInteractionView.setUrl(externalModuleFilePath);
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
		return externalInteractionView.asWidget();
	}
}
