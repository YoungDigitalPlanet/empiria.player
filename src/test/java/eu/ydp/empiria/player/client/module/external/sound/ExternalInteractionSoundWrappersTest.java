package eu.ydp.empiria.player.client.module.external.sound;

import com.google.gwt.user.client.ui.Widget;
import eu.ydp.empiria.player.client.media.MediaWrapperCreator;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.callback.CallbackReceiver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ExternalInteractionSoundWrappersTest {

	@InjectMocks
	private ExternalInteractionSoundWrappers testObj;
	@Mock
	private MediaWrapperCreator mediaWrapperCreator;
	@Mock
	private MediaWrapperAction action;
	@Mock
	private MediaWrapper<Widget> mediaWrapper;
	@Captor
	private ArgumentCaptor<CallbackReceiver<MediaWrapper<Widget>>> callbackCaptor;

	@Test
	public void shouldCreateMediaWrapper() {
		// given
		String src = "src";

		// when
		testObj.execute(src, action);

		// then
		verify(mediaWrapperCreator).createMediaWrapper(eq(src), callbackCaptor.capture());
		CallbackReceiver<MediaWrapper<Widget>> callback = callbackCaptor.getValue();
		callback.setCallbackReturnObject(mediaWrapper);

		verify(action).execute(mediaWrapper);
	}

	@Test
	public void shouldExecuteCachedWrapper() {
		// given
		String src = "src";

		// when
		testObj.execute(src, action);

		verify(mediaWrapperCreator).createMediaWrapper(eq(src), callbackCaptor.capture());
		CallbackReceiver<MediaWrapper<Widget>> callback = callbackCaptor.getValue();
		callback.setCallbackReturnObject(mediaWrapper);

		testObj.execute(src, action);

		// then
		verify(action, times(2)).execute(mediaWrapper);
	}
}