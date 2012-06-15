package eu.ydp.empiria.player.client.module.media.button;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.media.client.MediaBase;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.RootPanel;

import eu.ydp.empiria.player.client.event.html5.HTML5MediaEvent;
import eu.ydp.empiria.player.client.event.html5.HTML5MediaEventHandler;
import eu.ydp.empiria.player.client.event.html5.HTML5MediaEventsType;
import eu.ydp.gwtutil.client.util.UserAgentChecker.MobileUserAgent;

public abstract class AbstractMediaScroll<T> extends MediaController<T> {
	private boolean pressed = false;
	private MediaBase media;
	private boolean mediaReady = false;
	private boolean initialized;
	HandlerRegistration durationchangeHandlerRegistration ;
	public AbstractMediaScroll(MobileUserAgent... supportedUserAgents) {
		setSupportedMobileAgents(supportedUserAgents);
		if (isSupported()) {
			sinkEvents(Event.TOUCHEVENTS | Event.ONMOUSEMOVE | Event.ONMOUSEDOWN | Event.ONMOUSEUP);
			RootPanel.get().addDomHandler(new MouseUpHandler() {
				@Override
				public void onMouseUp(MouseUpEvent event) {
					pressed = false;
					setPosition(event.getNativeEvent());
					event.stopPropagation();
				}
			}, MouseUpEvent.getType());
		}
	}

	/**
	 * metoda wywolywana gdy pojawi sie jedno z obslugiwanych zdarzen
	 *
	 * @param event
	 */
	protected abstract void setPosition(NativeEvent event);

	@Override
	public void onBrowserEvent(com.google.gwt.user.client.Event event) {
		switch (event.getTypeInt()) {
		case Event.ONMOUSEDOWN:
		case Event.ONTOUCHSTART:
			pressed = true;
			setPosition(event);
			break;
		case Event.ONTOUCHEND:
		case Event.ONMOUSEUP:
			pressed = false;
			setPosition(event);
			break;
		case Event.ONTOUCHMOVE:
		case Event.ONMOUSEMOVE:
			setPosition(event);
		}
		super.onBrowserEvent(event);
	}

	@Override
	public void setMedia(MediaBase media) {
		this.media = media;
		init();
	}

	@Override
	public void init() {
		if (!initialized) {
			initialized = true;
			// czekamy na informacje na temat dlugosci utworu
			durationchangeHandlerRegistration = media.addBitlessDomHandler(new HTML5MediaEventHandler() {
				@Override
				public void onEvent(HTML5MediaEvent event) {
					mediaReady = true;
					durationchangeHandlerRegistration.removeHandler();
				}
			}, HTML5MediaEvent.getType(HTML5MediaEventsType.durationchange));
		}

	}

	public MediaBase getMedia() {
		return media;
	}
	/**
	 * Czy przycisk myszy jest wcisniety
	 *
	 * @return
	 */
	public boolean isPressed() {
		return pressed;
	}

	/**
	 * Czy multimedia zostaly zaladowane
	 *
	 * @return
	 */
	public boolean isMediaReady() {
		return mediaReady;
	}
}
