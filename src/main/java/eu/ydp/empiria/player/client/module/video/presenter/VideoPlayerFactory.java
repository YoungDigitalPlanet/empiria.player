package eu.ydp.empiria.player.client.module.video.presenter;

import com.google.inject.Inject;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.gin.factory.VideoModuleFactory;
import eu.ydp.empiria.player.client.module.video.VideoElementWrapperBuilder;
import eu.ydp.empiria.player.client.module.video.structure.VideoBean;
import eu.ydp.empiria.player.client.module.video.view.VideoPlayer;
import eu.ydp.empiria.player.client.module.video.wrappers.VideoElementWrapper;

public class VideoPlayerFactory {

    @Inject
    private Provider<VideoElementWrapperBuilder> elementWrapperBuilderProvider;
    @Inject
    private VideoModuleFactory videoModuleFactory;

    public VideoPlayer create(VideoBean videoBean) {
        VideoElementWrapperBuilder videoElementBuilder = elementWrapperBuilderProvider.get();
        VideoElementWrapper videoElement = videoElementBuilder
                .withWidth(videoBean.getWidth())
                .withHeight(videoBean.getHeight())
                .withPoster(videoBean.getPoster())
                .withSources(videoBean.getSources()).build();

        return videoModuleFactory.createVideoPlayer(videoElement);
    }
}
