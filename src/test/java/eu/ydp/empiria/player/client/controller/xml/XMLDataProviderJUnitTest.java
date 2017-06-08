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

import com.google.inject.Binder;
import com.google.inject.Module;
import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.GuiceModuleConfiguration;
import eu.ydp.empiria.player.client.controller.Page;
import eu.ydp.empiria.player.client.controller.communication.ItemData;
import eu.ydp.empiria.player.client.controller.data.DataSourceManager;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.util.events.internal.scope.CurrentPageScope;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@SuppressWarnings("PMD")
public class XMLDataProviderJUnitTest extends AbstractTestBaseWithoutAutoInjectorInit {

    private class CustomGinModule implements Module {
        @Override
        public void configure(Binder binder) {
            binder.bind(DataSourceManager.class).toInstance(dataSourceManager);
            binder.bind(PageScopeFactory.class).toInstance(pageScopeFactory);
        }
    }

    private final DataSourceManager dataSourceManager = mock(DataSourceManager.class);
    private final PageScopeFactory pageScopeFactory = mock(PageScopeFactory.class);
    private final CurrentPageScope pageScope = spy(new CurrentPageScope(new Page()));
    private XMLDataProvider instance;
    private final XmlData xmlData = mock(XmlData.class);
    private final ItemData itemData = new ItemData(0, xmlData);

    @Before
    public void before() {
        setUpAndOverrideMainModule(new GuiceModuleConfiguration(), new CustomGinModule());
        instance = injector.getInstance(XMLDataProvider.class);
        doReturn(itemData).when(dataSourceManager).getItemData(Matchers.anyInt());
        when(pageScopeFactory.getCurrentPageScope()).thenReturn(pageScope);
        doReturn(1).when(pageScope).getPageIndex();
    }

    @Test
    public void get() throws Exception {
        assertEquals(xmlData, instance.get());
        verify(pageScope).getPageIndex();
    }
}
