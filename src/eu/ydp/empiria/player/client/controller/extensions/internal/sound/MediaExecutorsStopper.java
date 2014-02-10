package eu.ydp.empiria.player.client.controller.extensions.internal.sound;

import java.util.Collection;

import eu.ydp.empiria.player.client.module.media.MediaWrapper;

public class MediaExecutorsStopper {

	public void forceStop(MediaWrapper<?> currentMediaWrapper, Collection<MediaExecutor<?>> executors) {
		for (MediaExecutor<?> exec : executors) {
			maybeStopOrPauseExecutor(currentMediaWrapper, exec);
		}
	}

	private void maybeStopOrPauseExecutor(MediaWrapper<?> currentMediaWrapper, MediaExecutor<?> exec) {
		MediaWrapper<?> otherMediaWrapper = exec.getMediaWrapper();

		boolean otherMediaWrapperDefined = (otherMediaWrapper != null);
		boolean currentMediaWrapperDefined = (currentMediaWrapper != null);

		if (otherMediaWrapperDefined) {

			stopOrPauseIfDifferent(currentMediaWrapper, exec);

		} else if (currentMediaWrapperDefined) {

			stop(exec);

		}
	}

	private void stopOrPauseIfDifferent(MediaWrapper<?> currentMediaWrapper, MediaExecutor<?> exec) {
		MediaWrapper<?> otherMediaWrapper = exec.getMediaWrapper();

		boolean currentIsDifferentThanOther = !otherMediaWrapper.equals(currentMediaWrapper);

		if (currentIsDifferentThanOther) {
			boolean pauseSupported = otherMediaWrapper.getMediaAvailableOptions().isPauseSupported();
			stopOrPause(exec, pauseSupported);
		}
	}

	private void stopOrPause(MediaExecutor<?> exec, boolean pauseSupported) {
		if (pauseSupported) {
			exec.pause();
		} else {
			exec.stop();
		}
	}

	private void stop(MediaExecutor<?> exec) {
		exec.stop();
	}

}
