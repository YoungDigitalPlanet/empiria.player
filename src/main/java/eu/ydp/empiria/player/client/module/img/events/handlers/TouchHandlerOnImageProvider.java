package eu.ydp.empiria.player.client.module.img.events.handlers;

import com.google.inject.Inject;
import com.google.inject.Provider;
import eu.ydp.gwtutil.client.util.UserAgentUtil;

public class TouchHandlerOnImageProvider implements Provider<ITouchHandlerOnImageInitializer> {

    @Inject
    private UserAgentUtil userAgentUtil;

    @Inject
    private PointerHandlersOnImageInitializer pointerHandlersOnImageInitializer;

    @Inject
    private TouchHandlersOnImageInitializer touchHandlersOnImageInitializer;

    @Override
    public ITouchHandlerOnImageInitializer get() {
        return userAgentUtil.isIE() ? pointerHandlersOnImageInitializer : touchHandlersOnImageInitializer;
    }
}
