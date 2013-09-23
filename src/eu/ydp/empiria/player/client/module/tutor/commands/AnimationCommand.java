package eu.ydp.empiria.player.client.module.tutor.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.module.tutor.TutorEndHandler;
import eu.ydp.empiria.player.client.module.tutor.TutorCommand;
import eu.ydp.gwtutil.client.animation.Animation;
import eu.ydp.gwtutil.client.animation.AnimationEndHandler;

public class AnimationCommand implements TutorCommand {

	private final Animation animation;
	private final TutorEndHandler endHandler;
	private boolean finished = false;
	protected final AnimationEndHandler animationHandler = new AnimationEndHandler() {

		@Override
		public void onEnd() {
			finished = true;
			endHandler.onEnd(true);
		}
	};

	@Inject
	public AnimationCommand(@Assisted Animation animation, @Assisted TutorEndHandler handler) {
		this.animation = animation;
		this.endHandler = handler;
	}

	@Override
	public void execute() {
		animation.start(animationHandler);
	}

	@Override
	public void terminate() {
		animation.terminate();
		finished = true;
	}

	@Override
	public boolean isFinished() {
		return finished;
	}

}
