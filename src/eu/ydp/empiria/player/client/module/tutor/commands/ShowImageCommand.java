package eu.ydp.empiria.player.client.module.tutor.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.module.tutor.TutorEndHandler;
import eu.ydp.empiria.player.client.module.tutor.TutorCommand;
import eu.ydp.empiria.player.client.module.tutor.view.TutorView;
import eu.ydp.gwtutil.client.util.geom.Size;


public class ShowImageCommand implements TutorCommand {

	private final TutorView view;
	private final String assetPath;
	private final Size size;
	private final TutorEndHandler handler;
	private boolean finished = false;

	@Inject
	public ShowImageCommand(@Assisted TutorView view, @Assisted String assetPath, @Assisted Size size, @Assisted TutorEndHandler handler) {
		this.view = view;
		this.assetPath = assetPath;
		this.size = size;
		this.handler = handler;
	}

	@Override
	public void execute() {
		view.setBackgroundImage(assetPath, size);
		finished = true;
		handler.onEnd(false);
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
