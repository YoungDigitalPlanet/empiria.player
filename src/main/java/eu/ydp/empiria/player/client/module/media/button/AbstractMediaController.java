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

import com.google.gwt.user.client.ui.Composite;
import eu.ydp.empiria.player.client.module.media.MediaAvailableOptions;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;

public abstract class AbstractMediaController extends Composite implements MediaController {
    protected static final String CLICK_SUFFIX = "-click";
    protected static final String HOVER_SUFFIX = "-hover";
    protected static final String FULL_SCREEN_SUFFIX = "-fullscreen";
    protected static final String UNSUPPORTED_SUFFIX = "-unsupported";
    private MediaAvailableOptions availableOptions;
    private MediaWrapper<?> mediaWrapper = null;
    private boolean infullScreen = false;

    /*
     * (non-Javadoc)
     *
     * @see eu.ydp.empiria.player.client.module.media.button.MediaController# setMediaDescriptor (eu.ydp.empiria.player.client.module.media.MediaWrapper)
     */
    @Override
    public void setMediaDescriptor(MediaWrapper<?> mediaDescriptor) {
        this.mediaWrapper = mediaDescriptor;
        availableOptions = mediaDescriptor.getMediaAvailableOptions();
    }

    /**
     * inicjalizacja kontrolki
     */
    @Override
    public abstract void init();

    /*
     * (non-Javadoc)
     *
     * @see eu.ydp.empiria.player.client.module.media.button.MediaController# getMediaAvailableOptions()
     */
    @Override
    public MediaAvailableOptions getMediaAvailableOptions() {
        return availableOptions;
    }

    /*
     * (non-Javadoc)
     *
     * @see eu.ydp.empiria.player.client.module.media.button.MediaController# getMediaWrapper()
     */
    @Override
    public MediaWrapper<?> getMediaWrapper() {
        return mediaWrapper;
    }

    @Override
    public void setFullScreen(boolean fullScreen) {
        this.infullScreen = fullScreen;
        setStyleNames();
    }

    /**
     * Czy modu renderowny jest w trybie fullscreen
     *
     * @return
     */
    public boolean isInFullScreen() {
        return infullScreen;
    }

    /**
     * Zwraca suffix dla danego trybu dzialania np fullscreen
     *
     * @return
     */
    public String getSuffixToAdd() {
        return isInFullScreen() ? FULL_SCREEN_SUFFIX : "";
    }

    public abstract void setStyleNames();
}
