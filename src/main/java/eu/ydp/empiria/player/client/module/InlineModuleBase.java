package eu.ydp.empiria.player.client.module;

import com.google.gwt.xml.client.Element;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;

public abstract class InlineModuleBase extends SingleViewModuleBase implements IInlineModule {

    private InteractionEventsListener interactionEventsListener;

    @Override
    public final void initModule(Element element, ModuleSocket moduleSocket, InteractionEventsListener iel) {
        interactionEventsListener = iel;
        super.initModuleInternal(element, moduleSocket);
    }

    protected InteractionEventsListener getInteractionEventsListener() {
        return interactionEventsListener;
    }

}
