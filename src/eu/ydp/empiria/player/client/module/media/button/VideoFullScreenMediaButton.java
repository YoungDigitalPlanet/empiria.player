package eu.ydp.empiria.player.client.module.media.button;

import com.google.gwt.user.client.History;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.media.fullscreen.VideoFullScreenHelper;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;
import eu.ydp.gwtutil.client.util.UserAgentChecker;

/**
 * Przycisk przelaczania pomiedzy trybem pelnoekranowym a zwyklym
 */
public class VideoFullScreenMediaButton extends FullScreenMediaButton<VideoFullScreenMediaButton> implements MediaEventHandler {
	@Inject
	private VideoFullScreenHelper fullScreenHelper;


	private Element fullScreenTemplate;
	private MediaWrapper<?> mediaWrapper;
	private MediaWrapper<?> fullScreenMediaWrapper;

	@Inject
	Provider<VideoFullScreenMediaButton> buttonProvider;

	@Inject
	public VideoFullScreenMediaButton(StyleNameConstants styleNames) {
		super(styleNames.QP_MEDIA_FULLSCREEN_BUTTON());
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

	@Override
	public void init() {
		super.init();
		eventsBus.addHandler(MediaEvent.getType(MediaEventTypes.ON_FULL_SCREEN_EXIT), this, scopeFactory.getCurrentPageScope());
		eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_FULL_SCREEN_OPEN), getMediaWrapper(), this, scopeFactory.getCurrentPageScope());

	}
	
	@Override
	protected void openFullScreen() {
		fullScreenHelper.openFullScreen(fullScreenMediaWrapper, mediaWrapper, fullScreenTemplate);
	}

	@Override
	protected void closeFullScreen() {
		fullScreenHelper.closeFullScreen();
	}

}
