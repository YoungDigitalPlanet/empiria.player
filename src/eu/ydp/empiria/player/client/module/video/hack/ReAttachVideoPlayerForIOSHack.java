package eu.ydp.empiria.player.client.module.video.hack;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.module.video.presenter.VideoPlayerAttacher;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class ReAttachVideoPlayerForIOSHack {

	private boolean isLoaded = false;
	private final EventsBus eventsBus;
	private final VideoPlayerAttacher videoPlayerAttacher;
	private final Provider<CurrentPageScope> pageScopeProvider;

	@Inject
	public ReAttachVideoPlayerForIOSHack(EventsBus eventsBus, @ModuleScoped VideoPlayerAttacher videoPlayerAttacher,
			Provider<CurrentPageScope> pageScopeProvider) {
		this.eventsBus = eventsBus;
		this.videoPlayerAttacher = videoPlayerAttacher;
		this.pageScopeProvider = pageScopeProvider;
	}

	public void apply() {
		eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.TEST_PAGE_LOADED), new PlayerEventHandler() {

			@Override
			public void onPlayerEvent(PlayerEvent event) {
				reAttachHackIfVideoPlayerIsLoaded();
			}

		}, pageScopeProvider.get());
	}

	private void reAttachHackIfVideoPlayerIsLoaded() {
		if (isLoaded) {
			videoPlayerAttacher.attachNew();
		} else {
			isLoaded = true;
		}
	}

}
