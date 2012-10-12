package eu.ydp.empiria.player.client.module.media.button;

import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.gin.PlayerGinjector;
import eu.ydp.empiria.player.client.media.Video;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.media.fullscreen.VideoFullScreenHelper;
import eu.ydp.empiria.player.client.module.media.html5.HTML5MediaWrapper;
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
	private final VideoFullScreenHelper fullScreenHelper = PlayerGinjector.INSTANCE.getVideoFullScreenHelper();
	protected final EventsBus eventsBus = PlayerGinjector.INSTANCE.getEventsBus();
	private boolean fullScreenOpen = false;
	private Element fullScreenTemplate;
	private MediaWrapper<?> mediaWrapper;
	private MediaWrapper<?> fullScreenMediaWrapper;

	public FullScreenMediaButton() {
		super(PlayerGinjector.INSTANCE.getStyleNameConstants().QP_MEDIA_FULLSCREEN_BUTTON());
	}
	
	public void setMediaWrapper(MediaWrapper<?> mediaDescriptor) {
		this.mediaWrapper = mediaDescriptor;
	}

	public void setFullScreenMediaWrapper(MediaWrapper<?> fullScreenMediaWrapper) {
		this.fullScreenMediaWrapper = fullScreenMediaWrapper;
	}

	@Override
	public FullScreenMediaButton getNewInstance() {
		return new FullScreenMediaButton();
	}

	@Override
	protected void onClick() {
		if (fullScreenOpen || isInFullScreen()) {
			fullScreenHelper.closeFullScreen();
		} else if (!isInFullScreen()) {
			fullScreenHelper.openFullScreen(fullScreenMediaWrapper, fullScreenTemplate);
			eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.PLAY, fullScreenMediaWrapper), fullScreenMediaWrapper);
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
