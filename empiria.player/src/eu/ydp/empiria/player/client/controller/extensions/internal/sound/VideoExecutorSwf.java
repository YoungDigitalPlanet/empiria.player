package eu.ydp.empiria.player.client.controller.extensions.internal.sound;

import com.google.gwt.user.client.ui.FlowPanel;

import eu.ydp.empiria.gwtflashmedia.client.FlashVideo;
import eu.ydp.empiria.gwtflashmedia.client.FlashVideoFactory;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.SwfMediaWrapper;
import eu.ydp.empiria.player.client.util.SourceUtil;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;

public class VideoExecutorSwf extends ExecutorSwf {
	boolean playing = false;

	@Override
	public void init() {
		FlowPanel fp = new FlowPanel();
		FlashVideo video = FlashVideoFactory.createVideo(SourceUtil.getMpegSource(baseMediaConfiguration.getSources()), fp);
		video.setSize(baseMediaConfiguration.getWidth(), baseMediaConfiguration.getHeight());
		flashMedia = video;
		if(this.mediaWrapper instanceof SwfMediaWrapper){
			((SwfMediaWrapper) this.mediaWrapper).setMediaWidget(fp);
		}
		super.init();
	}

	@Override
	public void play(String src) {
		flashMedia.setSrc(src);
		flashMedia.play();

	}

	@Override
	public void stop() {
		flashMedia.stop();
		eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.ON_STOP, getMediaWrapper()), getMediaWrapper());
	}

}
