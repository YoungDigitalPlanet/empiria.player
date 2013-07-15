package eu.ydp.empiria.player.client.module.tutor.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.module.tutor.EndHandler;
import eu.ydp.empiria.player.client.module.tutor.TutorCommand;
import eu.ydp.empiria.player.client.module.tutor.view.TutorView;

public class ShowImageCommand implements TutorCommand {

	private final TutorView view;
	private final String assetPath;
	private final EndHandler endHandler;
	private boolean finished = false;

	@Inject
	public ShowImageCommand(@Assisted TutorView view, @Assisted String assetPath, @Assisted EndHandler endHandler) {
		this.view = view;
		this.assetPath = assetPath;
		this.endHandler = endHandler;
	}

	@Override
	public void execute() {
		view.setAnimationImage(assetPath);
		finished = true;
		endHandler.onEnd();
	}

	@Override
	public void terminate() {
		finished = true;
	}

	@Override
	public boolean isFinished() {
		return finished;
	}

}
