package eu.ydp.empiria.player.client.module.media.button;

import eu.ydp.empiria.player.client.event.html5.HTML5MediaEvent;
import eu.ydp.empiria.player.client.event.html5.HTML5MediaEventHandler;
import eu.ydp.empiria.player.client.event.html5.HTML5MediaEventsType;



/**
 *
 * przycisk playPause
 * @author plelakowski
 *
 */
public class PlayPauseMediaButton extends AbstractMediaButton<PlayPauseMediaButton>{
	public PlayPauseMediaButton() {
		super("qp-media-play-pause");
	}

	@Override
	protected void onClick() {
		super.onClick();
		if(clicked){
			getMedia().play();
		}else{
			getMedia().pause();
		}
	}

	@Override
	public void init() {
		HTML5MediaEventHandler handler = new HTML5MediaEventHandler() {

			@Override
			public void onEvent(HTML5MediaEvent event) {
				clicked = true;
				changeStyleForClick();
				clicked = false;
			}
		};

		getMedia().addBitlessDomHandler(handler, HTML5MediaEvent.getType(HTML5MediaEventsType.pause));
		getMedia().addBitlessDomHandler(handler, HTML5MediaEvent.getType(HTML5MediaEventsType.ended));
	}

	@Override
	public PlayPauseMediaButton getNewInstance() {
		return new PlayPauseMediaButton();
	}

}
