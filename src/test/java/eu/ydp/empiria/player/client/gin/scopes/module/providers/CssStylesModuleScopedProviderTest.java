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

import com.google.common.collect.Maps;
import com.google.gwt.xml.client.Element;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.style.IOSModuleStyle;
import eu.ydp.empiria.player.client.style.ModuleStyle;
import eu.ydp.empiria.player.client.style.ModuleStyleImpl;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.gwtutil.client.util.UserAgentChecker.MobileUserAgent;
import eu.ydp.gwtutil.client.util.UserAgentUtil;
import org.fest.assertions.data.MapEntry;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Map;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CssStylesModuleScopedProviderTest {
    @Mock
    private UserAgentUtil agentUtil;
    @Mock
    private StyleSocket styleSocket;
    @Mock
    private Provider<Element> xmlProvider;
    @Mock
    Element element;
    @InjectMocks
    private CssStylesModuleScopedProvider instance;

    private final Map<String, String> styleMap = Maps.newHashMap();

    @Before
    public void before() {
        doReturn(element).when(xmlProvider).get();
        doReturn(styleMap).when(styleSocket).getStyles(any(Element.class));
        styleMap.put("1", "11");
    }

    @Test
    public void getIOS() throws Exception {
        doReturn(true).when(agentUtil).isMobileUserAgent(eq(MobileUserAgent.SAFARI));

        ModuleStyle moduleStyle = instance.get();
        assertThat(moduleStyle).isInstanceOf(IOSModuleStyle.class);
        assertThat(moduleStyle).contains(MapEntry.entry("1", "11"));
        verify(agentUtil).isMobileUserAgent(eq(MobileUserAgent.SAFARI));
        verify(styleSocket).getStyles(eq(element));

    }

    @Test
    public void get() throws Exception {
        ModuleStyle moduleStyle = instance.get();
        assertThat(moduleStyle).isInstanceOf(ModuleStyleImpl.class);
        assertThat(moduleStyle).contains(MapEntry.entry("1", "11"));
        verify(agentUtil).isMobileUserAgent(eq(MobileUserAgent.SAFARI));
        verify(styleSocket).getStyles(eq(element));
    }

}
