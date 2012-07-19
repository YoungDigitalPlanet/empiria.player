package eu.ydp.empiria.player.client.module.media.button;

import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.PlayerGinjector;
import eu.ydp.empiria.player.client.util.HTML5FullScreen;
import eu.ydp.empiria.player.client.util.HTML5FullScreen.FullScreenEvent;
import eu.ydp.empiria.player.client.util.HTML5FullScreen.FullScreenEventHandler;

/**
 * Przycisk przelaczania pomiedzy trybem pelnoekranowym a zwyklym
 *
 *
 */
public class FullScreenMediaButton extends AbstractMediaButton<FullScreenMediaButton> {
	private Widget elementToShow = null;

	public FullScreenMediaButton() {
		this(null);
	}

	public FullScreenMediaButton(Widget elementToShow) {
		super(PlayerGinjector.INSTANCE.getStyleNameConstants().QP_MEDIA_FULLSCREEN_BUTTON());
		this.elementToShow = elementToShow;
	}

	@Override
	public FullScreenMediaButton getNewInstance() {
		return new FullScreenMediaButton();
	}

	@Override
	protected void onClick() {
		if (!HTML5FullScreen.isInFullScreen()) {
			HTML5FullScreen.requestFullScreen(getElement().getOffsetParent());
		} else {
			HTML5FullScreen.exitFullScreen();
		}
	}

	@Override
	public void init() {
		super.init();
		if (elementToShow == null && getMediaWrapper() != null) {
			elementToShow = getMediaWrapper().getMediaObject();
		}
		HTML5FullScreen.addFullScreenEventHandler(new FullScreenEventHandler() {
			@Override
			public void handleEvent(FullScreenEvent event) {
				if (event.isInFullScreen()) {
					setActive(true);
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
