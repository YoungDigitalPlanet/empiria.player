package eu.ydp.empiria.player.client.module.video.hack;

import static eu.ydp.gwtutil.client.util.UserAgentChecker.*;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.video.presenter.VideoPlayerAttacher;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;
import eu.ydp.gwtutil.client.util.UserAgentChecker.MobileUserAgent;

public class ReAttachVideoPlayerForIOSHack {

	private boolean isLoaded = false;
	private final EventsBus eventsBus;
	private final VideoPlayerAttacher videoPlayerAttacher;

	@Inject
	public ReAttachVideoPlayerForIOSHack(EventsBus eventsBus, @ModuleScoped VideoPlayerAttacher videoPlayerAttacher) {
		this.eventsBus = eventsBus;
		this.videoPlayerAttacher = videoPlayerAttacher;
	}

	public boolean isNeeded() {
		return isUserAgent(MobileUserAgent.SAFARI);
	}

	public void applyIfNeeded() {
		if (isNeeded()) {
			apply();
		}
	}

	public void apply() {
		eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.TEST_PAGE_LOADED), new PlayerEventHandler() {

			@Override
			public void onPlayerEvent(PlayerEvent event) {
				reAttachHackIfVideoPlayerIsLoaded();
			}

		}, new CurrentPageScope());
	}

	private void reAttachHackIfVideoPlayerIsLoaded() {
		if (isLoaded) {
			videoPlayerAttacher.attachNewWithReattachHack();
		} else {
			isLoaded = true;
		}
	}

}
