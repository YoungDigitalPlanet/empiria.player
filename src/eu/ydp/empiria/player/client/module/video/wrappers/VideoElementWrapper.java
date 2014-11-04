package eu.ydp.empiria.player.client.module.video.wrappers;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.VideoElement;
import eu.ydp.gwtutil.client.util.UserAgentChecker;

public class VideoElementWrapper {

	private static final String VIDEOJS_SETUP_ATTRIBUTE = "data-setup";
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

	public void setPoster(String posterUrl) {
		if (UserAgentChecker.isMobileUserAgent()) {
			videoElement.setPoster(posterUrl);
		} else {
			videoElement.setAttribute(VIDEOJS_SETUP_ATTRIBUTE, posterSetupAttributeValue(posterUrl));
		}
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

	private String posterSetupAttributeValue(String posterUrl) {
		return "{\"poster\" : \"" + posterUrl + "\" }";
	}

	public String getSource() {
		Element firstChild = videoElement.getFirstChildElement();
		String source = firstChild.getAttribute("src");
		return source;
	}
}
