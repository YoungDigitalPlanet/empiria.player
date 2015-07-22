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
