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

package eu.ydp.empiria.player.client.module.speechscore.presenter;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import eu.ydp.empiria.player.client.module.speechscore.view.SpeechScoreLinkView;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SpeechScorePresenterTest {

    @InjectMocks
    private SpeechScorePresenter testObj;

    @Mock
    private SpeechScoreLinkView view;

    @Test
    public void shouldBuildLinkOnView() {
        // given
        final String LINK_TEXT = "Link text";

        Node node = mock(Node.class);
        when(node.getNodeValue()).thenReturn(LINK_TEXT);

        Element element = mock(Element.class);
        when(element.getFirstChild()).thenReturn(node);
        when(element.getAttribute("url")).thenReturn("url");

        // when
        testObj.init(element);

        // then
        verify(view).buildLink(LINK_TEXT, "http://url");
    }
}
