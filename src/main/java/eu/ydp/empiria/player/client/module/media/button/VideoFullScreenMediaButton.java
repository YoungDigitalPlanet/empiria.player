package eu.ydp.empiria.player.client.module.media.button;

import com.google.gwt.xml.client.Element;
import com.google.inject.*;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.media.fullscreen.VideoFullScreenHelper;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.*;

/**
 * Przycisk przelaczania pomiedzy trybem pelnoekranowym a zwyklym
 */
public class VideoFullScreenMediaButton extends AbstractMediaButton<VideoFullScreenMediaButton> implements MediaEventHandler {

	private VideoFullScreenHelper fullScreenHelper;
	private EventsBus eventsBus;
	private Provider<VideoFullScreenMediaButton> buttonProvider;
	private PageScopeFactory scopeFactory;

	private Element fullScreenTemplate;
	private MediaWrapper<?> mediaWrapper;
	private MediaWrapper<?> fullScreenMediaWrapper;
	protected boolean fullScreenOpen = false;

	@Inject
	public VideoFullScreenMediaButton(StyleNameConstants styleNames, VideoFullScreenHelper fullScreenHelper, EventsBus eventsBus,
			Provider<VideoFullScreenMediaButton> buttonProvider, PageScopeFactory scopeFactory) {
		super(styleNames.QP_MEDIA_FULLSCREEN_BUTTON());

		this.fullScreenHelper = fullScreenHelper;
		this.eventsBus = eventsBus;
		this.buttonProvider = buttonProvider;
		this.scopeFactory = scopeFactory;
	}

	@Override
	public void init() {
		super.init();
		eventsBus.addHandler(MediaEvent.getType(MediaEventTypes.ON_FULL_SCREEN_EXIT), this, scopeFactory.getCurrentPageScope());
		eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_FULL_SCREEN_OPEN), getMediaWrapper(), this, scopeFactory.getCurrentPageScope());
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

	public void setMediaWrapper(MediaWrapper<?> mediaDescriptor) {
		this.mediaWrapper = mediaDescriptor;
	}

	public void setFullScreenMediaWrapper(MediaWrapper<?> fullScreenMediaWrapper) {
		this.fullScreenMediaWrapper = fullScreenMediaWrapper;
	}

	@Override
	public VideoFullScreenMediaButton getNewInstance() {
		return buttonProvider.get();
	}

	@Override
	public boolean isSupported() {
		return getMediaAvailableOptions().isFullScreenSupported() && (fullScreenTemplate != null || isInFullScreen());
	}

	public void setFullScreenTemplate(Element fullScreenTemplate) {
		this.fullScreenTemplate = fullScreenTemplate;
	}

	private void openFullScreen() {
		fullScreenHelper.openFullScreen(fullScreenMediaWrapper, mediaWrapper, fullScreenTemplate);
	}

	private void closeFullScreen() {
		fullScreenHelper.closeFullScreen();
	}
}
