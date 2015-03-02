package eu.ydp.empiria.player.client.controller.assets;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AssetOpenDelegatorServiceJUnitTest {

	private final static String PATH = "path";

	@InjectMocks
	private AssetOpenDelegatorService service;

	@Mock
	private AssetOpenJSDelegator assetOpenJSDelegator;
	@Mock
	private URLOpenService urlOpenService;

	@Test
	public void shouldOpenExternal() {
		// given
		when(assetOpenJSDelegator.empiriaExternalLinkSupported()).thenReturn(true);

		// when
		service.open(PATH);

		// then
		verify(assetOpenJSDelegator).empiriaExternalLinkOpen(PATH);
	}

	@Test
	public void shouldOpenInWindow() {
		// given
		when(assetOpenJSDelegator.empiriaExternalLinkSupported()).thenReturn(false);

		// when
		service.open(PATH);

		// then
		verify(urlOpenService).open(PATH);
	}
}
