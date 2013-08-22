package eu.ydp.empiria.player.client.module.tutor.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.module.tutor.TutorCommand;
import eu.ydp.empiria.player.client.module.tutor.view.TutorView;
import eu.ydp.gwtutil.client.util.geom.Size;


public class ShowImageCommand implements TutorCommand {

	private final TutorView view;
	private final String assetPath;
	private final Size size;
	private boolean finished = false;

	@Inject
	public ShowImageCommand(@Assisted TutorView view, @Assisted String assetPath, @Assisted Size size) {
		this.view = view;
		this.assetPath = assetPath;
		this.size = size;
	}

	@Override
	public void execute() {
		view.setBackgroundImage(assetPath, size);
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
