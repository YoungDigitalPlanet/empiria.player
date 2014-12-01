package eu.ydp.empiria.player.client.module.media.progress;
import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class ProgressUpdateLogicTest {

	private ProgressUpdateLogic testObj;

	@Before
	public void init(){
		testObj = new ProgressUpdateLogic();
	}

	@Test
	public void shouldReturnTrue_whenDiffIsBiggerThanOne(){
		// when
		int lastTime = 5;
		double currentTime = lastTime + 2;

		// when
		boolean result = testObj.isReadyToUpdate(currentTime, lastTime);

		// than
		assertThat(result).isTrue();
	}

	@Test
	public void shouldReturnTrue_whenCurrentTimeIsSmaller(){
		// when
		int lastTime = 5;
		double currentTime = lastTime - 2;

		// when
		boolean result = testObj.isReadyToUpdate(currentTime, lastTime);

		// than
		assertThat(result).isTrue();
	}

	@Test
	public void shouldReturnFalse_whenDiffIsLessThanOne(){
		// when
		int lastTime = 5;
		double currentTime = lastTime + 0.5;

		// when
		boolean result = testObj.isReadyToUpdate(currentTime, lastTime);

		// than
		assertThat(result).isFalse();
	}
}
