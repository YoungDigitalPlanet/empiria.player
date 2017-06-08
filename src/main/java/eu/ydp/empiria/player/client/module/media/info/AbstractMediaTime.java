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

package eu.ydp.empiria.player.client.module.media.info;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.media.button.AbstractMediaButton;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;

public abstract class AbstractMediaTime extends AbstractMediaButton {
    NumberFormat formatter = NumberFormat.getFormat("##00");
    @Inject
    protected EventsBus eventsBus;

    public AbstractMediaTime(String baseStyleName) {
        super(baseStyleName);
    }

    /**
     * zwraca tekst w formacie time1:time2 format liczb ##00
     *
     * @param time1
     * @param time2
     * @return
     */
    protected String getInnerText(double time1, double time2) {
        StringBuilder innerText = new StringBuilder(formatter.format(time1));
        innerText.append(":");
        innerText.append(formatter.format(time2));
        return innerText.toString();
    }

    @Override
    protected void onClick() {
    }

    @Override
    public boolean isSupported() {
        return getMediaAvailableOptions().isMediaMetaAvailable() && getMediaAvailableOptions().isSeekSupported();
    }

}
