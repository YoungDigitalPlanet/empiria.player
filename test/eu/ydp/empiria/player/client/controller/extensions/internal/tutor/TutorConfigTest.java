package eu.ydp.empiria.player.client.controller.extensions.internal.tutor;

import static eu.ydp.empiria.player.client.module.tutor.ActionType.ON_PAGE_ALL_OK;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.gwt.core.client.JsArray;

import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.js.TutorConfigJs;
import eu.ydp.empiria.player.client.module.tutor.ActionType;
import eu.ydp.gwtutil.junit.runners.ExMockRunner;
import eu.ydp.gwtutil.junit.runners.PrepareForTest;

@RunWith(ExMockRunner.class)
@PrepareForTest(JsArray.class)
public class TutorConfigTest {
	
	private TutorConfig tutorConfig;

	@Test
	public void supportsAction() {
		// given
		ActionType type = ON_PAGE_ALL_OK;
		TutorConfigJs tutorConfigJs = createTutorConfigJs();
		tutorConfig = new TutorConfig(tutorConfigJs);
		
		// when
		boolean supportsAction = tutorConfig.supportsAction(type);
		
		// then
		assertThat(supportsAction).isTrue();
	}

	private TutorConfigJs createTutorConfigJs() {
		TutorConfigJs config = mock(TutorConfigJs.class, RETURNS_DEEP_STUBS);
		when(config.getActions().length()).thenReturn(1);
		//when(config.getActions().get(0)).thenReturn(1);
		return config;
	}
}
