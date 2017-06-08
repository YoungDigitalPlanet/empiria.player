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

package eu.ydp.empiria.player.client.controller.extensions.internal.sound.factory;

import com.google.inject.Inject;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.html5.AbstractHTML5MediaExecutor;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.html5.HTML5AudioMediaExecutor;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.html5.HTML5VideoMediaExecutor;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration.MediaType;
import eu.ydp.empiria.player.client.module.media.html5.AbstractHTML5MediaWrapper;
import eu.ydp.empiria.player.client.module.object.impl.Media;

public class HTML5MediaExecutorFactory {

    @Inject
    private HTML5MediaWrapperFactory mediaWrapperFactory;

    @Inject
    private Provider<HTML5VideoMediaExecutor> videoExecutorProvider;

    @Inject
    private Provider<HTML5AudioMediaExecutor> audioExecutorProvider;

    public AbstractHTML5MediaExecutor createMediaExecutor(Media media, MediaType mediaType) {
        AbstractHTML5MediaExecutor executor = null;
        if (media != null) {
            AbstractHTML5MediaWrapper html5MediaWrapper = mediaWrapperFactory.createMediaWrapper(media, mediaType);
            executor = getMediaExecutor(mediaType);
            executor.setMediaWrapper(html5MediaWrapper);
            media.setEventBusSourceObject(executor.getMediaWrapper());
        }
        return executor;
    }

    private AbstractHTML5MediaExecutor getMediaExecutor(MediaType mediaType) {
        return (mediaType == MediaType.VIDEO) ? videoExecutorProvider.get() : audioExecutorProvider.get();
    }

}
