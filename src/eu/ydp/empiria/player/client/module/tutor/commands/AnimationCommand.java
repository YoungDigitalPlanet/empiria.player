package eu.ydp.empiria.player.client.module.tutor.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.animation.Animation;
import eu.ydp.empiria.player.client.animation.AnimationEndHandler;
import eu.ydp.empiria.player.client.module.tutor.EndHandler;
import eu.ydp.empiria.player.client.module.tutor.TutorCommand;

public class AnimationCommand implements TutorCommand {

	private final Animation animation;
	private final EndHandler endHandler;
	private boolean finished = false;
	protected final AnimationEndHandler animationHandler = new AnimationEndHandler() {

		@Override
		public void onEnd() {
			finished = true;
			endHandler.onEnd();
		}
	};

	@Inject
	public AnimationCommand(@Assisted Animation animation, @Assisted EndHandler handler) {
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
