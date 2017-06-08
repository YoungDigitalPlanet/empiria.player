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

package eu.ydp.empiria.player.client.controller.xml;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.controller.data.DataSourceManager;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;

@Singleton
public class XMLDataProvider implements Provider<XmlData> {

    @Inject
    private PageScopeFactory pageScopeFactory;

    @Inject
    private DataSourceManager dataSourceManager;

    @Override
    public XmlData get() {
        int pageIndex = pageScopeFactory.getCurrentPageScope().getPageIndex();
        return dataSourceManager.getItemData(pageIndex).getData();
    }

}
