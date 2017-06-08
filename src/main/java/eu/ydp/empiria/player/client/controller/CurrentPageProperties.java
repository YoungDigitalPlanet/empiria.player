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

package eu.ydp.empiria.player.client.controller;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.view.player.PageControllerCache;

public class CurrentPageProperties {

    private final PageControllerCache pageControllerCache;
    private final Page page;

    @Inject
    public CurrentPageProperties(PageControllerCache pageControllerCache, Page page) {
        this.pageControllerCache = pageControllerCache;
        this.page = page;

    }

    public boolean hasInteractiveModules() {
        int currentPageNumber = page.getCurrentPageNumber();
        PageController currentPageController = pageControllerCache.getElement(currentPageNumber);
        return currentPageController.hasInteractiveModules();
    }
}
