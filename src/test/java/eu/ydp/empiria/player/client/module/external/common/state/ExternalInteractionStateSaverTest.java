package eu.ydp.empiria.player.client.module.external.common.state;

import com.google.common.base.Optional;
import com.google.gwt.core.client.JavaScriptObject;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class ExternalInteractionStateSaverTest {

	private ExternalInteractionStateSaver testObj = new ExternalInteractionStateSaver();

	@Test
	public void shouldReturnEmptyObject_whenStateIsNotSet() {
		// when
		Optional<JavaScriptObject> state = testObj.getExternalState();
		boolean isPresent = state.isPresent();

		// then
		assertThat(isPresent).isFalse();
	}

	@Test
	public void shouldReturnState() {
		// given
		JavaScriptObject expectedState = mock(JavaScriptObject.class);
		testObj.setExternalState(expectedState);

		// when
		Optional<JavaScriptObject> resultState = testObj.getExternalState();

		// then
		assertThat(resultState.isPresent()).isTrue();
		assertThat(resultState.get()).isEqualTo(expectedState);
	}
}
