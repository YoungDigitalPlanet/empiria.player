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

package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.core.client.GWT;
import com.google.inject.Provider;
import com.google.inject.TypeLiteral;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScopedProvider;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistLockingController;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistManager;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistManagerImpl;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistManagerModel;
import eu.ydp.empiria.player.client.module.sourcelist.presenter.SourceListPresenter;
import eu.ydp.empiria.player.client.module.sourcelist.presenter.SourceListPresenterImpl;
import eu.ydp.empiria.player.client.module.sourcelist.structure.SourceListJAXBParser;
import eu.ydp.empiria.player.client.module.sourcelist.view.SourceListView;
import eu.ydp.empiria.player.client.module.sourcelist.view.SourceListViewImpl;

public class SourceListGinModule extends BaseGinModule {
    public static class SourceListJAXBParserProvider implements Provider<SourceListJAXBParser> {
        @Override
        public SourceListJAXBParser get() {
            return GWT.create(SourceListJAXBParser.class);
        }

        ;
    }

    @Override
    protected void configure() {
        bind(SourceListView.class).to(SourceListViewImpl.class);
        bind(SourceListPresenter.class).to(SourceListPresenterImpl.class);
        bind(SourceListJAXBParser.class).toProvider(SourceListJAXBParserProvider.class);
        bind(SourcelistManager.class).to(SourcelistManagerImpl.class);

        bindPageScoped(SourcelistManager.class, new TypeLiteral<PageScopedProvider<SourcelistManager>>() {
        });
        bindPageScoped(SourcelistManagerModel.class, new TypeLiteral<PageScopedProvider<SourcelistManagerModel>>() {
        });
        bindPageScoped(SourcelistLockingController.class, new TypeLiteral<PageScopedProvider<SourcelistLockingController>>() {
        });
    }

}
