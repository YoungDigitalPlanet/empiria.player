package eu.ydp.empiria.player.client.module.choice;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.choice.ChoiceModuleEvent;
import eu.ydp.empiria.player.client.util.events.choice.ChoiceModuleEventHandler;
import eu.ydp.empiria.player.client.util.events.choice.ChoiceModuleEventType;

public class ChoiceModuleListener implements ChoiceModuleEventHandler {
	private final ChoiceModule choiceModule;
	private final EventsBus eventBus;
	private final PageScopeFactory pageScopeFactory;

	@Inject
	public ChoiceModuleListener(EventsBus eventsBus, PageScopeFactory pageScopeFactory, @Assisted ChoiceModule choiceModule) {
		this.choiceModule = choiceModule;
		this.eventBus = eventsBus;
		this.pageScopeFactory = pageScopeFactory;
	}

	@Override
	public void onChoiceModuleEvent(ChoiceModuleEvent event) {
		if (event.getType().equals(ChoiceModuleEventType.ON_CHOICE_CLICK)) {
			choiceModule.onSimpleChoiceClick(event.getChoiceIdentifier());
		}
	}

	public void addEventHandler(ChoiceModuleEventType type) {
		eventBus.addHandler(ChoiceModuleEvent.getType(type), this, pageScopeFactory.getCurrentPageScope());
	}

}
