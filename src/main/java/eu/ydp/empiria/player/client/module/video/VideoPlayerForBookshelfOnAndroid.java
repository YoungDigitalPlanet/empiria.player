package eu.ydp.empiria.player.client.module.video;

import com.google.gwt.dom.client.NativeEvent;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.external.FullscreenVideoConnector;
import eu.ydp.empiria.player.client.module.video.view.VideoPlayer;
import eu.ydp.empiria.player.client.module.video.view.VideoView;
import eu.ydp.gwtutil.client.event.factory.Command;

import java.util.List;

public class VideoPlayerForBookshelfOnAndroid {

    private final FullscreenVideoConnector fullscreenVideoConnector;
    private final String playerId;
    private final List<String> sources;

    @Inject
    public VideoPlayerForBookshelfOnAndroid(@Assisted VideoPlayer videoPlayer, FullscreenVideoConnector fullscreenVideoConnector,
                                            SourceForBookshelfFilter sourceForBookshelfFilter) {
        this.sources = sourceForBookshelfFilter.getFilteredSources(videoPlayer.getSources());
        this.playerId = videoPlayer.getId();
        this.fullscreenVideoConnector = fullscreenVideoConnector;
    }

    public void init(VideoView view) {
        view.preparePlayForBookshelf(new Command() {
            @Override
            public void execute(NativeEvent nativeEvent) {
                if (!sources.isEmpty()) {
                    fullscreenVideoConnector.openFullscreen(playerId, sources, 0);
                }
            }
        });
    }
}
