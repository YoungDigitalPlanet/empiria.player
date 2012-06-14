package eu.ydp.empiria.player.client.module.media.info;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.media.client.MediaBase;

import eu.ydp.empiria.player.client.event.html5.HTML5MediaEvent;
import eu.ydp.empiria.player.client.event.html5.HTML5MediaEventHandler;
import eu.ydp.empiria.player.client.event.html5.HTML5MediaEventsType;
import eu.ydp.empiria.player.client.module.media.button.AbstractMediaButton;

public class PositionInMediaStream extends AbstractMediaButton<PositionInMediaStream> {
	NumberFormat formatter = NumberFormat.getFormat("##00");

	public PositionInMediaStream() {
		super("qp-media-positioninstream");
	}

	@Override
	protected void onClick() {
	}

	private void setInnerText(double currentTime,double duration) {
		double timeModulo = currentTime % 60;
		StringBuilder innerText = new StringBuilder(formatter.format((currentTime - timeModulo) / 60f));
		innerText.append(":");
		innerText.append(formatter.format(timeModulo));
		innerText.append(" / ");
		timeModulo = duration % 60;
		innerText.append(formatter.format((duration - timeModulo) / 60f));
		innerText.append(":");
		innerText.append(formatter.format(timeModulo));
		getElement().setInnerText(innerText.toString());
	}

	@Override
	public void init() {
		final ScheduledCommand command = new ScheduledCommand() {
			int lastTime = 0;
			MediaBase media;
			@Override
			public void execute() {
				media = getMedia();
				double currentTime = media.getCurrentTime();
				if (currentTime > lastTime + 1 || currentTime < lastTime - 1) {
					lastTime = (int) currentTime;
					setInnerText(currentTime,media.getDuration());
				}
			}
		};
		HTML5MediaEventHandler handler = new HTML5MediaEventHandler() {
			@Override
			public void onEvent(HTML5MediaEvent event) {
				Scheduler.get().scheduleDeferred(command);
			}
		};

		getMedia().addBitlessDomHandler(handler, HTML5MediaEvent.getType(HTML5MediaEventsType.timeupdate));
		getMedia().addBitlessDomHandler(handler, HTML5MediaEvent.getType(HTML5MediaEventsType.durationchange));
		getElement().setInnerText("00:00 / 00:00");
	}

	@Override
	public PositionInMediaStream getNewInstance() {
		return new PositionInMediaStream();
	}

}
