package eu.ydp.empiria.player.client.controller.extensions.internal.sound.external;

import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration.MediaType;
import eu.ydp.gwtutil.client.collections.MapCreator;

import java.util.Collection;
import java.util.Map;

public enum TestMediaVideo implements TestMedia {
    SINGLE_MP4(MapCreator.create("video0.mp4", "video/mp4").build()), SINGLE_OGG(MapCreator.create("video0.ogg", "video/ogg").build()), MULTI_MP4_OGG(
            MapCreator.create("video0.mp4", "video/mp4").put("video0.ogg", "video/ogg").build());

    private BaseMediaConfiguration bmc;

    /**
     * Media configuration for video.
     */
    private TestMediaVideo(final Map<String, String> sources) {
        bmc = new BaseMediaConfiguration(sources, MediaType.VIDEO, "", 640, 480, false, false, "");
    }

    @Override
    public BaseMediaConfiguration getMediaConfiguration() {
        return bmc;
    }

    @Override
    public Collection<String> getSources() {
        return bmc.getSources().values();
    }
}
