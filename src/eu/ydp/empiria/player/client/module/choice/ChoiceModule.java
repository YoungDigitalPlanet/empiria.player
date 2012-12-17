package eu.ydp.empiria.player.client.module.choice;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.gin.factory.ChoiceModuleFactory;
import eu.ydp.empiria.player.client.module.AbstractInteractionModule;
import eu.ydp.empiria.player.client.module.ActivityPresenter;
import eu.ydp.empiria.player.client.module.abstractmodule.structure.AbstractModuleStructure;
import eu.ydp.empiria.player.client.module.choice.presenter.ChoiceModulePresenter;
import eu.ydp.empiria.player.client.module.choice.structure.ChoiceInteractionBean;
import eu.ydp.empiria.player.client.module.choice.structure.ChoiceModuleJAXBParser;
import eu.ydp.empiria.player.client.module.choice.structure.ChoiceModuleStructure;

public class ChoiceModule extends AbstractInteractionModule<ChoiceModule, ChoiceModuleModel, ChoiceInteractionBean> {

	@Inject
	private ChoiceModulePresenter presenter;

	@Inject
	private ChoiceModuleStructure choiceStructure;

	@Inject
	protected Provider<ChoiceModule> moduleFactory;

	@Inject
	protected ChoiceModuleFactory choiceModuleFactory;

	@Override
	protected void initalizeModule() {
		choiceStructure.setMulti(isMulti());
		presenter.setInlineBodyGenerator(getModuleSocket().getInlineBodyGeneratorSocket());
	}

	private boolean isMulti() {
		return Cardinality.MULTIPLE.equals(getResponse().cardinality);
	}

	@Override
	public ChoiceModule getNewInstance() {
		return moduleFactory.get();
	}

	@Override
	protected ActivityPresenter<ChoiceModuleModel, ChoiceInteractionBean> getPresenter(){
		return presenter;
	}

	@Override
	protected ChoiceModuleModel getResponseModel() {
		return choiceModuleFactory.getChoiceModuleModel(getResponse(), this);
	}

	@Override
	protected AbstractModuleStructure<ChoiceInteractionBean, ChoiceModuleJAXBParser> getStructure() {
		return choiceStructure;
	}

}
