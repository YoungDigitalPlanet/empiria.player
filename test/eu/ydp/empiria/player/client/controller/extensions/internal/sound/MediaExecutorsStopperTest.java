package eu.ydp.empiria.player.client.controller.extensions.internal.sound;

import static junitparams.JUnitParamsRunner.$;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import eu.ydp.empiria.player.client.AbstractTestWithMocksBase;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;

@RunWith(JUnitParamsRunner.class)
public class MediaExecutorsStopperTest extends AbstractTestWithMocksBase {

	private MediaExecutorsStopper stopper;

	@Override
	@Before
	public void setUp() {
		super.setUp(MediaExecutorsStopper.class);
		stopper = injector.getInstance(MediaExecutorsStopper.class);
	}

	@SuppressWarnings("unused")
	private Object[] parametersForForceStop() {
		MediaWrapper<?> currentMediaWrapper = createWrapper(false);
		MediaWrapper<?> currentMediaWrapperPauseSupport = createWrapper(false);
		return $($(null, createExecutor(null), ExpectedAction.NOOP), $(createWrapper(false), createExecutor(null), ExpectedAction.STOP),
				$(createWrapper(true), createExecutor(null), ExpectedAction.STOP), $(null, createExecutor(createWrapper(false)), ExpectedAction.STOP),
				$(null, createExecutor(createWrapper(true)), ExpectedAction.PAUSE),
				$(createWrapper(false), createExecutor(createWrapper(false)), ExpectedAction.STOP),
				$(createWrapper(false), createExecutor(createWrapper(true)), ExpectedAction.PAUSE),
				$(createWrapper(true), createExecutor(createWrapper(false)), ExpectedAction.STOP),
				$(createWrapper(true), createExecutor(createWrapper(true)), ExpectedAction.PAUSE),
				$(currentMediaWrapper, createExecutor(currentMediaWrapper), ExpectedAction.NOOP),
				$(currentMediaWrapperPauseSupport, createExecutor(currentMediaWrapperPauseSupport), ExpectedAction.NOOP));
	}

	@SuppressWarnings("rawtypes")
	private MediaExecutor createExecutor(MediaWrapper<?> mediaWrapper) {
		MediaExecutor exec = mock(MediaExecutor.class);
		stub(exec.getMediaWrapper()).toReturn(mediaWrapper);
		return exec;
	}

	private MediaWrapper<?> createWrapper(boolean withPauseSupport) {
		MediaWrapper<?> mediaWrapper = mock(MediaWrapper.class, RETURNS_DEEP_STUBS);
		stub(mediaWrapper.getMediaAvailableOptions().isPauseSupported()).toReturn(withPauseSupport);
		return mediaWrapper;
	}

	private static enum ExpectedAction {
		NOOP, STOP, PAUSE
	}

	@Test
	@Parameters
	public void forceStop(MediaWrapper<?> currentMediaWrapper, MediaExecutor<?> executor, ExpectedAction result) {
		// given
		List<MediaExecutor<?>> executors = Arrays.asList(new MediaExecutor<?>[] { executor });

		// when
		stopper.forceStop(currentMediaWrapper, executors);

		// then
		assertResult(executor, result);
	}

	private void assertResult(MediaExecutor<?> executor, ExpectedAction result) {
		switch (result) {
		case NOOP:
			verify(executor, never()).stop();
			verify(executor, never()).pause();
			break;

		case STOP:
			verify(executor).stop();
			verify(executor, never()).pause();
			break;

		case PAUSE:
			verify(executor, never()).stop();
			verify(executor).pause();
			break;

		}
	}

}
