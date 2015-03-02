package eu.ydp.empiria.player.client.module.video.wrappers;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.VideoElement;
import com.google.inject.Provider;

public class VideoElementWrapperProvider implements Provider<VideoElementWrapper> {

	private static final String CLASS_NAME = "video-js";

	@Override
	public VideoElementWrapper get() {
		String playerId = Document.get().createUniqueId();
		VideoElement videoElem = Document.get().createVideoElement();
		videoElem.setId(playerId);
		videoElem.addClassName(CLASS_NAME);
		return new VideoElementWrapper(videoElem);
	}
}
