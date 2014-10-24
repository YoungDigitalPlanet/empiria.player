package eu.ydp.empiria.player.client.module.simulation.soundjs;

import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import com.google.gwtmockito.GwtMockitoTestRunner;

@RunWith(GwtMockitoTestRunner.class)
public class SoundInstanceIdPredicateTest {

	private SoundInstanceIdPredicate testObj;

	@Mock
	private SoundInstance soundInstance;

	@Before
	public void setUp() {
		int id = 123;
		testObj = new SoundInstanceIdPredicate(id);
	}

	@Test
	public void shouldReturnTrue() {
		//given
		int id = 123;
		when(soundInstance.getUniqueId()).thenReturn(id);
		
		//when
		boolean result = testObj.apply(soundInstance);
		
		assertThat(result).isTrue();
	}

	@Test
	public void shouldReturnFalse() {
		// given
		int id = 89;
		when(soundInstance.getUniqueId()).thenReturn(id);

		// when
		boolean result = testObj.apply(soundInstance);

		assertThat(result).isFalse();
	}
}
