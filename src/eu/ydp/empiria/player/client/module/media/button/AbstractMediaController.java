package eu.ydp.empiria.player.client.module.media.button;

import com.google.gwt.user.client.ui.Composite;

import eu.ydp.empiria.player.client.PlayerGinjector;
import eu.ydp.empiria.player.client.module.media.MediaAvailableOptions;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;

public abstract class AbstractMediaController<T> extends Composite implements  MediaController<T> {
	protected final static String CLICK_SUFFIX = "-click";
	protected final static String HOVER_SUFFIX = "-hover";
	protected final static String FULL_SCREEN_SUFFIX = "-fullscreen";
	protected final static String UNSUPPORTED_SUFFIX = "-unsupported";
	protected final static StyleNameConstants styleNames = PlayerGinjector.INSTANCE.getStyleNameConstants(); //NOPMD
	private MediaAvailableOptions availableOptions;
	private MediaWrapper<?> mediaWrapper = null;
	protected boolean fullScreen = false;

	/* (non-Javadoc)
	 * @see eu.ydp.empiria.player.client.module.media.button.MediaController#setMediaDescriptor(eu.ydp.empiria.player.client.module.media.MediaWrapper)
	 */
	@Override
	public  void setMediaDescriptor(MediaWrapper<?> mediaDescriptor){
		this.mediaWrapper = mediaDescriptor;
		availableOptions = mediaDescriptor.getMediaAvailableOptions();
	}

	/**
	 * inicjalizacja kontrolki
	 */
	@Override
	public abstract void init();

	/* (non-Javadoc)
	 * @see eu.ydp.empiria.player.client.module.media.button.MediaController#getMediaAvailableOptions()
	 */
	@Override
	public MediaAvailableOptions getMediaAvailableOptions() {
		return availableOptions;
	}

	/* (non-Javadoc)
	 * @see eu.ydp.empiria.player.client.module.media.button.MediaController#getMediaWrapper()
	 */
	@Override
	public MediaWrapper<?> getMediaWrapper() {
		return mediaWrapper;
	}

	@Override
	public void setFullScreen(boolean fullScreen) {
		this.fullScreen = fullScreen;
		setStyleNames();
	}

	public abstract void setStyleNames();
}