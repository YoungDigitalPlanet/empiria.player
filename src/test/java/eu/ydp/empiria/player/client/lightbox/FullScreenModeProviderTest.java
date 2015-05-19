package eu.ydp.empiria.player.client.lightbox;

import static org.fest.assertions.api.Assertions.assertThat;

import eu.ydp.empiria.player.client.lightbox.lightbox2.LightBox2;
import eu.ydp.empiria.player.client.lightbox.magnific.popup.MagnificPopup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FullScreenModeProviderTest {

	@InjectMocks
	private FullScreenModeProvider testObj;
	@Mock
	private MagnificPopup magnificPopup;
	@Mock
	private LightBox2 lightBox2;

	@Test
	public void shouldGetMagnificMode() {
		// given
		String mode = "magnific";

		// when
		FullScreen fullscreen = testObj.getFullscreen(mode);

		// then
		assertThat(fullscreen).isEqualTo(magnificPopup);
	}

	@Test
	public void shouldGetDefaultMode_whenUnknown() {
		// given
		String mode = "unknown";

		// when
		FullScreen fullscreen = testObj.getFullscreen(mode);

		// then
		assertThat(fullscreen).isEqualTo(lightBox2);
	}
}
