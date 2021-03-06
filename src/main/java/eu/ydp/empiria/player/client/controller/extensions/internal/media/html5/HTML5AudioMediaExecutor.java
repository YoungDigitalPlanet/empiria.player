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

package eu.ydp.empiria.player.client.controller.extensions.internal.media.html5;

import com.google.common.base.Strings;
import com.google.gwt.dom.client.MediaElement;
import com.google.gwt.media.client.Audio;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.html5.natives.HTML5MediaNativeListeners;
import eu.ydp.gwtutil.client.util.UserAgentChecker;

public class HTML5AudioMediaExecutor extends AbstractHTML5MediaExecutor<Audio> {

    @Inject
    public HTML5AudioMediaExecutor(HTML5MediaEventMapper mediaEventMapper, HTML5MediaNativeListeners html5MediaNativeListeners) {
        super(mediaEventMapper, html5MediaNativeListeners);
    }

    @Override
    public void initExecutor() {
    }

    @Override
    protected String getMediaPreloadType() {
        return MediaElement.PRELOAD_AUTO;
    }

    @Override
    public void play() {
        applyIOS11Hack();
        super.play();
    }

    @Override
    public void playLooped() {
        applyIOS11Hack();
        super.playLooped();
    }

    private void applyIOS11Hack(){
        if (UserAgentChecker.isMobileUserAgent(UserAgentChecker.MobileUserAgent.SAFARI)) {
            if (Strings.isNullOrEmpty(media.getSrc())) {
                media.setSrc(baseMediaConfiguration.getSources().keySet().iterator().next());
            }
        }
    }
}
