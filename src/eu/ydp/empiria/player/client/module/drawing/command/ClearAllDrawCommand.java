package eu.ydp.empiria.player.client.module.drawing.command;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.drawing.view.DrawCanvas;


public class ClearAllDrawCommand implements DrawCommand {
	@Inject private DrawCanvas view;

	@Override
	public void execute() {
		view.clear();
	}

}
