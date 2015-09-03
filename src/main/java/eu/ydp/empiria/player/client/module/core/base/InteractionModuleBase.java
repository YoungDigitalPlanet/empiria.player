package eu.ydp.empiria.player.client.module.core.base;

import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.events.interaction.StateChangedInteractionEvent;
import eu.ydp.empiria.player.client.controller.item.ResponseSocket;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.workmode.WorkModePreviewClient;
import eu.ydp.empiria.player.client.controller.workmode.WorkModeTestSubmittedClient;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;
import eu.ydp.empiria.player.client.module.core.flow.Ignored;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.state.StateChangeEvent;
import eu.ydp.empiria.player.client.util.events.internal.state.StateChangeEventTypes;
import eu.ydp.gwtutil.client.xml.XMLUtils;

import java.util.List;

public abstract class InteractionModuleBase extends ModuleBase implements IInteractionModule, WorkModePreviewClient, WorkModeTestSubmittedClient, Ignored {

    private Response response;
    private String responseIdentifier;

    @Inject
    private EventsBus eventsBus;

    @Inject
    private PageScopeFactory pageScopeFactory;

    @Inject
    @PageScoped
    private ResponseSocket responseSocket;

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

    protected void fireStateChanged(boolean userInteract, boolean isReset) {
        eventsBus.fireEvent(new StateChangeEvent(StateChangeEventTypes.STATE_CHANGED, new StateChangedInteractionEvent(userInteract, isReset, this)),
                pageScopeFactory.getCurrentPageScope());
    }

    @Override
    public List<IModule> getChildren() {
        return getModuleSocket().getChildren(this);
    }

    @Override
    public void enablePreviewMode() {
        lock(true);
    }

    @Override
    public void enableTestSubmittedMode() {
        lock(true);
    }

    @Override
    public void disableTestSubmittedMode() {
        lock(false);
    }

    @Override
    public boolean isIgnored() {
        return false;
    }
}
