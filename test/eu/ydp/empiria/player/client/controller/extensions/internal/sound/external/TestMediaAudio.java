package eu.ydp.empiria.player.client.controller.extensions.internal.sound.external;

import java.util.Collection;
import java.util.Map;

import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.gwtutil.client.collections.MapCreator;

public enum TestMediaAudio implements TestMedia {

	SINGLE_MP3_0(MapCreator.create("sound0.mp3", "audio/mp3").build(), false), SINGLE_MP3_1(MapCreator.create("sound1.mp3", "audio/mp3").build(), false), SINGLE_OGG_0(
			MapCreator.create("sound0.ogg", "audio/ogg").build(), false), SINGLE_OGG_1(MapCreator.create("sound1.ogg", "audio/ogg").build(), false), MULTI_MP3_OGG(
			MapCreator.create("sound0.mp3", "audio/mp3").put("sound0.ogg", "audio/ogg").build(), false);

	private BaseMediaConfiguration bmc;

	/**
	 * Media configuration for audio.
	 */
	private TestMediaAudio(final Map<String, String> sources, final boolean isFeedback) {
		bmc = new BaseMediaConfiguration(sources, isFeedback);
	}

	@Override
	public BaseMediaConfiguration getMediaConfiguration() {
		return bmc;
	}

	@Override
	public Collection<String> getSources() {
		return bmc.getSources().keySet();
	}

}
