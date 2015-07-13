package eu.ydp.empiria.player.client.module.object;

import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.media.MediaWrappersPair;
import eu.ydp.empiria.player.client.util.events.internal.callback.CallbackReceiver;

public class MediaWrapperHandler implements CallbackReceiver {
    private final ObjectModule objectModule;

    public MediaWrapperHandler(ObjectModule objectModule) {
        this.objectModule = objectModule;
    }

    @Override
    public void setCallbackReturnObject(Object object) {
        if (object instanceof MediaWrapper<?>) {
            this.objectModule.createMedia((MediaWrapper<?>) object, null);
        }

        if (object instanceof MediaWrappersPair) {
            this.objectModule.createMedia(((MediaWrappersPair) object).getDefaultMediaWrapper(), ((MediaWrappersPair) object).getFullScreanMediaWrapper());
        }
    }
}
