package eu.ydp.empiria.player.client.controller.feedback.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Joiner;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.callback.CallbackRecevier;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;

public class SoundPlayer {
	
	@Inject
	protected EventsBus eventsBus;
	
	// Cache dla wrapperow - do odegrania danego pliku bedzie uzywany zawsze ten sam wrapper.
	protected Map<String, MediaWrapper<?>> wrappers = new HashMap<String, MediaWrapper<?>>();
	
	protected class MediaWrapperHandler implements CallbackRecevier {
		protected String wrappersSourcesKey;
		
		@Override
		public void setCallbackReturnObject(Object object) {
			if (object instanceof MediaWrapper<?>) {
				MediaWrapper<?> mediaWrapper = (MediaWrapper<?>) object;
				attachMediaObject(mediaWrapper.getMediaObject());
				wrappers.put(wrappersSourcesKey, mediaWrapper);
				
				eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.PLAY, mediaWrapper), mediaWrapper);
			}
		}
		
		protected void attachMediaObject(Widget mediaObject) {
			FlowPanel panel = new FlowPanel();
			panel.getElement().getStyle().setWidth(1, Unit.PX);
			panel.getElement().getStyle().setHeight(1, Unit.PX);
			panel.getElement().getStyle().setVisibility(Visibility.HIDDEN);
			panel.getElement().getStyle().setOverflow(Overflow.HIDDEN);
			RootPanel.get().add(panel);
			panel.add(mediaObject);
		}

		public void setWrappersSourcesKey(String wrappersSourcesKey) {
			this.wrappersSourcesKey = wrappersSourcesKey;
		}
	}

	// Jesli mamy tylko zrodla plikow.
	public void play(List<String> sources) {
		Map<String, String> sourcesWithTypes = new HashMap<String, String>();
		for (String source : sources) {
			sourcesWithTypes.put(source, getMimeType(source));
		}
		
		this.play(getWrappersSourcesKey(sources), sourcesWithTypes);
	}
	
	// Jesli mamy zrodla plikow oraz ich MIME.
	public void play(Map<String, String> sourcesWithTypes) {
		String wrappersSourcesKey = getWrappersSourcesKey(new ArrayList<String>(sourcesWithTypes.keySet()));
		
		this.play(wrappersSourcesKey, sourcesWithTypes);
	}
	
	protected void play(String wrappersSourcesKey, Map<String, String> sourcesWithTypes) {
		if (wrappers.containsKey(wrappersSourcesKey)) {
			playSound(wrappers.get(wrappersSourcesKey));
		} else {
			createMediaWrapper(wrappersSourcesKey, sourcesWithTypes);
		}
	}
	
	protected void playSound(MediaWrapper<?> mediaWrapper) {
		eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.STOP, mediaWrapper), mediaWrapper);
		eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.PLAY, mediaWrapper), mediaWrapper);
	}

	protected void createMediaWrapper(String wrappersSourcesKey, Map<String, String> sourcesWithTypes) {
		BaseMediaConfiguration bmc = new BaseMediaConfiguration(sourcesWithTypes, true);
		MediaWrapperHandler callbackHandler = new MediaWrapperHandler();
		callbackHandler.setWrappersSourcesKey(wrappersSourcesKey);
		eventsBus.fireEvent(new PlayerEvent(PlayerEventTypes.CREATE_MEDIA_WRAPPER, bmc, callbackHandler));
	}
	
	// Klucz po ktorym przeszukiwany jest cache.
	protected String getWrappersSourcesKey(List<String> sources) {
		return Joiner.on(",").join(sources);
	}
	
	protected String getMimeType(String url) {
		String mimeType = "";
		String fileType = url.substring(url.length() - 3);
		
		if (fileType.equals("mp3")) {
			mimeType = "audio/mp4";
		} else if (fileType.equals("ogg")) {
			mimeType = "audio/ogg";
		}
		
		return mimeType;
	}
}
