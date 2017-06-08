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

package eu.ydp.empiria.player.client.view.player;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.controller.multiview.MultiPageController;
import eu.ydp.empiria.player.client.gin.factory.PageContentFactory;
import eu.ydp.empiria.player.client.view.page.PageContentView;
import eu.ydp.empiria.player.client.view.page.PageViewSocket;
import eu.ydp.gwtutil.client.collections.KeyValue;

@Singleton
public class PageViewCache extends AbstractElementCache<KeyValue<PageViewSocket, PageContentView>> {
    @Inject
    private MultiPageController multiPageView;
    @Inject
    private PageContentFactory pageContentFactory;

    @Override
    protected KeyValue<PageViewSocket, PageContentView> getElement(Integer index) {
        PageContentView view = pageContentFactory.getPageContentView(multiPageView.getViewForPage(index));
        return new KeyValue<>(pageContentFactory.getPageViewSocket(view), view);
    }
}
