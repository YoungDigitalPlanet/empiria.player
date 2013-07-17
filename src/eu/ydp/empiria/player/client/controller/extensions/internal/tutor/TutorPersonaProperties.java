package eu.ydp.empiria.player.client.controller.extensions.internal.tutor;

import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.js.TutorJs;
import eu.ydp.empiria.player.client.util.geom.Size;

public class TutorPersonaProperties {
	
	public static TutorPersonaProperties fromJs(TutorJs tutorJs){
		Size size = new Size(tutorJs.getWidth(), tutorJs.getHeight());
		int fps = tutorJs.getFps();
		String name = tutorJs.getName();
		return new TutorPersonaProperties(size, fps, name);
	}
	
	private final Size size;
	private final int fps;
	private final String name;
	
	private TutorPersonaProperties(Size size, int fps, String name) {
		super();
		this.size = size;
		this.fps = fps;
		this.name = name;
	}

	public Size getAnimationSize() {
		return size;
	}

	public int getAnimationFps() {
		return fps;
	}

	public String getName() {
		return name;
	}

}
