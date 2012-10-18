package eu.ydp.empiria.player.client.module.choice;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.gin.factory.ChoiceModuleFactory;
import eu.ydp.empiria.player.client.gin.factory.ModuleFactory;
import eu.ydp.empiria.player.client.module.AbstractInteractionModule;
import eu.ydp.empiria.player.client.module.AbstractResponseModel;
import eu.ydp.empiria.player.client.module.ActivityPresenter;
import eu.ydp.empiria.player.client.module.abstractmodule.structure.AbstractModuleStructure;
import eu.ydp.empiria.player.client.module.choice.structure.ChoiceInteractionBean;
import eu.ydp.empiria.player.client.module.choice.structure.ChoiceModuleStructure;
import eu.ydp.empiria.player.client.module.choice.structure.SimpleChoiceBean;
import eu.ydp.empiria.player.client.util.events.choice.ChoiceModuleEventType;

public class ChoiceModule extends AbstractInteractionModule<ChoiceModule, String, ChoiceInteractionBean> {
	
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
		choiceStructure.createFromXml(getModuleElement().toString());
		choiceStructure.setMulti(isMulti());
		/*presenter.setInlineBodyGenerator(getModuleSocket().getInlineBodyGeneratorSocket());
		
		presenter.setPrompt(choiceStructure.getPrompt());
		presenter.setChoices(choiceStructure.getSimpleChoices());*/

		addListeners();
	}

	private boolean isMulti() {
		return getResponse().cardinality == Cardinality.MULTIPLE;
	}

	private void addListeners() {
		ChoiceModuleListener listener = choiceModuleFactory.getChoiceModuleListener(this);
		listener.addEventHandler(ChoiceModuleEventType.ON_CHOICE_CLICK);
	}

	@Override
	protected void initializeAndInstallFeedbacks() {
		super.initializeAndInstallFeedbacks();

		// TODO: rewrite to JAXB
		for (SimpleChoiceBean choiceOption : choiceStructure.getSimpleChoices()) {
			String identifier = choiceOption.getIdentifier();
			Element feedbackNode = choiceStructure.getFeedbackElement(identifier);

			if (feedbackNode != null) {
				Widget feedbackPlaceholder = presenter.getFeedbackPlaceholderByIdentifier(identifier);
				createInlineFeedback(feedbackPlaceholder, feedbackNode);
			}
		}
	}

	public void onSimpleChoiceClick(String identifier) {
		if (!locked) {
			presenter.switchChoiceSelection(identifier);
			updateResponse(identifier);
			fireStateChanged(true);
		}
	}

	private void updateResponse(String identifier) {
		boolean selected = presenter.isChoiceSelected(identifier);

		if (selected) {
			getResponse().add(identifier);
		} else {
			getResponse().remove(identifier);
		}
	}

	@Override
	public ChoiceModule getNewInstance() {
		return moduleFactory.getChoiceModule();
	}

	@Override
	protected ActivityPresenter<String, ChoiceInteractionBean> getPresenter(){
		return presenter;
	}

	@Override
	protected AbstractResponseModel<String> getResponseModel() {
		return choiceModuleFactory.getChoiceModuleModel(getResponse());
	}

	@Override
	protected AbstractModuleStructure<ChoiceInteractionBean> getStructure() {
		return choiceStructure;
	}

}
