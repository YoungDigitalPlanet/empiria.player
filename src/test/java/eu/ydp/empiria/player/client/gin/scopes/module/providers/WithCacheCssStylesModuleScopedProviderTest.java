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

package eu.ydp.empiria.player.client.gin.scopes.module.providers;

import com.google.common.collect.Lists;
import com.google.gwt.xml.client.Element;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.controller.data.ElementStyleSelectorBuilder;
import eu.ydp.empiria.player.client.style.ModuleStyle;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class WithCacheCssStylesModuleScopedProviderTest {
    @Mock
    private ElementStyleSelectorBuilder elementStyleSelectorBuilder;
    @Mock
    private Provider<Element> xmlProvider;
    @Mock
    private Provider<ModuleStyle> moduleStyleProvider;
    @Mock
    private ModuleStyle moduleStyle;
    @Mock
    Element element;

    @InjectMocks
    private WithCacheCssStylesModuleScopedProvider instance;

    private final List<String> selectors = Lists.newArrayList("3 3");

    @Before
    public void before() {
        doReturn(element).when(xmlProvider).get();
        doReturn(selectors).when(elementStyleSelectorBuilder).getElementSelectors(eq(element));
        doReturn(moduleStyle).when(moduleStyleProvider).get();
    }

    @Test
    public void get_NoDataInCache() throws Exception {
        ModuleStyle moduleStyle = instance.get();
        assertThat(moduleStyle).isNotNull();

        verify(moduleStyleProvider).get();
    }

    @Test
    public void get_FromCache() throws Exception {
        instance.get();
        ModuleStyle moduleStyle = instance.get();

        assertThat(moduleStyle).isNotNull();
        // only once
        verify(moduleStyleProvider).get();
    }

}
