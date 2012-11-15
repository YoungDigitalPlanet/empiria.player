package eu.ydp.empiria.player.client.module;

import java.util.List;

import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
import eu.ydp.empiria.player.client.controller.events.interaction.StateChangedInteractionEvent;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.gin.PlayerGinjector;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;
import eu.ydp.empiria.player.client.util.events.state.StateChangeEvent;
import eu.ydp.empiria.player.client.util.events.state.StateChangeEventTypes;
import eu.ydp.gwtutil.client.xml.XMLUtils;

public abstract class InteractionModuleBase extends ModuleBase implements IInteractionModule {

	private InteractionEventsListener interactionEventsListener;
	private ModuleSocket moduleSocket;

	private Response response;
	private String responseIdentifier;
	private final EventsBus eventsBus = getEventsBus();


	@Override
	public final void initModule(ModuleSocket moduleSocket, InteractionEventsListener interactionEventsListener) {
		initModule(moduleSocket);
		this.interactionEventsListener = interactionEventsListener;
		this.moduleSocket = moduleSocket;
	}

	@Override
	public final String getIdentifier() {
		return responseIdentifier;
	}

	protected final void setResponseFromElement(Element element){
		responseIdentifier = XMLUtils.getAttributeAsString(element, "responseIdentifier");
		response = findResponse();
	}

	protected Response findResponse(){
		return moduleSocket.getResponse(responseIdentifier);
	}

	protected Response getResponse(){
		return response;
	}

	protected final InteractionEventsListener getInteractionEventsListener() {
		return interactionEventsListener;
	}

	protected void fireStateChanged(boolean userInteract){
		eventsBus.fireEvent(new StateChangeEvent(StateChangeEventTypes.STATE_CHANGED, new StateChangedInteractionEvent(userInteract, this)), new CurrentPageScope());
	}

	protected EventsBus getEventsBus() {
		return PlayerGinjector.INSTANCE.getEventsBus();
	}

	@Override
	public List<IModule> getChildren() {
		return moduleSocket.getChildren(this);
	}
}
