package eu.ydp.empiria.player.client.module.choice;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.gin.factory.ChoiceModuleFactory;
import eu.ydp.empiria.player.client.gin.factory.ModuleFactory;
import eu.ydp.empiria.player.client.module.AbstractInteractionModule;
import eu.ydp.empiria.player.client.module.ActivityPresenter;
import eu.ydp.empiria.player.client.module.abstractmodule.structure.AbstractModuleStructure;
import eu.ydp.empiria.player.client.module.choice.structure.ChoiceInteractionBean;
import eu.ydp.empiria.player.client.module.choice.structure.ChoiceModuleJAXBParser;
import eu.ydp.empiria.player.client.module.choice.structure.ChoiceModuleStructure;
import eu.ydp.empiria.player.client.module.choice.structure.SimpleChoiceBean;

public class ChoiceModule extends AbstractInteractionModule<ChoiceModule, ChoiceModuleModel, ChoiceInteractionBean> {
	
	@Inject
	private ChoiceModulePresenter presenter;
	
	@Inject
	private ChoiceModuleStructure choiceStructure;

	@Inject
	protected ModuleFactory moduleFactory;

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
	protected void initializeAndInstallFeedbacks() {
		super.initializeAndInstallFeedbacks();

		// TODO: rewrite to JAXB
		for (SimpleChoiceBean choiceOption : choiceStructure.getSimpleChoices()) {
			String identifier = choiceOption.getIdentifier();
			Element feedbackNode = choiceStructure.getFeedbackElement(identifier);

			if (feedbackNode != null) {
				IsWidget feedbackPlaceholder = presenter.getFeedbackPlaceholderByIdentifier(identifier);
				createInlineFeedback(feedbackPlaceholder, feedbackNode);
			}
		}
	}

	@Override
	public ChoiceModule getNewInstance() {
		return moduleFactory.getChoiceModule();
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
