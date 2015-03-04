package eu.ydp.empiria.player.client.controller.extensions.internal.sound;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.FlowPanel;

import eu.ydp.empiria.gwtflashmedia.client.FlashVideo;
import eu.ydp.empiria.gwtflashmedia.client.FlashVideoFactory;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.SwfMediaWrapper;
import eu.ydp.empiria.player.client.media.texttrack.TextTrack;
import eu.ydp.empiria.player.client.media.texttrack.TextTrackCue;
import eu.ydp.empiria.player.client.media.texttrack.TextTrackKind;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;

public class VideoExecutorSwf extends ExecutorSwf {

	@Override
	public void init() {
		FlowPanel flowPanel = new FlowPanel();
		FlashVideo video = FlashVideoFactory.createVideo(source, flowPanel, baseMediaConfiguration.getWidth(), baseMediaConfiguration.getHeight());
		flashMedia = video;
		if (this.mediaWrapper instanceof SwfMediaWrapper) {
			((SwfMediaWrapper) this.mediaWrapper).setMediaWidget(flowPanel);
		}
		if (baseMediaConfiguration.getNarrationText().trim().length() > 0) {
			final TextTrack textTrack = textTrackFactory.getTextTrack(TextTrackKind.SUBTITLES, mediaWrapper);
			// FIXME do poprawy gdy narrationScript bedzie zawieral informacje o czasie wyswietlania
			// zamiast Double.MAX_VALUE tu powinna sie znalezc wartosc czasowa okreslajaca
			// kiedy napis znika poniewaz w tej chwili narrationScript nie posiada takiej informacji
			// przypisuje najwieksza dostepna wartosc
			textTrack.addCue(new TextTrackCue(Document.get().createUniqueId(), 0, Double.MAX_VALUE, baseMediaConfiguration.getNarrationText()));
			eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_TIME_UPDATE), mediaWrapper, new MediaEventHandler() {

				@Override
				public void onMediaEvent(MediaEvent event) {
					textTrack.setCurrentTime(mediaWrapper.getCurrentTime());
				}
			}, new CurrentPageScope());

		}
		super.init();
	}

	@Override
	public void play(String src) {
		flashMedia.setSrc(src);
		flashMedia.play();

	}

	@Override
	public void stop() {
		flashMedia.stop();
		eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.ON_STOP, getMediaWrapper()), getMediaWrapper());
	}

}
