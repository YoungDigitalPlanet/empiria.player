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

package eu.ydp.empiria.player.client.module.img.picture.player.structure;

import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(GwtMockitoTestRunner.class)
public class PictureAltProviderTest {

    @InjectMocks
    private PictureAltProvider testObj;
    @Mock
    private InlineBodyGeneratorSocket inlineBodyGeneratorSocket;
    @Mock
    private PictureTitleProvider pictureTitleProvider;
    @Mock
    private PicturePlayerBean bean;

    private String TEST_ALT = "testAlt";
    private String TEST_TITLE = "testTitle";

    @Test
    public void shouldReturnAlt_whenAltIsPresent() {
        // given
        when(bean.hasAlt()).thenReturn(true);
        when(bean.getAlt()).thenReturn(TEST_ALT);

        //when
        String result = testObj.getPictureAltString(bean);

        //then
        assertThat(result).isEqualTo(TEST_ALT);
    }

    @Test
    public void shouldReturnTitle_whenAltIsNotPresent() throws Exception {
        //given
        when(bean.hasAlt()).thenReturn(false);
        when(pictureTitleProvider.getPictureTitleString(bean)).thenReturn(TEST_TITLE);

        //when
        String result = testObj.getPictureAltString(bean);

        //then
        assertThat(result).isEqualTo(TEST_TITLE);
    }
}