package eu.ydp.empiria.player.client.module;

import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
import eu.ydp.empiria.player.client.controller.events.interaction.StateChangedInteractionEvent;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.util.xml.XMLUtils;

public abstract class InteractionModuleBase extends ModuleBase implements IInteractionModule {

	private InteractionEventsListener interactionEventsListener;
	private ModuleSocket moduleSocket;
	
	private Response response;
	private String responseIdentifier;

	@Override
	public final void initModule(ModuleSocket moduleSocket, InteractionEventsListener interactionEventsListener) {
		this.interactionEventsListener = interactionEventsListener;
		this.moduleSocket = moduleSocket;
	}

	@Override
	public final String getIdentifier() {
		return responseIdentifier;
	}
		
	protected final void findResponse(Element element){
		responseIdentifier = XMLUtils.getAttributeAsString(element, "responseIdentifier");
		response = moduleSocket.getResponse(responseIdentifier);
	}
	
	protected final Response getResponse(){
		return response;
	}
	protected final InteractionEventsListener getInteractionEventsListener() {
		return interactionEventsListener;
	}

	protected final ModuleSocket getModuleSocket() {
		return moduleSocket;
	}
	
	protected void fireStateChanged(boolean userInteract){
		getInteractionEventsListener().onStateChanged(new StateChangedInteractionEvent(userInteract, this));
	}

}
