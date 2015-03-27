package eu.ydp.empiria.player.client.module.media;

import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import eu.ydp.empiria.player.client.util.MimeUtil;

@RunWith(MockitoJUnitRunner.class)
public class MimeSourceProviderTest {

	@InjectMocks
	private MimeSourceProvider testObj;

	@Mock
	private MimeUtil mimeUtil;

	@Test
	public void shouldGetSourceByExtension() {
		// given
		String fileName = "file.mp3";
		String mimeType = "audio/mp4";
		when(mimeUtil.getMimeTypeFromExtension(fileName)).thenReturn(mimeType);

		// when
		Map<String, String> result = testObj.getSourcesWithTypeByExtension(fileName);

		// then
		assertThat(result.keySet()).hasSize(1);
		assertThat(result.get(fileName)).isEqualTo(mimeType);
	}
}
