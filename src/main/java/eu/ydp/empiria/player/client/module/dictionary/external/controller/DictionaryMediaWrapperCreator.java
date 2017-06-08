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
import eu.ydp.empiria.player.client.module.dictionary.external.DictionaryMimeSourceProvider;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.callback.CallbackReceiver;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;

import java.util.Map;

public class DictionaryMediaWrapperCreator {

    @Inject
    private DictionaryMimeSourceProvider dictionaryMimeSourceProvider;

    @Inject
    private EventsBus eventsBus;

    public void create(String filePath, CallbackReceiver<MediaWrapper<Widget>> callback) {
        Map<String, String> sourcesWithTypes = dictionaryMimeSourceProvider.getSourcesWithTypes(filePath);

        BaseMediaConfiguration bmc = new BaseMediaConfiguration(sourcesWithTypes, false, true);
        eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.CREATE_MEDIA_WRAPPER, bmc, callback));
    }
}
