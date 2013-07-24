package eu.ydp.empiria.player.client.animation;

import eu.ydp.empiria.player.client.util.geom.Size;

public class AnimationConfig {

	private static final int MS_IN_SECONDS = 1000;
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
		if (fps > 0){
			return MS_IN_SECONDS / fps;
		} else {
			return MS_IN_SECONDS;
		}
	}

	public Size getFrameSize() {
		return frameSize;
	}

	public String getSource() {
		return source;
	}
	
}
