package eu.ydp.empiria.player.client.module;

import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
import eu.ydp.empiria.player.client.controller.events.interaction.StateChangedInteractionEvent;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;
import eu.ydp.empiria.player.client.util.events.state.StateChangeEvent;
import eu.ydp.empiria.player.client.util.events.state.StateChangeEventTypes;
import eu.ydp.gwtutil.client.xml.XMLUtils;

import java.util.List;

public abstract class InteractionModuleBase extends ModuleBase implements IInteractionModule, WorkModeClient, IIgnored {

	private InteractionEventsListener interactionEventsListener;
	private ModuleSocket moduleSocket;

	private Response response;
	private String responseIdentifier;

	@Inject
	private EventsBus eventsBus;

	@Inject
	private Provider<CurrentPageScope> providerCurrentPageScope;

	@Inject
	@PageScoped
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

	protected Response getResponse() {
		return response;
	}

	protected final void setResponseFromElement(Element element) {
		responseIdentifier = XMLUtils.getAttributeAsString(element, "responseIdentifier");
		response = findResponse();
	}

	protected Response findResponse() {
		return responseSocket.getResponse(responseIdentifier);
	}

	protected final InteractionEventsListener getInteractionEventsListener() {
		return interactionEventsListener;
	}

	protected void fireStateChanged(boolean userInteract, boolean isReset) {
		eventsBus.fireEvent(new StateChangeEvent(StateChangeEventTypes.STATE_CHANGED, new StateChangedInteractionEvent(userInteract, isReset, this)),
				providerCurrentPageScope.get());
	}

	@Override
	public List<IModule> getChildren() {
		return moduleSocket.getChildren(this);
	}

	@Override
	public void enablePreviewMode() {
		lock(true);
	}

	@Override
	public boolean isIgnored() {
		return false;
	}
}
