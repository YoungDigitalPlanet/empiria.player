package eu.ydp.empiria.player.client.module.external.sound;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.media.MediaWrapperController;

public class MediaWrapperSoundActions {
	@Inject
	private MediaWrapperController mediaWrapperController;

	private MediaWrapperAction playAction = new MediaWrapperAction() {
		@Override
		public void execute(MediaWrapper<Widget> wrapper) {
			mediaWrapperController.stopAndPlay(wrapper);
		}
	};

	private MediaWrapperAction playLoopedAction = new MediaWrapperAction() {
		@Override
		public void execute(MediaWrapper<Widget> wrapper) {
			mediaWrapperController.playLooped(wrapper);
		}
	};

	private MediaWrapperAction stopAction = new MediaWrapperAction() {
		@Override
		public void execute(MediaWrapper<Widget> wrapper) {
			mediaWrapperController.stop(wrapper);
		}
	};

	private MediaWrapperAction pauseAction = new MediaWrapperAction() {
		@Override
		public void execute(MediaWrapper<Widget> wrapper) {
			mediaWrapperController.pause(wrapper);
		}
	};

	private MediaWrapperAction resumeAction = new MediaWrapperAction() {
		@Override
		public void execute(MediaWrapper<Widget> wrapper) {
			mediaWrapperController.resume(wrapper);
		}
	};

	public MediaWrapperAction getPlayAction() {
		return playAction;
	}

	public MediaWrapperAction getPlayLoopedAction() {
		return playLoopedAction;
	}

	public MediaWrapperAction getStopAction() {
		return stopAction;
	}

	public MediaWrapperAction getPauseAction() {
		return pauseAction;
	}

	public MediaWrapperAction getResumeAction() {
		return resumeAction;
	}
}
