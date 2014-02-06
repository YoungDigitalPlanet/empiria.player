package eu.ydp.empiria.player.client.controller.extensions.internal.media;

import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration.MediaType;
import eu.ydp.empiria.player.client.module.object.impl.EmbedAudioImpl;
import eu.ydp.empiria.player.client.module.object.impl.FlashVideoImpl;
import eu.ydp.empiria.player.client.util.SourceUtil;

public class OldSwfMediaExecutor extends AbstractNoControlExecutor {
	@Override
	public void init() {
		Widget widget = null;
		if (bmc.getMediaType() == MediaType.AUDIO) {
			EmbedAudioImpl audop = new EmbedAudioImpl();
			audop.setSrc(SourceUtil.getMpegSource(bmc.getSources()));
			widget = audop;
		} else {
			FlashVideoImpl video = new FlashVideoImpl();
			video.setSrc(SourceUtil.getMpegSource(bmc.getSources()));
			widget = video;
		}
		((OldSwfMediaWrapper) mediaWrapper).setMediaWidget(widget);

	}

}
