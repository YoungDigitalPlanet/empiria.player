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

package eu.ydp.empiria.player.client.module.img.explorable;

import com.google.gwtmockito.GwtMockitoTestRunner;
import com.google.inject.Provider;
import eu.ydp.gwtutil.client.util.UserAgentChecker;
import eu.ydp.gwtutil.client.util.UserAgentUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@RunWith(GwtMockitoTestRunner.class)
public class ExplorableImgWindowProviderTest {

    @InjectMocks
    private ExplorableImgWindowProvider testObj;
    @Mock
    private UserAgentUtil userAgentUtil;
    @Mock
    private Provider<ExplorableImgWindowImg> explorableImgWindowImgProvider;
    @Mock
    private ExplorableImgWindowImg explorableImgWindowImg;
    @Mock
    private Provider<ExplorableImgWindowCanvas> explorableImgWindowCanvasProvider;
    @Mock
    private ExplorableImgWindowCanvas explorableImgWindowCanvas;

    @Before
    public void init() {
        when(explorableImgWindowImgProvider.get()).thenReturn(explorableImgWindowImg);
        when(explorableImgWindowCanvasProvider.get()).thenReturn(explorableImgWindowCanvas);
    }

    @Test
    public void shouldReturnCanvasWindow_whenUserIsNotIE8() {
        // given
        when(userAgentUtil.isUserAgent(UserAgentChecker.UserAgent.IE8)).thenReturn(false);

        // when
        ExplorableImgWindow result = testObj.get();

        // then
        assertThat(result).isEqualTo(explorableImgWindowCanvas);
    }

    @Test
    public void shouldReturnImgWindow_whenUserIsIE8() {
        // given
        when(userAgentUtil.isUserAgent(UserAgentChecker.UserAgent.IE8)).thenReturn(true);

        // when
        ExplorableImgWindow result = testObj.get();

        // then
        assertThat(result).isEqualTo(explorableImgWindowImg);
    }
}