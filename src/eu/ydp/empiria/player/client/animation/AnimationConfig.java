package eu.ydp.empiria.player.client.animation;

import eu.ydp.empiria.player.client.util.geom.Size;

public class AnimationConfig {

	private final int fps;
	private final Size frameSize;
	private final String source;
	
	public AnimationConfig(int fps, Size frameSize, String source) {
		this.fps = fps;
		this.frameSize = frameSize;
		this.source = source;
	}

	public int getFps() {
		return fps;
	}
	
	public int getIntervalMs(){
		return 1000 / fps;
	}

	public Size getFrameSize() {
		return frameSize;
	}

	public String getSource() {
		return source;
	}
	
}
