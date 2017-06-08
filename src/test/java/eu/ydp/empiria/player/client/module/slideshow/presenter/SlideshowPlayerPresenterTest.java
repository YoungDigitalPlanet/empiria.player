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

package eu.ydp.empiria.player.client.module.slideshow.presenter;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwtmockito.GwtMockitoTestRunner;
import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.slideshow.structure.SlideshowBean;
import eu.ydp.empiria.player.client.module.slideshow.structure.SlideshowTitleBean;
import eu.ydp.empiria.player.client.module.slideshow.view.player.SlideshowPlayerView;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class SlideshowPlayerPresenterTest {

    @InjectMocks
    private SlideshowPlayerPresenter testObj;
    @Mock
    private SlideshowPlayerView view;
    @Mock
    private InlineBodyGeneratorSocket inlineBodyGeneratorSocket;

    @Test
    public void shouldInit() {
        // given
        SlideshowBean slideshow = mock(SlideshowBean.class);

        SlideshowTitleBean slideshowTitle = mock(SlideshowTitleBean.class);
        when(slideshow.getTitle()).thenReturn(slideshowTitle);

        XMLContent titleValue = mock(XMLContent.class);
        when(slideshowTitle.getTitleValue()).thenReturn(titleValue);

        Element titleElement = mock(Element.class);
        when(titleValue.getValue()).thenReturn(titleElement);

        Widget titleView = mock(Widget.class);
        when(inlineBodyGeneratorSocket.generateInlineBody(titleElement)).thenReturn(titleView);

        // when
        testObj.init(slideshow, inlineBodyGeneratorSocket);

        // then
        verify(view).setTitle(titleView);
    }

    @Test
    public void shouldDelegatePager() {
        // given
        Widget widget = mock(Widget.class);
        when(view.asWidget()).thenReturn(widget);

        // when
        testObj.setPager(widget);

        // then
        verify(view).addPager(widget);
    }

    @Test
    public void shouldReturnViewAsWidget() {
        // given
        Widget widget = mock(Widget.class);
        when(view.asWidget()).thenReturn(widget);

        // when
        Widget result = testObj.getView();

        // then
        assertThat(result).isEqualTo(widget);
    }
}
