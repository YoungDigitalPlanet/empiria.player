package eu.ydp.empiria.player.client.module.choice;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.gin.PlayerGinjector;
import eu.ydp.empiria.player.client.module.AbstractInteractionModule;
import eu.ydp.empiria.player.client.module.ActivityPresenter;
import eu.ydp.empiria.player.client.module.choice.structure.ChoiceModuleStructure;
import eu.ydp.empiria.player.client.module.choice.structure.ChoiceOption;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.choice.ChoiceModuleEvent;
import eu.ydp.empiria.player.client.util.events.choice.ChoiceModuleEventHandler;
import eu.ydp.empiria.player.client.util.events.choice.ChoiceModuleEventType;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;

public class ChoiceModule extends AbstractInteractionModule<ChoiceModule> {
	
	private ChoiceModulePresesenter presenter;
	
	private ChoiceModuleStructure choiceStructure;

	@Override
	protected void initalizeModule() {
		choiceStructure = new ChoiceModuleStructure();
		choiceStructure.createFromXml(getModuleElement().toString());
		
		choiceStructure.setMulti(isMulti());
		
		presenter.setInlineBodyGenerator(getModuleSocket().getInlineBodyGeneratorSocket());
		presenter.setPrompt(choiceStructure.getPrompt());
		presenter.setChoices(choiceStructure.getChoiceOptions());
		
		addListeners();
	}
	
	private boolean isMulti(){
		return getResponse().cardinality == Cardinality.MULTIPLE;
	}
	
	private void addListeners(){
		ChoiceModuleListener listener = new ChoiceModuleListener();
		listener.addEventHandler(ChoiceModuleEventType.ON_CHOICE_CLICK);
	}
	
	@Override
	protected void initializeAndInstallFeedbacks() {
		super.initializeAndInstallFeedbacks();
		
		//TODO: rewrite to JAXB
		for(ChoiceOption choiceOption: choiceStructure.getChoiceOptions()){
			String identifier = choiceOption.getIdentifier();
			Element feedbackNode = choiceStructure.getSimpleChoiceFeedbackElement(identifier);
			
			if(feedbackNode != null){
				Widget feedbackPlaceholder = presenter.getFeedbackPlaceholderByIdentifier(identifier);
				createInlineFeedback(feedbackPlaceholder, feedbackNode);
			}
		}
	}
	
	public void onSimpleChoiceClick(String identifier) {
		if (!locked){
			presenter.switchChoiceSelection(identifier);
			updateResponse(identifier);
			fireStateChanged(true);
		}
	}
	
	private void updateResponse(String identifier){
		boolean selected = presenter.isChoiceSelected(identifier);
		
		if(selected){
			getResponse().add(identifier);
		}else{
			getResponse().remove(identifier);
		}
	}

	@Override
	public ChoiceModule getNewInstance() {
		return new ChoiceModule();
	}

	@Override
	protected ActivityPresenter createPresenter() {
		if(presenter == null){
			presenter = new ChoiceModulePresenterImpl();
		}
		return presenter;
	}
	
	private class ChoiceModuleListener implements ChoiceModuleEventHandler{
		private final EventsBus eventBus = PlayerGinjector.INSTANCE.getEventsBus();
		
		@Override
		public void onChoiceModuleEvent(ChoiceModuleEvent event) {
			if(event.getType().equals(ChoiceModuleEventType.ON_CHOICE_CLICK)){
				onSimpleChoiceClick(event.getChoiceIdentifier());
			}
		}
		
		public void addEventHandler(ChoiceModuleEventType type){
			eventBus.addHandler(ChoiceModuleEvent.getType(type), this, new CurrentPageScope());
		}
		
	}
	
}
