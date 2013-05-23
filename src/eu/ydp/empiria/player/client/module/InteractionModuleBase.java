package eu.ydp.empiria.player.client.module;

import java.util.List;

import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
import eu.ydp.empiria.player.client.controller.events.interaction.StateChangedInteractionEvent;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
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

	@Inject
	private EventsBus eventsBus;
	
	@Inject @PageScoped
	private ResponseSocket responseSocket;


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
		return responseSocket.getResponse(responseIdentifier);
	}

	protected Response getResponse(){
		return response;
	}

	protected final InteractionEventsListener getInteractionEventsListener() {
		return interactionEventsListener;
	}

	protected void fireStateChanged(boolean userInteract, boolean isReset){
		eventsBus.fireEvent(new StateChangeEvent(StateChangeEventTypes.STATE_CHANGED, new StateChangedInteractionEvent(userInteract, isReset, this)), new CurrentPageScope());
	}

	@Override
	public List<IModule> getChildren() {
		return moduleSocket.getChildren(this);
	}
}
