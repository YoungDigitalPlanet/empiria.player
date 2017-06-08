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

package eu.ydp.empiria.player.client.module.media.button;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.IsWidget;
import eu.ydp.empiria.player.client.module.media.MediaAvailableOptions;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;

public interface MediaController extends SupportedAction, IsWidget {

    /**
     * przekazuje obiekt multimediow na jakim ma pracowac kontrolka
     *
     * @param mediaDescriptor
     */
    public abstract void setMediaDescriptor(MediaWrapper<?> mediaDescriptor);

    /**
     * obiekt opisujacy funkcje dostepne dla podlaczonego zasobu
     *
     * @return
     */
    public abstract MediaAvailableOptions getMediaAvailableOptions();

    /**
     * Zwraca
     *
     * @return
     */
    public abstract MediaWrapper<?> getMediaWrapper();

    public void setFullScreen(boolean fullScreen);

    public Element getElement();

    public void init();

}
