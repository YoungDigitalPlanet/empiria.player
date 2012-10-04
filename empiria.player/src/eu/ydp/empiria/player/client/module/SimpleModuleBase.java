package eu.ydp.empiria.player.client.module;

import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;

public abstract class SimpleModuleBase extends SingleViewModuleBase implements ISimpleModule {

	private InteractionEventsListener interactionEventsListener;
	
	@Override
	public final void initModule(Element element, ModuleSocket ms, InteractionEventsListener iel) {		
		interactionEventsListener = iel;
		super.initModuleInternal(element, ms);
	}
	protected InteractionEventsListener getInteractionEventsListener(){
		return interactionEventsListener;
	}


}
