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

package eu.ydp.empiria.player.client.module.dictionary.external.controller;

import com.google.common.base.Strings;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.media.MediaWrapperController;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.callback.CallbackReceiver;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;

import static eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes.*;

public class DescriptionSoundController {

    private boolean playing;
    private final MediaWrapperController mediaWrapperController;
    private MediaWrapper<Widget> mediaWrapper;
    private final DictionaryMediaWrapperCreator mediaWrapperCreator;
    private final EventsBus eventsBus;
    private final PageScopeFactory pageScopeFactory;

    @Inject
    public DescriptionSoundController(MediaWrapperController mediaWrapperController,
                                      DictionaryMediaWrapperCreator mediaWrapperCreator,
                                      EventsBus eventsBus,
                                      PageScopeFactory pageScopeFactory) {
        this.mediaWrapperController = mediaWrapperController;
        this.mediaWrapperCreator = mediaWrapperCreator;
        this.eventsBus = eventsBus;
        this.pageScopeFactory = pageScopeFactory;
    }

    public void createMediaWrapper(String fileName, CallbackReceiver<MediaWrapper<Widget>> callbackReceiver) {
        if (!Strings.isNullOrEmpty(fileName)) {
            mediaWrapperCreator.create(fileName, callbackReceiver);
        }
    }

    public boolean isPlaying() {
        return playing;
    }

    public void playFromMediaWrapper(MediaEventHandler mediaEventHandler, MediaWrapper<Widget> mediaWrapper) {
        this.mediaWrapper = mediaWrapper;
        addMediaHandlers(mediaEventHandler);
        playing = true;
        mediaWrapperController.stopAndPlay(mediaWrapper);
    }

    private void addMediaHandlers(MediaEventHandler handler) {
        MediaEventTypes[] eventTypes = {ON_PAUSE, ON_END, ON_STOP};
        addMediaHandlers(eventTypes, handler);
    }

    private void addMediaHandlers(MediaEventTypes[] types, MediaEventHandler handler) {
        for (MediaEventTypes eventType : types) {
            eventsBus.addHandlerToSource(MediaEvent.getType(eventType), mediaWrapper, handler, pageScopeFactory.getCurrentPageScope());
        }
    }

    public void stopPlaying() {
        playing = false;
    }

    public void stopMediaWrapper() {
        mediaWrapperController.stop(mediaWrapper);
    }

}
