/*
 * Copyright 2017 Young Digital Planet S.A.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
