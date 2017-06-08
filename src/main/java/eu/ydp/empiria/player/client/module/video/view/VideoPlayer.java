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

package eu.ydp.empiria.player.client.module.video.view;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.module.video.ElementScaler;
import eu.ydp.empiria.player.client.module.video.VideoEndListener;
import eu.ydp.empiria.player.client.module.video.VideoFullscreenListener;
import eu.ydp.empiria.player.client.module.video.VideoPlayerControl;
import eu.ydp.empiria.player.client.module.video.wrappers.VideoElementWrapper;
import eu.ydp.gwtutil.client.util.UserAgentUtil;

import java.util.List;

public class VideoPlayer extends Widget {

    private final VideoPlayerNative nativePlayer;
    private final VideoElementWrapper videoElementWrapper;
    private final UserAgentUtil userAgentUtil;
    private final DivElement divElement;
    private ElementScaler elementScaler;

    @Inject
    public VideoPlayer(@Assisted VideoElementWrapper videoElementWrapper, VideoPlayerNative nativePlayer, UserAgentUtil userAgentUtil) {
        this.nativePlayer = nativePlayer;
        this.videoElementWrapper = videoElementWrapper;
        this.userAgentUtil = userAgentUtil;
        divElement = Document.get().createDivElement();
        setElement(divElement);
    }

    @Override
    protected void onLoad() {
        divElement.appendChild(videoElementWrapper.asNode());

        initializeNativePlayer();

        enablePlayerScaling();
    }

    private void enablePlayerScaling() {
        final Element wrappedElement = divElement.getFirstChildElement();
        final int playerWidth = nativePlayer.getWidth();
        elementScaler = new ElementScaler(wrappedElement);

        elementScaler.setRatio();
        elementScaler.setMaxWidth(playerWidth);
        nativePlayer.addFullscreenListener(new VideoFullscreenListener() {
            @Override
            public void onEnterFullscreen() {
                elementScaler.clearRatio();
                elementScaler.clearMaxWidth();
            }

            @Override
            public void onExitFullscreen() {
                elementScaler.setRatio();
                elementScaler.setMaxWidth(playerWidth);
            }
        });
    }

    private void initializeNativePlayer() {
        String playerId = videoElementWrapper.getId();
        nativePlayer.initPlayer(playerId);

        if (userAgentUtil.isAndroidBrowser() && userAgentUtil.isAIR()) {
            nativePlayer.disablePointerEvents();
        }

        nativePlayer.addVideoEndListener(new VideoEndListener() {
            @Override
            public void onVideoEnd() {
                nativePlayer.stop();
            }
        });
    }

    public VideoPlayerControl getControl() {
        return nativePlayer;
    }

    @Override
    protected void onUnload() {
        nativePlayer.disposeCurrentPlayer();
    }

    public String getId() {
        return videoElementWrapper.getId();
    }

    public List<String> getSources() {
        return videoElementWrapper.getSources();
    }
}
