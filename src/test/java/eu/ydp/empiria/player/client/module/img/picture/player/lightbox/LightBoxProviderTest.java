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

package eu.ydp.empiria.player.client.module.img.picture.player.lightbox;

import com.google.inject.Provider;
import eu.ydp.empiria.player.client.module.img.picture.player.lightbox.lightbox2.LightBox2;
import eu.ydp.empiria.player.client.module.img.picture.player.lightbox.magnific.popup.MagnificPopup;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LightBoxProviderTest {

    @InjectMocks
    private LightBoxProvider testObj;
    @Mock
    private Provider<MagnificPopup> magnificPopupProvider;
    @Mock
    private Provider<LightBox2> lightBox2Provider;
    @Mock
    private MagnificPopup magnificPopup;
    @Mock
    private LightBox2 lightBox2;

    @Before
    public void init() {
        when(magnificPopupProvider.get()).thenReturn(magnificPopup);
        when(lightBox2Provider.get()).thenReturn(lightBox2);
    }

    @Test
    public void shouldGetMagnificMode() {
        // given
        String mode = "magnific";

        // when
        LightBox fullscreen = testObj.getFullscreen(mode);

        // then
        assertThat(fullscreen).isEqualTo(magnificPopup);
    }

    @Test
    public void shouldGetDefaultMode_whenUnknown() {
        // given
        String mode = "unknown";

        // when
        LightBox fullscreen = testObj.getFullscreen(mode);

        // then
        assertThat(fullscreen).isEqualTo(lightBox2);
    }
}
