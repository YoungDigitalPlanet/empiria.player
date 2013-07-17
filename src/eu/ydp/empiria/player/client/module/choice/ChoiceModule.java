package eu.ydp.empiria.player.client.module.choice;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.gin.scopes.module.ModuleScoped;
import eu.ydp.empiria.player.client.module.AbstractInteractionModule;
import eu.ydp.empiria.player.client.module.ActivityPresenter;
import eu.ydp.empiria.player.client.module.abstractmodule.structure.AbstractModuleStructure;
import eu.ydp.empiria.player.client.module.choice.presenter.ChoiceModulePresenter;
import eu.ydp.empiria.player.client.module.choice.structure.ChoiceInteractionBean;
import eu.ydp.empiria.player.client.module.choice.structure.ChoiceModuleJAXBParser;
import eu.ydp.empiria.player.client.module.choice.structure.ChoiceModuleStructure;

public class ChoiceModule extends AbstractInteractionModule<ChoiceModule, ChoiceModuleModel, ChoiceInteractionBean> {

	private ChoiceModuleStructure choiceStructure;

	private ChoiceModuleModel moduleModel;

	private ChoiceModulePresenter presenter;

	@Inject
	public ChoiceModule(ChoiceModuleStructure choiceStructure, @ModuleScoped ChoiceModuleModel moduleModel, @ModuleScoped ChoiceModulePresenter presenter) {
		this.choiceStructure = choiceStructure;
		this.moduleModel = moduleModel;
		this.presenter = presenter;
	}

	@Override
	protected void initalizeModule() {
		choiceStructure.setMulti(isMulti());
		if (isMulti()) {
			getResponse().setCountMode(getCountMode());
		}
		presenter.setInlineBodyGenerator(getModuleSocket().getInlineBodyGeneratorSocket());

		moduleModel.initialize(this);
	}

	private boolean isMulti() {
		return Cardinality.MULTIPLE == getResponse().cardinality;
	}

	@Override
	protected ActivityPresenter<ChoiceModuleModel, ChoiceInteractionBean> getPresenter() {
		return presenter;
	}

	@Override
	protected ChoiceModuleModel getResponseModel() {
		return moduleModel;
	}

	@Override
	protected AbstractModuleStructure<ChoiceInteractionBean, ChoiceModuleJAXBParser> getStructure() {
		return choiceStructure;
	}

}
