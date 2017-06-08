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

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.module.dictionary.external.view.ExplanationView;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.internal.callback.CallbackReceiver;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventHandler;

public class ExplanationDescriptionSoundController {

    private final DescriptionSoundController descriptionSoundController;

    private final ExplanationView explanationView;

    @Inject
    public ExplanationDescriptionSoundController(@Assisted ExplanationView explanationView,
                                                 DescriptionSoundController descriptionSoundController) {
        this.explanationView = explanationView;
        this.descriptionSoundController = descriptionSoundController;
    }

    public void playOrStopExplanationSound(String filename) {
        if (descriptionSoundController.isPlaying()) {
            stop();
        } else {
            descriptionSoundController.createMediaWrapper(filename, getCallbackReceiver());
        }
    }

    private CallbackReceiver<MediaWrapper<Widget>> getCallbackReceiver() {
        return new CallbackReceiver<MediaWrapper<Widget>>() {

            @Override
            public void setCallbackReturnObject(MediaWrapper<Widget> mw) {
                onExplanationMediaWrapperCallback(mw);
            }
        };
    }

    private void onExplanationMediaWrapperCallback(MediaWrapper<Widget> mw) {
        explanationView.setExplanationPlayButtonStyle();
        MediaEventHandler handler = createExplanationSoundMediaHandler();
        descriptionSoundController.playFromMediaWrapper(handler, mw);
    }

    private MediaEventHandler createExplanationSoundMediaHandler() {
        return new MediaEventHandler() {
            @Override
            public void onMediaEvent(MediaEvent event) {
                explanationView.setExplanationStopButtonStyle();
                descriptionSoundController.stopPlaying();
            }
        };
    }

    public void stop() {
        descriptionSoundController.stopPlaying();
        explanationView.setExplanationStopButtonStyle();
        descriptionSoundController.stopMediaWrapper();
    }
}
