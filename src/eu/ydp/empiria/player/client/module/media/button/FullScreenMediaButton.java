package eu.ydp.empiria.player.client.module.media.button;

import eu.ydp.empiria.player.client.util.HTML5FullScreen;
import eu.ydp.empiria.player.client.util.HTML5FullScreen.FullScreenEvent;
import eu.ydp.empiria.player.client.util.HTML5FullScreen.FullScreenEventHandler;

/**
 * Przycisk przełączania pomiedzy trybem pełnoekranowym a zwyklym
 *
 *
 */
public class FullScreenMediaButton extends AbstractMediaButton<FullScreenMediaButton> {
	public FullScreenMediaButton() {
		super("qp-media-fullscreen-button");
	}

	@Override
	public FullScreenMediaButton getNewInstance() {
		return new FullScreenMediaButton();
	}

	@Override
	protected void onClick() {
		super.onClick();
		if (!HTML5FullScreen.isInFullScreen()) {
			HTML5FullScreen.requestFullScreen(getMedia().getParent().getElement());
		} else {
			HTML5FullScreen.exitFullScreen();
		}
	}

	@Override
	public void init() {
		HTML5FullScreen.addFullScreenEventHandler(new FullScreenEventHandler() {
			@Override
			public void handleEvent(FullScreenEvent event) {
				if (event.isInFullScreen()) {
					clicked = true;
					onClick();
				}
			}
		});
	}
	@Override
	public boolean isSupported() {
		return false;
	}
}
