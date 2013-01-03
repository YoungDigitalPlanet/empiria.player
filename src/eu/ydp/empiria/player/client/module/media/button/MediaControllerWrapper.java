package eu.ydp.empiria.player.client.module.media.button;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.module.media.MediaAvailableOptions;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;

public class MediaControllerWrapper<T  extends Widget> implements MediaController<T> {

	private final T widget;

	public MediaControllerWrapper(T widget) {
		this.widget = widget;
	}

	@Override
	public T getNewInstance() {
		return null;
	}

	@Override
	public boolean isSupported() {
		return true;
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	@Override
	public void setMediaDescriptor(MediaWrapper<?> mediaDescriptor) {
		//
	}

	@Override
	public MediaAvailableOptions getMediaAvailableOptions() {
		return null;
	}

	@Override
	public MediaWrapper<?> getMediaWrapper() {
		return null;
	}

	@Override
	public void setFullScreen(boolean fullScreen) {
		//
	}

	@Override
	public Element getElement() {
		return null;
	}

	@Override
	public void init() {
		//
	}

}
