package eu.ydp.empiria.player.client.module.tutor.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.module.tutor.TutorCommand;
import eu.ydp.empiria.player.client.module.tutor.view.TutorView;

public class ShowImageCommand implements TutorCommand {

	private final TutorView view;
	private final String assetPath;
	private boolean finished = false;

	@Inject
	public ShowImageCommand(@Assisted TutorView view, @Assisted String assetPath) {
		this.view = view;
		this.assetPath = assetPath;
	}

	@Override
	public void execute() {
		view.setAnimationImage(assetPath);
		finished = true;
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
