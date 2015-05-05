package eu.ydp.empiria.player.client.module.external;

import com.google.gwt.json.client.JSONArray;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.AbstractInteractionModule;
import eu.ydp.empiria.player.client.module.external.structure.ExternalInteractionModuleBean;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class ExternalInteractionModule
		extends AbstractInteractionModule<ExternalInteractionModule, ExternalInteractionResponseModel, ExternalInteractionModuleBean> {

	private final ExternalInteractionModulePresenter presenter;
	private final ExternalInteractionResponseModel externalInteractionResponseModel;
	private final ExternalInteractionModuleStructure externalInteractionModuleStructure;

	@Inject
	public ExternalInteractionModule(ExternalInteractionModulePresenter presenter, @ModuleScoped ExternalInteractionResponseModel model,
			ExternalInteractionModuleStructure structure) {
		this.presenter = presenter;
		this.externalInteractionResponseModel = model;
		this.externalInteractionModuleStructure = structure;
	}

	@Override
	protected ExternalInteractionModulePresenter getPresenter() {
		return presenter;
	}

	@Override
	protected void initalizeModule() {
		ExternalInteractionModuleBean bean = externalInteractionModuleStructure.getBean();
		presenter.setBean(bean);

		externalInteractionResponseModel.setResponseModelChange(this);
	}

	@Override
	protected ExternalInteractionResponseModel getResponseModel() {
		return externalInteractionResponseModel;
	}

	@Override
	protected ExternalInteractionModuleStructure getStructure() {
		return externalInteractionModuleStructure;
	}

	@Override
	public JSONArray getState() {
		return presenter.getState();
	}

	@Override
	public void setState(JSONArray stateAndStructure) {
		presenter.setState(stateAndStructure);
	}
}
