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

import com.google.gwt.core.client.GWT;
import com.google.gwt.media.client.MediaBase;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.object.impl.Video;

public class FlashLocalVideoImpl extends FlashLocalMediaImpl implements Video {

    private final BaseMediaConfiguration baseMediaConfiguration;

    public FlashLocalVideoImpl(BaseMediaConfiguration baseMediaConfiguration) {
        super("video");
        this.baseMediaConfiguration = baseMediaConfiguration;
    }

    @Override
    protected native void loadFlvPlayerThroughSwfobject(String id, String swfSrc, String installSrc, String videoSrc, int width, int height)/*-{
        var flashvars = {
            video: videoSrc,
            sizeMode: "1"
        };
        var params = {
            allowFullScreen: true
        };
        $wnd.swfobject.embedSWF(swfSrc, id, width, height, "9", installSrc,
            flashvars, params);
    }-*/;

    @Override
    protected String getSwfSrc() {
        return GWT.getModuleBaseURL() + "flvplayer/flvplayer.swf";
    }

    @Override
    public void setWidth(int width) {
    }

    @Override
    public void setHeight(int height) {
    }

    @Override
    public void addSrc(String src, String type) {
        if (this.src == null) {
            setSrc(src);
        }
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

    @Override
    protected int getWidth() {
        return baseMediaConfiguration.getWidth();
    }

    @Override
    protected int getHeight() {
        return baseMediaConfiguration.getHeight();
    }

}
