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

package eu.ydp.empiria.player.client.controller.extensions.internal.media;

import com.google.gwt.user.client.ui.Widget;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration.MediaType;
import eu.ydp.empiria.player.client.module.object.impl.EmbedAudioImpl;
import eu.ydp.empiria.player.client.module.object.impl.flash.FlashVideoImpl;
import eu.ydp.empiria.player.client.util.SourceUtil;

public class OldSwfMediaExecutor extends AbstractNoControlExecutor {
    @Override
    public void init() {
        Widget widget = null;
        if (bmc.getMediaType() == MediaType.AUDIO) {
            EmbedAudioImpl audop = new EmbedAudioImpl();
            audop.setSrc(SourceUtil.getMpegSource(bmc.getSources()));
            widget = audop;
        } else {
            FlashVideoImpl video = new FlashVideoImpl();
            video.setSrc(SourceUtil.getMpegSource(bmc.getSources()));
            widget = video;
        }
        ((OldSwfMediaWrapper) mediaWrapper).setMediaWidget(widget);

    }

}
