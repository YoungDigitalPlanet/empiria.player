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

package eu.ydp.empiria.player.client.module.object.impl;

import com.google.gwt.media.client.MediaBase;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;

public class EmbedAudioImpl extends Composite implements Audio {
    HTML audio = null;

    public EmbedAudioImpl() {
        audio = new HTML();
        initWidget(audio);
    }

    public void setSrc(String src) {
        audio.setHTML("<embed src='" + src + "' autostart='false' controller='true'></embed>");
    }

    @Override
    public void setShowNativeControls(boolean show) {
    }

    @Override
    public MediaBase getMedia() {
        return null;
    }

    @Override
    public void addSrc(String src, String type) {
        setSrc(src);
    }

    @Override
    public void setEventBusSourceObject(MediaWrapper<?> object) {

    }
}
