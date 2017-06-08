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
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.object.impl.Audio;

public class FlashLocalAudioImpl extends FlashLocalMediaImpl implements Audio {

    public FlashLocalAudioImpl() {
        super("audio");
    }

    @Override
    protected native void loadFlvPlayerThroughSwfobject(String id, String swfSrc, String installSrc, String mediaSrc, int width, int height) /*-{
        var flashvars = {soundFile: mediaSrc, playerID: id, animation: "no", noinfo: "yes"};
        $wnd.swfobject.embedSWF(swfSrc, id, width, height, "9", installSrc, flashvars);

    }-*/;

    @Override
    protected String getSwfSrc() {
        return GWT.getModuleBaseURL() + "wpaudioplayer/wpaudioplayer.swf";
    }

    @Override
    public void addSrc(String src, String type) {
        if (this.src == null) {
            setSrc(src);
        }
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
        return 160;
    }

    @Override
    protected int getHeight() {
        return 24;
    }

}
