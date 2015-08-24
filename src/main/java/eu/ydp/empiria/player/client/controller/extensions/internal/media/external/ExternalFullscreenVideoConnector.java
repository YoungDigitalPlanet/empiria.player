package eu.ydp.empiria.player.client.controller.extensions.internal.media.external;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.gwt.core.client.JsArrayString;
import com.google.inject.Singleton;
import eu.ydp.gwtutil.client.collections.CollectionsUtil;

import java.util.Map;

@Singleton
public class ExternalFullscreenVideoConnector implements FullscreenVideoConnector {

    private Map<String, FullscreenVideoConnectorListener> idToListeners = Maps.newHashMap();

    public ExternalFullscreenVideoConnector() {
        initJs();
    }

    private native void initJs() /*-{
        var instance = this;
        $wnd.empiriaMediaFullscreenVideoOnClose = function (id, currentTimeMillipercents) {
            instance.@eu.ydp.empiria.player.client.controller.extensions.internal.media.external.ExternalFullscreenVideoConnector::onFullscreenClose(Ljava/lang/String;I)(id, currentTimeMillipercents);
        }
    }-*/;

    private void onFullscreenClose(String id, int currentTimeMillipercent) {
        if (idToListeners.containsKey(id)) {
            idToListeners.get(id).onFullscreenClosed(id, currentTimeMillipercent);
        }
    }

    @Override
    public void addConnectorListener(String id, FullscreenVideoConnectorListener listener) {
        Preconditions.checkNotNull(id);
        Preconditions.checkArgument(!id.isEmpty());
        idToListeners.put(id, listener);
    }

    @Override
    public void openFullscreen(String id, Iterable<String> sources, double currentTimePercent) {
        JsArrayString sourcesArray = CollectionsUtil.iterableToJsArray(sources);
        openFullscreenJs(id, sourcesArray, (int) currentTimePercent);
    }

    private native void openFullscreenJs(String id, JsArrayString sources, int currentTimePercent)/*-{
        if (typeof $wnd.empiriaMediaFullscreenVideoOpen == 'function') {
            $wnd.empiriaMediaFullscreenVideoOpen(id, sources, currentTimePercent);
        }
    }-*/;
}
