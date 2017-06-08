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

package eu.ydp.empiria.player.client.controller.extensions.jswrappers;

import com.google.gwt.core.client.JavaScriptObject;
import eu.ydp.empiria.player.client.module.media.MediaAvailableOptions;

/**
 * Overlay type dla konfiguracji w formacie JSON
 */
public class JsMediaAvaliableOptions extends JavaScriptObject implements MediaAvailableOptions {

    protected JsMediaAvaliableOptions() {

    }

    @Override
    public native final boolean isPlaySupported() /*-{
        return this.playSupported == true;
    }-*/;

    @Override
    public native final boolean isPauseSupported() /*-{
        return this.pauseSupported == true;
    }-*/;

    @Override
    public native final boolean isMuteSupported() /*-{
        return this.muteSupported == true;
    }-*/;

    @Override
    public native final boolean isVolumeChangeSupported() /*-{
        return this.volumeChangeSupported == true;
    }-*/;

    @Override
    public native final boolean isStopSupported() /*-{
        return this.stopSupported == true;
    }-*/;

    @Override
    public native final boolean isSeekSupported() /*-{
        return this.seekSupported == true;
    }-*/;

    @Override
    public native final boolean isFullScreenSupported() /*-{
        return this.fullScreenSupported == true;
    }-*/;

    @Override
    public native final boolean isMediaMetaAvailable() /*-{
        return this.mediaMetaAvailable == true;
    }-*/;

    @Override
    public native final boolean isTemplateSupported() /*-{
        return this.templateSupported == true;
    }-*/;
}
