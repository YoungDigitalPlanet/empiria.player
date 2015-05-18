package eu.ydp.empiria.player.client.module.media.button;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.*;

public abstract class FullScreenMediaButton<H> extends AbstractMediaButton<H> implements MediaEventHandler {

	@Inject
	protected EventsBus eventsBus;
	@Inject
	protected PageScopeFactory scopeFactory;

	protected boolean fullScreenOpen = false;

	public FullScreenMediaButton(String baseStyleName) {
		super(baseStyleName);
	}

	@Override
	public void onMediaEvent(MediaEvent event) {
		if (event.getType() == MediaEventTypes.ON_FULL_SCREEN_OPEN) {
			fullScreenOpen = true;
		} else if (event.getType() == MediaEventTypes.ON_FULL_SCREEN_EXIT) {
			fullScreenOpen = false;
		}
	}

	public boolean isFullScreenOpen() {
		return fullScreenOpen;
	}

	@Override
	protected void onClick() {
		if (isFullScreenOpen() || isInFullScreen()) {
			closeFullScreen();
		} else {
			openFullScreen();
		}
	}

	protected abstract void openFullScreen();

	protected abstract void closeFullScreen();
}
