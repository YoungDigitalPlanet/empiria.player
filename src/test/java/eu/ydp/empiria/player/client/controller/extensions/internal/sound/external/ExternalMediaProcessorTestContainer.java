package eu.ydp.empiria.player.client.controller.extensions.internal.sound.external;

import java.util.List;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Injector;

import eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.connector.MediaConnector;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.connector.MediaConnectorListener;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;

public class ExternalMediaProcessorTestContainer {

	private ExternalMediaProcessor processor;
	private MediaConnector connector;
	private EventsBus eventsBus;
	private MediaConnectorListener mediaConnectorListener;
	private TestConnectorForwarder connectorForwarder = new TestConnectorForwarder();
	private TestMediaWrapperCreator mediaWrapperCreator = new TestMediaWrapperCreator();

	public void init(Injector injector) {
		getDependencies(injector);
		init();
	}

	private void getDependencies(Injector injector) {
		processor = injector.getInstance(ExternalMediaProcessor.class);
		eventsBus = injector.getInstance(EventsBus.class);
		connector = injector.getInstance(MediaConnector.class);
		mediaConnectorListener = injector.getInstance(MediaConnectorListener.class);
	}

	private void init() {
		processor.initMediaProcessor();
		mediaWrapperCreator.init(eventsBus);
	}

	public void forwardPlayFromConnectorToListener() {
		connectorForwarder.forwardPlay(connector, mediaConnectorListener);
	}

	public void forwardPauseFromConnectorToListener() {
		connectorForwarder.forwardPause(connector, mediaConnectorListener);
	}

	public List<MediaWrapper<Widget>> createMediaWrappers(Iterable<? extends TestMedia> testMedias) {
		return mediaWrapperCreator.createMediaWrappers(testMedias);
	}

	public MediaWrapper<Widget> createMediaWrapper() {
		return mediaWrapperCreator.createMediaWrapper();
	}

	public MediaWrapper<Widget> createMediaWrapper(TestMedia testMedias) {
		return mediaWrapperCreator.createMediaWrapper(testMedias);
	}

	protected MediaConnector getConnector() {
		return connector;
	}

	protected void fireMediaEvent(MediaEventTypes type, MediaWrapper<Widget> mw) {
		eventsBus.fireEvent(new MediaEvent(type, mw));
	}

	protected void firePlayerEvent(PlayerEventTypes pet) {
		eventsBus.fireEvent(new PlayerEvent(pet));
	}

	protected void fireMediaEvent(MediaEvent me) {
		eventsBus.fireEvent(me);
	}

	protected MediaConnectorListener getMediaConnectorListener() {
		return mediaConnectorListener;
	}

	protected EventsBus getEventsBus() {
		return eventsBus;
	}
}
