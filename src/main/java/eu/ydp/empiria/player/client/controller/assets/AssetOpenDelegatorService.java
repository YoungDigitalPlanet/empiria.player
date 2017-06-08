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

package eu.ydp.empiria.player.client.controller.assets;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class AssetOpenDelegatorService {

    @Inject
    private AssetOpenJSDelegator assetOpenJSDelegator;

    @Inject
    private URLOpenService urlOpenService;

    public void open(String path) {
        if (externalLinkSupported()) {
            delegateToExternal(path);
        } else {
            openInWindow(path);
        }
    }

    private boolean externalLinkSupported() {
        return assetOpenJSDelegator.empiriaExternalLinkSupported();
    }

    private void delegateToExternal(String path) {
        assetOpenJSDelegator.empiriaExternalLinkOpen(path);
    }

    private void openInWindow(String path) {
        urlOpenService.open(path);
    }
}
