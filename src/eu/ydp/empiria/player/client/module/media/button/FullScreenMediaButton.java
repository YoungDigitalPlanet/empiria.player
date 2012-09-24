package eu.ydp.empiria.player.client.module.media.button;

import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.PlayerGinjector;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.media.fullscreen.VideoFullScreenHelper;
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
	private boolean isInfullScreen = false;
	private Element fullScreenTemplate;
	private MediaWrapper<?> fullScreenMediaWrapper;

	public FullScreenMediaButton() {
		super(PlayerGinjector.INSTANCE.getStyleNameConstants().QP_MEDIA_FULLSCREEN_BUTTON());
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
		if (isInfullScreen || fullScreen) {
			fullScreenHelper.closeFullScreen();
		} else if(!fullScreen){
			fullScreenHelper.openFullScreen(fullScreenMediaWrapper, fullScreenTemplate);
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
		return getMediaAvailableOptions().isFullScreenSupported();
	}

	@Override
	public void onMediaEvent(MediaEvent event) {
		if(event.getType() == MediaEventTypes.ON_FULL_SCREEN_OPEN){
			isInfullScreen = true;
		}else if(event.getType() == MediaEventTypes.ON_FULL_SCREEN_EXIT){
			isInfullScreen = false;
		}

	}

	public void setFullScreenTemplate(Element fullScreenTemplate) {
		this.fullScreenTemplate = fullScreenTemplate;
	}

}
