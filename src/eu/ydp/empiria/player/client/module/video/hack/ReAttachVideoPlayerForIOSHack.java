package eu.ydp.empiria.player.client.module.video.hack;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.module.video.presenter.VideoPlayerBuilder;
import eu.ydp.empiria.player.client.module.video.view.VideoPlayer;
import eu.ydp.empiria.player.client.module.video.view.VideoView;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class ReAttachVideoPlayerForIOSHack {

	private boolean isLoaded = false;
	private final EventsBus eventsBus;
	private final VideoPlayerBuilder videoPlayerAttacher;
	private final Provider<CurrentPageScope> pageScopeProvider;

	@Inject
	public ReAttachVideoPlayerForIOSHack(EventsBus eventsBus, @ModuleScoped VideoPlayerBuilder videoPlayerBuilder,
			Provider<CurrentPageScope> pageScopeProvider) {
		this.eventsBus = eventsBus;
		this.videoPlayerAttacher = videoPlayerBuilder;
		this.pageScopeProvider = pageScopeProvider;
	}

	public void apply(final VideoView view) {
		eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.TEST_PAGE_LOADED), new PlayerEventHandler() {
			@Override
			public void onPlayerEvent(PlayerEvent event) {
				reAttachHackIfVideoPlayerIsLoaded(view);
			}
		}, pageScopeProvider.get());
	}

	private void reAttachHackIfVideoPlayerIsLoaded(VideoView view) {
		if (isLoaded) {
			VideoPlayer videoPlayer = videoPlayerAttacher.buildVideoPlayer();
			view.attachVideoPlayer(videoPlayer);
		} else {
			isLoaded = true;
		}
	}

}
