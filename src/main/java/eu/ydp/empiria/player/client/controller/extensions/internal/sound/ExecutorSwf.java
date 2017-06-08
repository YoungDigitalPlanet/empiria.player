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

package eu.ydp.empiria.player.client.controller.extensions.internal.sound;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.gwtflashmedia.client.FlashMedia;
import eu.ydp.empiria.gwtflashmedia.client.event.*;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.SwfMediaWrapper;
import eu.ydp.empiria.player.client.gin.factory.TextTrackFactory;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.SourceUtil;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;

public abstract class ExecutorSwf implements MediaExecutor<Widget> {
    protected BaseMediaConfiguration baseMediaConfiguration;
    protected MediaWrapper<Widget> mediaWrapper = null;
    protected SoundExecutorListener soundExecutorListener;
    protected FlashMedia flashMedia;
    protected boolean pause;
    protected boolean playing = false;
    protected String source = null;
    @Inject
    protected EventsBus eventsBus;
    @Inject
    protected TextTrackFactory textTrackFactory;

    @Override
    public void init() {
        // Mapujemy wszystkie eventy flasha na mediaevent
        HasFlashMediaHandlers hasFlashMediaHandlers = (HasFlashMediaHandlers) this.flashMedia;
        hasFlashMediaHandlers.addFlashMediaPlayHandler(new FlashMediaPlayHandler() {
            @Override
            public void onFlashSoundPlay(FlashMediaPlayEvent event) {
                if (soundExecutorListener != null) {
                    soundExecutorListener.onPlay();
                }
                playing = true;
                eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.ON_PLAY, getMediaWrapper()), getMediaWrapper());
            }
        });

        hasFlashMediaHandlers.addFlashMediaCompleteHandler(new FlashMediaCompleteHandler() {
            @Override
            public void onFlashSoundComplete(FlashMediaCompleteEvent event) {
                if (soundExecutorListener != null) {
                    soundExecutorListener.onSoundFinished();
                }
                eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.ENDED, getMediaWrapper()), getMediaWrapper());
            }
        });

        hasFlashMediaHandlers.addFlashMediaLoadedHandler(new FlashMediaLoadedHandler() {
            @Override
            public void onFlashSoundLoaded(FlashMediaLoadedEvent event) {
                eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.ON_DURATION_CHANGE, getMediaWrapper()), getMediaWrapper());
            }
        });

        hasFlashMediaHandlers.addFlashMediaMuteChangeHandler(new FlashMediaMuteChangeHandler() {
            @Override
            public void onFlashSoundMuteChange(FlashMediaMuteChangeEvent event) {
                eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.ON_VOLUME_CHANGE, getMediaWrapper()), getMediaWrapper());
            }
        });

        hasFlashMediaHandlers.addFlashMediaVolumeChangeHandler(new FlashMediaVolumeChangeHandler() {
            @Override
            public void onFlashSoundVolumeChange(FlashMediaVolumeChangeEvent event) {
                eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.ON_VOLUME_CHANGE, getMediaWrapper()), getMediaWrapper());
            }
        });
        hasFlashMediaHandlers.addFlashMediaLoadErrorHandler(new FlashMediaLoadErrorHandler() {
            @Override
            public void onFlashSoundLoadError(FlashMediaLoadErrorEvent event) {
                eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.ERROR, getMediaWrapper()), getMediaWrapper());
            }
        });
        hasFlashMediaHandlers.addFlashMediaMetadataHandler(new FlashMediaMetadataHandler() {
            @Override
            public void onFlashMediaMetadataEvent(FlashMediaMetadataEvent event) {
                eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.ON_DURATION_CHANGE, getMediaWrapper()), getMediaWrapper());
            }
        });

        hasFlashMediaHandlers.addFlashMediaPauseHandler(new FlashMediaPauseHandler() {
            @Override
            public void onFlashSoundPause(FlashMediaPauseEvent event) {
                eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.ON_PAUSE, getMediaWrapper()), getMediaWrapper());
            }
        });

        hasFlashMediaHandlers.addFlashMediaStopHandler(new FlashMediaStopHandler() {
            @Override
            public void onFlashSoundStop(FlashMediaStopEvent event) {
                eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.ON_STOP, getMediaWrapper()), getMediaWrapper());
            }
        });
        hasFlashMediaHandlers.addFlashMediaPositionChangeHandler(new FlashMediaPlayheadUpdateHandler() {

            @Override
            public void onFlashSoundPositionChange(FlashMediaPlayheadUpdateEvent event) {
                MediaEvent mediaEvent = new MediaEvent(MediaEventTypes.ON_TIME_UPDATE, getMediaWrapper());
                mediaEvent.setCurrentTime(event.getPlayheadTime() * .01d);
                eventsBus.fireAsyncEventFromSource(mediaEvent, getMediaWrapper());
            }
        });
        if (mediaWrapper != null) {
            hasFlashMediaHandlers.addFlashMediaPositionChangeHandler((FlashMediaPlayheadUpdateHandler) mediaWrapper);
            hasFlashMediaHandlers.addFlashMediaMetadataHandler((FlashMediaMetadataHandler) mediaWrapper);
            hasFlashMediaHandlers.addFlashMediaMuteChangeHandler((FlashMediaMuteChangeHandler) mediaWrapper);
            hasFlashMediaHandlers.addFlashMediaVolumeChangeHandler((FlashMediaVolumeChangeHandler) mediaWrapper);
            hasFlashMediaHandlers.addFlashMediaStopHandler((FlashMediaStopHandler) mediaWrapper);
            hasFlashMediaHandlers.addFlashMediaLoadedHandler((FlashMediaLoadedHandler) mediaWrapper);
            ((SwfMediaWrapper) mediaWrapper).setFlashMedia(this.flashMedia);
        }

    }

    @Override
    public void setBaseMediaConfiguration(BaseMediaConfiguration baseMediaConfiguration) {
        this.baseMediaConfiguration = baseMediaConfiguration;
        source = SourceUtil.getMpegSource(baseMediaConfiguration.getSources());
    }

    @Override
    public BaseMediaConfiguration getBaseMediaConfiguration() {
        return baseMediaConfiguration;
    }

    @Override
    public void setMediaWrapper(MediaWrapper<Widget> descriptor) {
        this.mediaWrapper = descriptor;
    }

    @Override
    public MediaWrapper<Widget> getMediaWrapper() {
        return mediaWrapper;
    }

    @Override
    public void setSoundFinishedListener(SoundExecutorListener listener) {
        this.soundExecutorListener = listener;
    }

    @Override
    public void setMuted(boolean mute) {
        flashMedia.setMute(mute);
    }

    @Override
    public void setVolume(double volume) {
        flashMedia.setVolume((int) (volume * 100));
    }

    @Override
    public void setCurrentTime(double time) {
        flashMedia.setPlayheadTime((int) (time * 1000));
    }

    @Override
    public void play(String src) {
        play(src, true);
    }

    @Override
    public void play() {
        play(source, false);
    }

    private void play(String src, boolean free) {
        if (flashMedia != null && free) {
            free();
        }
        source = src;
        if (playing && !pause) {
            stop();
        }
        if (flashMedia == null) {
            init();
        }
        flashMedia.play();

    }

    @Override
    public void playLooped() {
    }

    @Override
    public void pause() {
        pause = true;
        try {
            flashMedia.pause();
        } catch (Exception exception) {// NOPMD
        }
    }

    @Override
    public void resume() {
    }

    private void free() {
        if (flashMedia != null) {
            flashMedia.free();
            flashMedia = null;
        }
    }
}
