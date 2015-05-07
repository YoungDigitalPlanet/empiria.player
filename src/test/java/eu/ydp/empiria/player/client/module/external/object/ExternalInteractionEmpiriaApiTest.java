package eu.ydp.empiria.player.client.module.external.object;

import eu.ydp.empiria.player.client.module.external.ExternalInteractionResponseModel;
import eu.ydp.empiria.player.client.module.external.sound.ExternalInteractionSoundApi;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExternalInteractionEmpiriaApiTest {

	@InjectMocks
	private ExternalInteractionEmpiriaApi testObj;
	@Mock
	private ExternalInteractionResponseModel responseModel;
	@Mock
	private ExternalInteractionSoundApi soundApi;
	@Captor
	private ArgumentCaptor<String> answersCaptor;

	@Test
	public void shouldReplaceExternalState() {
		// given
		int done = 5;
		int errors = 3;
		ExternalInteractionStatus status = mock(ExternalInteractionStatus.class);
		when(status.getDone()).thenReturn(done);
		when(status.getErrors()).thenReturn(errors);

		List<String> expectedAnswers = Arrays.asList("1", "2", "3", "4", "5", "-1", "-2", "-3");
		int answersSize = expectedAnswers.size();

		// when
		testObj.onResultChange(status);

		// then
		verify(responseModel, times(answersSize)).addAnswer(answersCaptor.capture());
		List<String> resultList = answersCaptor.getAllValues();
		assertThat(resultList).containsAll(expectedAnswers);
	}

	@Test
	public void shouldDelegatePlay() {
		// given
		String src = "src";

		// when
		testObj.play(src);

		// then
		verify(soundApi).play(src);
	}

	@Test
	public void shouldDelegatePlayLooped() {
		// given
		String src = "src";

		// when
		testObj.playLooped(src);

		// then
		verify(soundApi).playLooped(src);
	}

	@Test
	public void shouldDelegatePause() {
		// given
		String src = "src";

		// when
		testObj.pause(src);

		// then
		verify(soundApi).pause(src);
	}

	@Test
	public void shouldDelegateResume() {
		// given
		String src = "src";

		// when
		testObj.resume(src);

		// then
		verify(soundApi).resume(src);
	}

	@Test
	public void shouldDelegateStop() {
		// given
		String src = "src";

		// when
		testObj.stop(src);

		// then
		verify(soundApi).stop(src);
	}
}