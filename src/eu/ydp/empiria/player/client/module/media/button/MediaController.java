package eu.ydp.empiria.player.client.module.media.button;

import com.google.gwt.user.client.ui.Composite;

import eu.ydp.empiria.player.client.PlayerGinjector;
import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.media.MediaAvailableOptions;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;

/**
 * Kontroler mediow
 *
 * @param <T>
 */
public abstract class MediaController<T> extends Composite implements Factory<T>, SupportedAction<T> {
	protected final static String CLICK_SUFFIX = "-click";
	protected final static String HOVER_SUFFIX = "-hover";
	protected final static String UNSUPPORTED_SUFFIX = "-unsupported"; //NOPMD
	protected final static StyleNameConstants styleNames = PlayerGinjector.INSTANCE.getStyleNameConstants(); //NOPMD
	private MediaAvailableOptions availableOptions;
	private MediaWrapper<?> mediaWrapper = null;

	/**
	 * przekazuje obiekt multimediow na jakim ma pracowac kontrolka
	 *
	 * @param mediaDescriptor
	 */
	public  void setMediaDescriptor(MediaWrapper<?> mediaDescriptor){
		this.mediaWrapper = mediaDescriptor;
		availableOptions = mediaDescriptor.getMediaAvailableOptions();
	}

	/**
	 * inicjalizacja kontrolki
	 */
	public abstract void init();

	/**
	 * obiekt opisujacy funkcje dostepne dla podlaczonego zasobu
	 * @return
	 */
	public MediaAvailableOptions getMediaAvailableOptions() {
		return availableOptions;
	}

	/**
	 * Zwraca
	 * @return
	 */
	public MediaWrapper<?> getMediaWrapper() {
		return mediaWrapper;
	}


}
