package eu.ydp.empiria.player.client.module.video.wrappers;

import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.VideoElement;

public class VideoElementWrapper {

	private final VideoElement videoElement;

	public VideoElementWrapper(VideoElement videoElement) {
		this.videoElement = videoElement;
	}

	public void setWidth(int width) {
		videoElement.setWidth(width);
	}

	public void setHeight(int height) {
		videoElement.setHeight(height);
	}

	public void setControls(boolean controls) {
		videoElement.setControls(controls);
	}

	public boolean addClassName(String className) {
		return videoElement.addClassName(className);
	}

	public void setPreload(String preload) {
		videoElement.setPreload(preload);
	}

	public void setPoster(String poster) {
		videoElement.setPoster(poster);
	}

	public <T extends Node> T appendChild(T newChild) {
		return videoElement.appendChild(newChild);
	}

	public String getId() {
		return videoElement.getId();
	}

	public Node asNode() {
		return videoElement;
	}
}
