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

package eu.ydp.empiria.player.client.module.audioplayer;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.core.base.ParentedModuleBase;
import eu.ydp.empiria.player.client.module.object.impl.flash.FlashLocalAudioImpl;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.gwtutil.client.xml.XMLUtils;

public class FlashAudioPlayerModule extends ParentedModuleBase implements AudioPlayerModule {

    @Inject
    private FlashLocalAudioImpl view;

    @Override
    public void initModule(Element element, ModuleSocket moduleSocket, EventsBus eventsBus) {
        initModule(moduleSocket);
        String address = XMLUtils.getAttributeAsString(element, "src");
        if (address == null || "".equals(address)) {
            address = XMLUtils.getAttributeAsString(element, "data");
        }

        String[] type = address.split("[.]");
        view.addSrc(address, "audio/" + type[type.length - 1]);

    }

    @Override
    public Widget getView() {
        return view;
    }
}
