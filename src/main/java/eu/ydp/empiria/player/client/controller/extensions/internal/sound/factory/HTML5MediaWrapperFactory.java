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
import eu.ydp.empiria.player.client.gin.factory.MediaWrapperFactory;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration.MediaType;
import eu.ydp.empiria.player.client.module.media.html5.AbstractHTML5MediaWrapper;
import eu.ydp.empiria.player.client.module.object.impl.Media;

public class HTML5MediaWrapperFactory {
    @Inject
    MediaWrapperFactory wrapperFactory;

    public AbstractHTML5MediaWrapper createMediaWrapper(Media media, MediaType mediaType) {
        AbstractHTML5MediaWrapper mediaWrapper;
        if (mediaType == MediaType.VIDEO) {
            mediaWrapper = wrapperFactory.getHtml5VideoMediaWrapper(media);
        } else {
            mediaWrapper = wrapperFactory.getHtml5AudioMediaWrapper(media);
        }
        return mediaWrapper;
    }
}
