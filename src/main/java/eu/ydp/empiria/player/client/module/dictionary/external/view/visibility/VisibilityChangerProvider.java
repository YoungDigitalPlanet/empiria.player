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

package eu.ydp.empiria.player.client.module.dictionary.external.view.visibility;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import eu.ydp.gwtutil.client.util.UserAgentChecker.MobileUserAgent;
import eu.ydp.gwtutil.client.util.UserAgentUtil;

@Singleton
public class VisibilityChangerProvider implements Provider<VisibilityChanger> {

    @Inject
    private UserAgentUtil userAgentUtil;
    @Inject
    private Provider<AndroidVisibilityChanger> androidVisibilityChangerProvider;
    @Inject
    private Provider<SafariMobileVisibilityChanger> safariMobileVisibilityChangerProvider;
    @Inject
    private Provider<DefaultVisibilityChanger> defaultVisibilityChangerProvider;

    @Override
    public VisibilityChanger get() {
        if (userAgentUtil.isStackAndroidBrowser()) {
            return androidVisibilityChangerProvider.get();
        } else if (userAgentUtil.isUserAgent(MobileUserAgent.SAFARI, MobileUserAgent.SAFARI_WEBVIEW)) {
            return safariMobileVisibilityChangerProvider.get();
        } else {
            return defaultVisibilityChangerProvider.get();
        }
    }
}
