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
import com.google.gwt.user.client.ui.FlowPanel;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;

public class HTML5AudioImpl extends FlowPanel implements Audio {
    eu.ydp.empiria.player.client.media.Audio audio = null;

    public HTML5AudioImpl() {
        audio = new eu.ydp.empiria.player.client.media.Audio();
        add(audio);
    }

    public void setSrc(String src) {
        audio.setSrc(src);
    }

    @Override
    public void setShowNativeControls(boolean show) {
        audio.setControls(show);
    }

    @Override
    public MediaBase getMedia() {
        return audio;
    }

    @Override
    public void addSrc(String src, String type) {
        audio.addSource(src, type);
    }

    @Override
    public void setEventBusSourceObject(MediaWrapper<?> object) {

    }
}
