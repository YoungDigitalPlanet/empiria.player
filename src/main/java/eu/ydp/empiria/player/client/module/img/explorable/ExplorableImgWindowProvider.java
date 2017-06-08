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

package eu.ydp.empiria.player.client.module.img.explorable;

import com.google.inject.Inject;
import com.google.inject.Provider;
import eu.ydp.gwtutil.client.util.UserAgentChecker;
import eu.ydp.gwtutil.client.util.UserAgentUtil;

public class ExplorableImgWindowProvider implements Provider<ExplorableImgWindow>{

    @Inject
    private UserAgentUtil userAgent;
    @Inject
    private Provider<ExplorableImgWindowImg> explorableImgWindowImgProvider;
    @Inject
    private Provider<ExplorableImgWindowCanvas> explorableImgWindowCanvasProvider;

    @Override
    public ExplorableImgWindow get() {
        boolean isIE8 = userAgent.isUserAgent(UserAgentChecker.UserAgent.IE8);
        if(isIE8){
            return explorableImgWindowImgProvider.get();
        }
        return explorableImgWindowCanvasProvider.get();
    }
}
