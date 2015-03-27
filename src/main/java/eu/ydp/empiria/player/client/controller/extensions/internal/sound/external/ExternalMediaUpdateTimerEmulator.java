package eu.ydp.empiria.player.client.controller.extensions.internal.sound.external;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.params.MediaStatus;
import eu.ydp.gwtutil.client.timer.Timer;

public class ExternalMediaUpdateTimerEmulator {

	private static final int DELAY_MILLIS = 250;

	@Inject
	private Timer timer;
	@Inject
	private Provider<ExternalMediaUpdateTimerEmulatorState> stateProvider;

	private EmulatedTimeUpdateListener listener;
	private ExternalMediaUpdateTimerEmulatorState state;

	private Runnable timerAction = new Runnable() {

		@Override
		public void run() {
			update();
		}
	};

	public void init(EmulatedTimeUpdateListener listener) {
		this.listener = listener;
		timer.init(timerAction);
	}

	public void run(int initialTimeMillis) {
		createAndInitState(initialTimeMillis);
		runTimer();
	}

	public void stop() {
		timer.cancel();
	}

	private void createAndInitState(int initialTimeMillis) {
		state = stateProvider.get();
		state.init(initialTimeMillis);
	}

	private void runTimer() {
		timer.scheduleRepeating(DELAY_MILLIS);
	}

	private void update() {
		final int currentMediaTimeMillis = state.findCurrentMediaTimeMillis();
		MediaStatus status = createMediaStatus(currentMediaTimeMillis);
		listener.emulatedTimeUpdate(status);
	}

	private MediaStatus createMediaStatus(final int currentMediaTimeMillis) {
		MediaStatus status = new MediaStatus() {

			@Override
			public int getCurrentTimeMillis() {
				return currentMediaTimeMillis;
			}
		};
		return status;
	}
}
