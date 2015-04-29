package eu.ydp.empiria.player.client.module.external;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.AbstractInteractionModule;
import eu.ydp.empiria.player.client.module.external.structure.*;

public class ExternalInteractionModule extends AbstractInteractionModule<ExternalInteractionModule, ExternalInteractionResponseModel, ExternalInteractionModuleBean> {

	private final ExternalInteractionModulePresenter externalInteractionModulePresenter;
	private final ExternalInteractionResponseModel externalInteractionResponseModel;
	private final ExternalInteractionModuleStructure externalInteractionModuleStructure;

	@Inject
	public ExternalInteractionModule(ExternalInteractionModulePresenter externalInteractionModulePresenter, ExternalInteractionResponseModel externalInteractionResponseModel,
			ExternalInteractionModuleStructure externalInteractionModuleStructure) {
		this.externalInteractionModulePresenter = externalInteractionModulePresenter;
		this.externalInteractionResponseModel = externalInteractionResponseModel;
		this.externalInteractionModuleStructure = externalInteractionModuleStructure;
	}

	@Override
	protected ExternalInteractionModulePresenter getPresenter() {
		return externalInteractionModulePresenter;
	}

	@Override
	protected void initalizeModule() {

	}

	@Override
	protected ExternalInteractionResponseModel getResponseModel() {
		return externalInteractionResponseModel;
	}

	@Override
	protected ExternalInteractionModuleStructure getStructure() {
		return externalInteractionModuleStructure;
	}
}
