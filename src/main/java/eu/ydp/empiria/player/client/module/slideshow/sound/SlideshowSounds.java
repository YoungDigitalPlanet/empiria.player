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

package eu.ydp.empiria.player.client.module.slideshow.sound;

import com.google.common.collect.Maps;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.media.MediaWrapperCreator;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.media.MimeSourceProvider;
import eu.ydp.empiria.player.client.util.events.internal.callback.CallbackReceiver;

import java.util.Collection;
import java.util.Map;

public class SlideshowSounds {

    private final Map<String, MediaWrapper<Widget>> sounds = Maps.newHashMap();

    private final MediaWrapperCreator mediaWrapperCreator;
    private final MimeSourceProvider mimeSourceProvider;

    @Inject
    public SlideshowSounds(MediaWrapperCreator slideshowMediaWrapperCreator, MimeSourceProvider mimeSourceProvider) {
        this.mediaWrapperCreator = slideshowMediaWrapperCreator;
        this.mimeSourceProvider = mimeSourceProvider;
    }

    public MediaWrapper<Widget> getSound(String audiopath) {
        return sounds.get(audiopath);
    }

    public void initSound(String audiopath) {
        if (!sounds.containsKey(audiopath)) {
            createMediaWrapper(audiopath);
        }
    }

    public boolean containsWrapper(MediaWrapper<Widget> mediaWrapper) {
        return sounds.containsValue(mediaWrapper);
    }

    public Collection<MediaWrapper<Widget>> getAllSounds() {
        return sounds.values();
    }

    private void createMediaWrapper(String audiopath) {
        Map<String, String> sourceWithType = mimeSourceProvider.getSourcesWithTypeByExtension(audiopath);
        CallbackReceiver<MediaWrapper<Widget>> callbackReceiver = createCallbackReceiver(audiopath);

        mediaWrapperCreator.createMediaWrapper(sourceWithType, callbackReceiver);
    }

    private CallbackReceiver<MediaWrapper<Widget>> createCallbackReceiver(final String audiopath) {
        return new CallbackReceiver<MediaWrapper<Widget>>() {

            @Override
            public void setCallbackReturnObject(MediaWrapper<Widget> mediaWrapper) {
                sounds.put(audiopath, mediaWrapper);
            }
        };
    }
}
