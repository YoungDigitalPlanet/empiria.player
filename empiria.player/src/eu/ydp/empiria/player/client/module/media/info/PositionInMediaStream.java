package eu.ydp.empiria.player.client.module.media.info;

import com.google.gwt.i18n.client.NumberFormat;

import eu.ydp.empiria.player.client.PlayerGinjector;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.AbstractMediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;

/**
 * Widget wyswietlajacy pozycje w strumieniu oraz dlugosc calego strumienia
 *
 */
public class PositionInMediaStream extends AbstractMediaTime<PositionInMediaStream> {
	NumberFormat formatter = NumberFormat.getFormat("##00");
	protected EventsBus eventsBus = PlayerGinjector.INSTANCE.getEventsBus();

	public PositionInMediaStream() {
		super("qp-media-positioninstream");
	}

	@Override
	public void init() {
		AbstractMediaEventHandler handler = new AbstractMediaEventHandler() {
			//-1 aby przy pierwszym zdarzeniu pokazal sie timer
			int lastTime = -1;

			@Override
			public void onMediaEvent(MediaEvent event) {
				double currentTime = getMediaWrapper().getCurrentTime();
				if (currentTime > lastTime + 1 || currentTime < lastTime - 1) {
					lastTime = (int) currentTime;
					double timeModulo = currentTime % 60;
					String innerText = getInnerText((currentTime - timeModulo) / 60f, timeModulo);
					innerText += " / ";
					double duration = getMediaWrapper().getDuration();
					timeModulo = duration % 60;
					innerText += getInnerText((duration - timeModulo) / 60f, timeModulo);
					getElement().setInnerText(innerText);
				}
			}
		};
		if (isSupported()) {
			eventsBus.addAsyncHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_TIME_UPDATE), getMediaWrapper(), handler);
			eventsBus.addAsyncHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_DURATION_CHANGE), getMediaWrapper(), handler);
		}
	}

	@Override
	public PositionInMediaStream getNewInstance() {
		return new PositionInMediaStream();
	}
}
