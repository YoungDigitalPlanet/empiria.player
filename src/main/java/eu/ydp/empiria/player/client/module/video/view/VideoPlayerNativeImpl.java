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

import com.google.gwt.core.client.JavaScriptObject;
import eu.ydp.empiria.player.client.module.video.VideoEndListener;
import eu.ydp.empiria.player.client.module.video.VideoFullscreenListener;
import eu.ydp.empiria.player.client.module.video.VideoPlayerControlHandler;

import static com.google.gwt.core.client.GWT.getModuleBaseURL;

public class VideoPlayerNativeImpl implements VideoPlayerNative {

    private static final String FALLBACK_SWF = getModuleBaseURL() + "/videojs/video-js.swf";
    private JavaScriptObject playerObject;
    private String playerId;

    @Override
    public void initPlayer(String playerId) {
        this.playerId = playerId;
        playerObject = initPlayerNative();
    }

    @Override
    public native void play() /*-{
        var player = this.@eu.ydp.empiria.player.client.module.video.view.VideoPlayerNativeImpl::playerObject;

        if (player) {
            player.play();
        }
    }-*/;

    @Override
    public int getWidth() {
        return getWidthNative();
    }

    private native int getWidthNative() /*-{
        var player = this.@eu.ydp.empiria.player.client.module.video.view.VideoPlayerNativeImpl::playerObject;

        if (player) {
            var options = player.options();
            return options.width;
        }
    }-*/;

    @Override
    public void stop() {
        stopNative();
    }

    private native void stopNative() /*-{
        var player = this.@eu.ydp.empiria.player.client.module.video.view.VideoPlayerNativeImpl::playerObject;
        if (player) {
            player.posterImage.show();
            player.exitFullscreen();
            player.currentTime(0);
            player.trigger('loadstart');
        }
    }-*/;

    @Override
    public native void pause() /*-{
        var player = this.@eu.ydp.empiria.player.client.module.video.view.VideoPlayerNativeImpl::playerObject;

        if (player) {
            player.pause();
        }
    }-*/;

    @Override
    public native void setCurrentTime(float position) /*-{
        var player = this.@eu.ydp.empiria.player.client.module.video.view.VideoPlayerNativeImpl::playerObject;

        if (player) {
            player.currentTime(position);
        }
    }-*/;


    @Override
    public native float getCurrentTime() /*-{
        var player = this.@eu.ydp.empiria.player.client.module.video.view.VideoPlayerNativeImpl::playerObject;

        if (player) {
            return player.currentTime();
        }
    }-*/;

    private native void setFlashFallback() /*-{
        $wnd.videojs.options.flash.swf = @eu.ydp.empiria.player.client.module.video.view.VideoPlayerNativeImpl::FALLBACK_SWF;
    }-*/;

    private native boolean isFlashFallback() /*-{
        var objects = $wnd.document
            .getElementById(
            this.@eu.ydp.empiria.player.client.module.video.view.VideoPlayerNativeImpl::playerId)
            .getElementsByTagName('object');

        return ((objects != null) && (objects.length != 0));
    }-*/;

    private native JavaScriptObject initPlayerNative() /*-{
        var playerId = this.@eu.ydp.empiria.player.client.module.video.view.VideoPlayerNativeImpl::playerId;

        var options = $wnd.document.getElementById(playerId).getAttribute(
                'data-setup')
            || '{}';
        options = $wnd.videojs.JSON.parse(options);

        return $wnd.videojs(playerId, options);
    }-*/;

    @Override
    public void disablePointerEvents() {
        disablePointerEventsNative();
    }

    private native void disablePointerEventsNative() /*-{
        var playerId = this.@eu.ydp.empiria.player.client.module.video.view.VideoPlayerNativeImpl::playerId;
        var player = $wnd.document.getElementById(playerId);

        if (player) {
            $wnd.$(player).css("pointer-events", "none");
        }
    }-*/;

    @Override
    public void disposeCurrentPlayer() {
        disposeCurrentPlayerNative();
    }

    private native void disposeCurrentPlayerNative() /*-{
        var playerId = this.@eu.ydp.empiria.player.client.module.video.view.VideoPlayerNativeImpl::playerId;

        if ($wnd.videojs.players[playerId]) {
            $wnd.videojs.players[playerId].dispose();
        }

    }-*/;

    @Override
    public void addPlayHandler(VideoPlayerControlHandler handler) {
        addEventHandler("play", handler);
    }

    @Override
    public void addPauseHandler(VideoPlayerControlHandler handler) {
        addEventHandler("pause", handler);
    }

    @Override
    public void addVideoEndListener(VideoEndListener listener) {
        addVideoEndListenerNative(listener);
    }

    private native void addVideoEndListenerNative(VideoEndListener listener)/*-{
        var player = this.@eu.ydp.empiria.player.client.module.video.view.VideoPlayerNativeImpl::playerObject;
        if (player) {
            player
                .on(
                "ended",
                function () {
                    listener.@eu.ydp.empiria.player.client.module.video.VideoEndListener::onVideoEnd()();
                });
        }
    }-*/;

    @Override
    public void addTimeUpdateHandler(VideoPlayerControlHandler handler) {
        addEventHandler("timeupdate", handler);
    }

    @Override
    public void addLoadStartHandler(VideoPlayerControlHandler handler) {
        addEventHandler("loadstart", handler);
    }

    @Override
    public void addLoadedMetadataHandler(VideoPlayerControlHandler handler) {
        addEventHandler("loadedmetadata", handler);
    }

    @Override
    public void addLoadedDataHandler(VideoPlayerControlHandler handler) {
        addEventHandler("loadeddata", handler);
    }

    @Override
    public void addLoadedAllDataHandler(VideoPlayerControlHandler handler) {
        addEventHandler("loadedalldata", handler);
    }

    @Override
    public void addDurationChangeHandler(VideoPlayerControlHandler handler) {
        addEventHandler("durationchange", handler);
    }

    @Override
    public void addFullscreenListener(VideoFullscreenListener videoFullscreenListener) {
        addFullscreenListenerNative(videoFullscreenListener);
    }

    private native void addFullscreenListenerNative(VideoFullscreenListener listener)/*-{
        var player = this.@eu.ydp.empiria.player.client.module.video.view.VideoPlayerNativeImpl::playerObject;
        if (player) {
            player
                .on(
                "fullscreenchange",
                function () {
                    if (player.isFullscreen()) {
                        listener.@eu.ydp.empiria.player.client.module.video.VideoFullscreenListener::onEnterFullscreen()();
                    } else {
                        listener.@eu.ydp.empiria.player.client.module.video.VideoFullscreenListener::onExitFullscreen()();
                    }
                });
        }
    }-*/;

    private native void addEventHandler(String event, VideoPlayerControlHandler handler) /*-{
        var player = this.@eu.ydp.empiria.player.client.module.video.view.VideoPlayerNativeImpl::playerObject;
        var javaPlayer = this;

        if (player) {
            player
                .on(
                event,
                function () {
                    handler.@eu.ydp.empiria.player.client.module.video.VideoPlayerControlHandler::handle(Leu/ydp/empiria/player/client/module/video/VideoPlayerControl;)(javaPlayer);
                });
        }
    }-*/;
}
