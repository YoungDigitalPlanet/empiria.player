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

package eu.ydp.empiria.player.client.module.object.impl.flash;

import com.google.gwt.dom.client.Document;
import com.google.gwt.media.client.MediaBase;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.object.impl.Video;

public class FlashVideoImpl extends Composite implements Video {

    protected String elementId;
    protected String src;

    public FlashVideoImpl() {
        elementId = Document.get().createUniqueId();
        HTML html = new HTML("<div id='" + elementId + "' class='qp-video'></div>");

        initWidget(html);
    }

    public void setSrc(String src) {
        this.src = src;
    }

    @Override
    public void onLoad() {
        initFAV(elementId, src);
    }

    private native void initFAV(String elementId, String src)/*-{
        if (typeof $wnd.FAVideo == 'function')
            var vp = new $wnd.FAVideo(elementId, src, 0, 0, {
                autoLoad: true,
                autoPlay: false
            });
    }-*/;

    @Override
    public void addSrc(String src, String type) {
        setSrc(src);
    }

    @Override
    public void setWidth(int width) {
    }

    @Override
    public void setHeight(int height) {
    }

    @Override
    public void setPoster(String url) {
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

}
