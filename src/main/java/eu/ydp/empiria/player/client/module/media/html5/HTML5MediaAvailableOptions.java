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

package eu.ydp.empiria.player.client.module.media.html5;

import eu.ydp.empiria.player.client.module.media.MediaAvailableOptions;
import eu.ydp.empiria.player.client.util.NativeHTML5FullScreenHelper;
import eu.ydp.gwtutil.client.util.UserAgentChecker;
import eu.ydp.gwtutil.client.util.UserAgentChecker.MobileUserAgent;

import static eu.ydp.gwtutil.client.util.UserAgentChecker.UserAgent.IE8;
import static eu.ydp.gwtutil.client.util.UserAgentChecker.UserAgent.IE9;

public class HTML5MediaAvailableOptions implements MediaAvailableOptions {

    @Override
    public boolean isPlaySupported() {
        return true;
    }

    @Override
    public boolean isPauseSupported() {
        return true;
    }

    @Override
    public boolean isMuteSupported() {
        return isVolumeChangeSupported();
    }

    @Override
    public boolean isVolumeChangeSupported() {
        // mobilna i inna niz ff i chrome bo tam dziala
        if (UserAgentChecker.getMobileUserAgent() != MobileUserAgent.UNKNOWN
                && !UserAgentChecker.isMobileUserAgent(MobileUserAgent.FIREFOX, MobileUserAgent.CHROME)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isStopSupported() {
        return true;
    }

    @Override
    public boolean isSeekSupported() {
        return true;
    }

    @Override
    public boolean isFullScreenSupported() {
        return NativeHTML5FullScreenHelper.isFullScreenSupported() || UserAgentChecker.isMobileUserAgent() || UserAgentChecker.isUserAgent(IE8, IE9);
    }

    @Override
    public boolean isMediaMetaAvailable() {
        return true;
    }

    @Override
    public boolean isTemplateSupported() {
        return true;
    }

}
