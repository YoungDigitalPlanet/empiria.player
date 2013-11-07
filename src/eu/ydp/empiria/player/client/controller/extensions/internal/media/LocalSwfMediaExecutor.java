package eu.ydp.empiria.player.client.controller.extensions.internal.media;

import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration.MediaType;
import eu.ydp.empiria.player.client.module.object.impl.FlashLocalAudioImpl;
import eu.ydp.empiria.player.client.module.object.impl.FlashLocalVideoImpl;
import eu.ydp.empiria.player.client.util.SourceUtil;

/**
 * Executor dla trybu offline
 * 
 * @author plelakowski
 * 
 */
public class LocalSwfMediaExecutor extends AbstractNoControlExecutor {

	@Override
	public void init() {
		Widget widget = null;
		if (bmc.getMediaType() == MediaType.AUDIO) {
			FlashLocalAudioImpl audop = new FlashLocalAudioImpl();
			audop.setSrc(SourceUtil.getMpegSource(bmc.getSources()));
			widget = audop;
		} else {
			FlashLocalVideoImpl video = new FlashLocalVideoImpl(bmc);
			video.setSrc(SourceUtil.getMpegSource(bmc.getSources()));
			widget = video;
		}
		((LocalSwfMediaWrapper) mediaWrapper).setMediaWidget(widget);
	}
}
