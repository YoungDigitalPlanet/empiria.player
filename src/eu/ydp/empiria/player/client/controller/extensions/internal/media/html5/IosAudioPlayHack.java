package eu.ydp.empiria.player.client.controller.extensions.internal.media.html5;

import com.google.common.base.Optional;
import com.google.gwt.event.dom.client.TouchStartEvent;
import com.google.gwt.event.dom.client.TouchStartHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.event.html5.HTML5MediaEvent;
import eu.ydp.empiria.player.client.event.html5.HTML5MediaEventHandler;
import eu.ydp.empiria.player.client.event.html5.HTML5MediaEventsType;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;
import eu.ydp.gwtutil.client.proxy.RootPanelDelegate;

public class IosAudioPlayHack implements TouchStartHandler, MediaEventHandler, HTML5MediaEventHandler {
	private HTML5AudioMediaExecutor audioMediaExecutor;

	private @Inject RootPanelDelegate rootPanelDelegate;
	private @Inject EventsBus eventsBus;
	private boolean selfplayFired;
	private Optional<com.google.web.bindery.event.shared.HandlerRegistration> eventBusHandlerRegistration = Optional.absent();
	private Optional<HandlerRegistration> touchHandlerRegistration = Optional.absent();
	private Optional<HandlerRegistration> playNativeEventHandlerRegistration = Optional.absent();

	public void applyHack(HTML5AudioMediaExecutor audioMediaExecutor) {
		removeTouchStartHandler();
		this.audioMediaExecutor = audioMediaExecutor;
		addTouchHandler();
	}

	private void addTouchHandler() {
		RootPanel rootPanel = rootPanelDelegate.getRootPanel();
		touchHandlerRegistration = Optional.of(rootPanel.addDomHandler(this, TouchStartEvent.getType()));
	}

	@Override
	public void onTouchStart(TouchStartEvent event) {
		removeTouchStartHandler();
		eventBusHandlerRegistration = Optional.of(addHandlerToEventBus(MediaEventTypes.PLAY));
		playNativeEventHandlerRegistration = Optional.of(addNativePlayEventHandler());
		audioMediaExecutor.playWithoutOnPlayEventPropagation();
		selfplayFired = true;
	}

	@Override
	public void onMediaEvent(MediaEvent event) {
		if (event.getType() == MediaEventTypes.PLAY) {
			removeHandlersIfPlayWasFiredFromOtherModule();
		}
	}

	@Override
	public void onEvent(HTML5MediaEvent event) {
		if (event.getType() == HTML5MediaEventsType.play) {
			removeHandlersAndStopAudioIfPlayWasFiredFromThisHack();
		}
	}

	private HandlerRegistration addNativePlayEventHandler() {
		return audioMediaExecutor.getMedia().addBitlessDomHandler(this, HTML5MediaEvent.getType(HTML5MediaEventsType.play));
	}

	private com.google.web.bindery.event.shared.HandlerRegistration addHandlerToEventBus(MediaEventTypes onPlayEvent) {
		com.google.web.bindery.event.shared.HandlerRegistration handlerRegistration = eventsBus.addHandlerToSource(MediaEvent.getType(onPlayEvent),
				audioMediaExecutor.getMediaWrapper(), this);
		return handlerRegistration;
	}

	private void removeHandlersAndStopAudioIfPlayWasFiredFromThisHack() {
		if (selfplayFired) {
			removeHandlersFromEventBus();
			removeNativeEventPlayEventHandler();
			audioMediaExecutor.stop();
			selfplayFired = false;
		}
	}

	private void removeHandlersIfPlayWasFiredFromOtherModule() {
		if (!selfplayFired) {
			removeTouchStartHandler();
			removeHandlersFromEventBus();
			removeNativeEventPlayEventHandler();
		}
	}

	private void removeNativeEventPlayEventHandler() {
		if (playNativeEventHandlerRegistration.isPresent()) {
			playNativeEventHandlerRegistration.get().removeHandler();
			playNativeEventHandlerRegistration = Optional.absent();
		}
	}

	private void removeTouchStartHandler() {
		if (touchHandlerRegistration.isPresent()) {
			touchHandlerRegistration.get().removeHandler();
			touchHandlerRegistration = Optional.absent();
		}
	}

	private void removeHandlersFromEventBus() {
		if (eventBusHandlerRegistration.isPresent()) {
			eventBusHandlerRegistration.get().removeHandler();
			eventBusHandlerRegistration = Optional.absent();
		}
	}


}
