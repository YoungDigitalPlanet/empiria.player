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

package eu.ydp.empiria.player.client.controller.multiview.swipe;

import com.google.common.collect.Maps;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.gwtutil.client.xml.XMLParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Map;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class SwipeTypeProviderTest {

    @Mock
    private StyleSocket styleSocket;
    @Mock
    private XMLParser parser;
    @Mock
    private Document documentElement;
    @Mock
    private Element firstChild;

    @InjectMocks
    private SwipeTypeProvider instance;
    private Map<String, String> styleMap;

    @Before
    public void before() {
        doReturn(firstChild).when(documentElement).getFirstChild();
        doReturn(documentElement).when(parser).parse(anyString());
        doReturn(firstChild).when(documentElement).getDocumentElement();
        styleMap = Maps.newHashMap();
        doReturn(styleMap).when(styleSocket).getStyles(any(Element.class));
    }

    @Test
    public void getSwipeDisabled() throws Exception {
        styleMap.put(EmpiriaStyleNameConstants.EMPIRIA_SWIPE_DISABLE_ANIMATION, "true");
        assertThat(instance.get()).isEqualTo(SwipeType.DISABLED);
    }

    @Test
    public void getSwipeEnabled() throws Exception {
        assertThat(instance.get()).isEqualTo(SwipeType.DEFAULT);
    }

}
