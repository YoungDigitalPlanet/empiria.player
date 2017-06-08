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

package eu.ydp.empiria.player.client.module.img.picture.player.presenter;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.jaxb.XmlContentMock;
import eu.ydp.empiria.player.client.module.img.picture.player.structure.PictureAltProvider;
import eu.ydp.empiria.player.client.module.img.picture.player.structure.PicturePlayerBean;
import eu.ydp.empiria.player.client.module.img.picture.player.structure.PicturePlayerTitleBean;
import eu.ydp.empiria.player.client.module.img.picture.player.view.PicturePlayerView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PicturePlayerPresenterTest {

    @InjectMocks
    private PicturePlayerPresenter testObj;
    @Mock
    private PicturePlayerView view;
    @Mock
    private PicturePlayerFullscreenController fullscreenController;
    @Mock
    private PictureAltProvider pictureAltProvider;
    @Mock
    private InlineBodyGeneratorSocket inlineBodyGeneratorSocket;

    private PicturePlayerBean bean = new PicturePlayerBean();

    private String src = "src";
    private String srcFullscreen = "src_f";
    private String alt = "alt";

    @Before
    public void setUp() throws Exception {
        bean.setSrc(src);
        bean.setAlt(alt);
        bean.setSrcFullScreen(srcFullscreen);
        PicturePlayerTitleBean titleBean = new PicturePlayerTitleBean();
        bean.setTitleBean(titleBean);
        titleBean.setTitleName(new XmlContentMock());
    }

    @Test
    public void shouldInitFullPicturePlayer() {
        // given
        when(pictureAltProvider.getPictureAltString(bean)).thenReturn(alt);

        // when
        testObj.init(bean, inlineBodyGeneratorSocket);

        // then
        verify(view).setImage(alt, src);
        verify(view).addFullscreenButton();
    }

    @Test
    public void shouldNotInitFullscreen_whenModuleIsTemplate() {
        // given
        testObj.setTemplate(true);

        // when
        testObj.init(bean, inlineBodyGeneratorSocket);

        // then
        verify(view).setImage(anyString(), eq(src));
        verify(view, never()).addFullscreenButton();
    }

    @Test
    public void shouldNotInitFullscreen_whenSrcFullscreenIsNull() {
        // given
        bean.setSrcFullScreen(null);

        // when
        testObj.init(bean, inlineBodyGeneratorSocket);

        // then
        verify(view).setImage(anyString(), eq(src));
        verify(view, never()).addFullscreenButton();
    }

    @Test
    public void shouldNotInitFullscreen_whenSrcFullscreenIsEmpty() {
        // given
        bean.setSrcFullScreen("");

        // when
        testObj.init(bean, inlineBodyGeneratorSocket);

        // then
        verify(view).setImage(anyString(), eq(src));
        verify(view, never()).addFullscreenButton();
    }


    @Test
    public void shouldOpenFullscreen() {
        // given
        testObj.init(bean, inlineBodyGeneratorSocket);

        // when
        testObj.openFullscreen();

        // then
        verify(fullscreenController).openFullscreen(bean, inlineBodyGeneratorSocket);

    }
}
