package eu.ydp.empiria.player.client.module.media.info;

import eu.ydp.empiria.player.client.PlayerGinjectorFactory;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.media.*;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;

/**
 * Widget wyswietlajacy pozycje w strumieniu oraz dlugosc calego strumienia
 * 
 */
public class PositionInMediaStream extends AbstractMediaTime<PositionInMediaStream> {
	protected EventsBus eventsBus = PlayerGinjectorFactory.getPlayerGinjector().getEventsBus();

	public PositionInMediaStream() {
		super(styleNames.QP_MEDIA_POSITIONINSTREAM());
	}

	@Override
	public void init() {
		AbstractMediaEventHandler handler = new AbstractMediaEventHandler() {
			// -1 aby przy pierwszym zdarzeniu pokazal sie timer
			int lastTime = -1;

			@Override
			public void onMediaEvent(MediaEvent event) {
				double currentTime = getMediaWrapper().getCurrentTime();
				if (currentTime > lastTime + 1 || currentTime < lastTime) {
					lastTime = (int) currentTime;
					double timeModulo = currentTime % 60;
					StringBuilder innerText = new StringBuilder(getInnerText((currentTime - timeModulo) / 60f, timeModulo));
					innerText.append(" / ");
					double duration = getMediaWrapper().getDuration();
					timeModulo = duration % 60;
					innerText.append(getInnerText((duration - timeModulo) / 60f, timeModulo));
					getElement().setInnerText(innerText.toString());
				}
			}
		};
		if (isSupported()) {
			CurrentPageScope scope = new CurrentPageScope();
			eventsBus.addAsyncHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_TIME_UPDATE), getMediaWrapper(), handler, scope);
			eventsBus.addAsyncHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_DURATION_CHANGE), getMediaWrapper(), handler, scope);
		}
	}

	@Override
	public PositionInMediaStream getNewInstance() {
		return new PositionInMediaStream();
	}
}
