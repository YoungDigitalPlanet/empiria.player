package eu.ydp.empiria.player.client.module.media.button;

import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.media.fullscreen.VideoFullScreenHelper;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;

/**
 * Przycisk przelaczania pomiedzy trybem pelnoekranowym a zwyklym
 *
 *
 *
 */
public class FullScreenMediaButton extends AbstractMediaButton<FullScreenMediaButton> implements MediaEventHandler {
	@Inject
	private VideoFullScreenHelper fullScreenHelper;
	@Inject
	protected EventsBus eventsBus;
	private boolean fullScreenOpen = false;
	private Element fullScreenTemplate;
	private MediaWrapper<?> mediaWrapper;
	private MediaWrapper<?> fullScreenMediaWrapper;

	@Inject
	Provider<FullScreenMediaButton> buttonProvider;

	@Inject
	public FullScreenMediaButton(StyleNameConstants styleNames) {
		super(styleNames.QP_MEDIA_FULLSCREEN_BUTTON());
	}

	public void setMediaWrapper(MediaWrapper<?> mediaDescriptor) {
		this.mediaWrapper = mediaDescriptor;
	}

	public void setFullScreenMediaWrapper(MediaWrapper<?> fullScreenMediaWrapper) {
		this.fullScreenMediaWrapper = fullScreenMediaWrapper;
	}

	@Override
	public FullScreenMediaButton getNewInstance() {
		return buttonProvider.get();
	}

	@Override
	protected void onClick() {
		if (fullScreenOpen || isInFullScreen()) {
			fullScreenHelper.closeFullScreen();
		} else if (!isInFullScreen()) {
			fullScreenHelper.openFullScreen(fullScreenMediaWrapper, mediaWrapper, fullScreenTemplate);
		}
	}

	@Override
	public void init() {
		super.init();
		eventsBus.addHandler(MediaEvent.getType(MediaEventTypes.ON_FULL_SCREEN_EXIT), this, new CurrentPageScope());
		eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_FULL_SCREEN_OPEN), getMediaWrapper(), this, new CurrentPageScope());
	}

	@Override
	public boolean isSupported() {
		return getMediaAvailableOptions().isFullScreenSupported() && (fullScreenTemplate != null || isInFullScreen());
	}

	@Override
	public void onMediaEvent(MediaEvent event) {
		if (event.getType() == MediaEventTypes.ON_FULL_SCREEN_OPEN) {
			fullScreenOpen = true;
		} else if (event.getType() == MediaEventTypes.ON_FULL_SCREEN_EXIT) {
			fullScreenOpen = false;
		}

	}

	public void setFullScreenTemplate(Element fullScreenTemplate) {
		this.fullScreenTemplate = fullScreenTemplate;
	}

}
