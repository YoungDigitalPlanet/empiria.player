package eu.ydp.empiria.player.client.module.media.html5.reattachhack;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.media.Video;
import eu.ydp.empiria.player.client.media.VideoCreator;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.media.html5.AbstractHTML5MediaWrapper;
import eu.ydp.empiria.player.client.module.media.html5.HTML5VideoMediaWrapper;

class HTML5VideoRebuilder {

	private Video video;

	@Inject
	private VideoCreator videoCreator;

	public void recreateVideoWidget(HTML5VideoMediaWrapper mediaWrapper) {
		setVideo(getVideo(mediaWrapper));
		FlowPanel parent = getParentAndRemoveVideo(video);
		setVideo(recreateVideoAndUpdateEventBusSourceObject(video));
		insertVideoAndUpdateMediaWrapper(mediaWrapper, video, parent);
	}

	private MediaWrapper<?> getEventBusSourceObject(Video video) {
		return video.getEventBusSourceObject();
	}

	private FlowPanel getParentAndRemoveVideo(Video video) {
		FlowPanel parent = (FlowPanel) video.getParent();
		video.removeFromParent();
		return parent;
	}

	private void insertVideoAndUpdateMediaWrapper(AbstractHTML5MediaWrapper mediaWrapper, Video video, FlowPanel parent) {
		parent.insert(video, 0);
		mediaWrapper.setMediaObject(video);
	}

	private Video recreateVideoAndUpdateEventBusSourceObject(Video video) {
		MediaWrapper<?> eventBusSourceObject = getEventBusSourceObject(video);
		Video newVideo = videoCreator.createVideo();
		newVideo.setEventBusSourceObject(eventBusSourceObject);
		return newVideo;
	}

	private Video getVideo(HTML5VideoMediaWrapper mediaWrapper) {
		return (Video) mediaWrapper.getMediaObject();
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

}
