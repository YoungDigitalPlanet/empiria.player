package eu.ydp.empiria.player.client.controller.extensions.internal.tutor;

import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.js.TutorJs;
import eu.ydp.empiria.player.client.util.geom.Size;

public class TutorPersonaProperties {
	
	private final TutorJs tutorJs;
	
	public TutorPersonaProperties(TutorJs tutorJs) {
		this.tutorJs = tutorJs;
	}

	public Size getAnimationSize() {
		return new Size(tutorJs.getWidth(), tutorJs.getHeight());
	}

	public int getAnimationFps() {
		return tutorJs.getFps();
	}

	public String getName() {
		return tutorJs.getName();
	}

}
