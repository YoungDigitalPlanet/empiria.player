package eu.ydp.empiria.player.client.controller.extensions.internal.tutor;

import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.js.TutorJs;
import eu.ydp.gwtutil.client.util.geom.Size;


public class TutorPersonaProperties {

	public static TutorPersonaProperties fromJs(TutorJs tutorJs, int index){
		Size size = new Size(tutorJs.getWidth(), tutorJs.getHeight());
		int fps = tutorJs.getFps();
		String name = tutorJs.getName();
		boolean interactive = tutorJs.isInteractive();
		String avatar = tutorJs.getAvatar();
		return new TutorPersonaProperties(index, size, fps, name, interactive, avatar);
	}

	private final Size size;
	private final int fps;
	private final String name;
	private final boolean interactive;
	private final String avatarFilename;
	private final int index;

	public TutorPersonaProperties(int index, Size size, int fps, String name, boolean interactive, String avatarFilename) {
		this.index = index;
		this.size = size;
		this.fps = fps;
		this.name = name;
		this.interactive = interactive;
		this.avatarFilename = avatarFilename;
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

	public boolean isInteractive() {
		return interactive;
	}

	public String getAvatarFilename() {
		return avatarFilename;
	}

	public int getIndex() {
		return index;
	}
}
