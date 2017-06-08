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
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;

public class ExternalFullscreenVideoImpl implements Video {

    private Image poster = new Image();

    public ExternalFullscreenVideoImpl() {
    }

    @Override
    public void addSrc(String src, String type) {
    }

    @Override
    public void setShowNativeControls(boolean show) {
    }

    @Override
    public void setEventBusSourceObject(MediaWrapper<?> object) {
    }

    @Override
    public MediaBase getMedia() {
        return null;
    }

    @Override
    public Widget asWidget() {
        return poster;
    }

    @Override
    public void setWidth(int width) {
        poster.setWidth(width + "px");
    }

    @Override
    public void setHeight(int height) {
        poster.setHeight(height + "px");
    }

    @Override
    public void setPoster(String url) {
        poster.setUrl(url);
    }

}
