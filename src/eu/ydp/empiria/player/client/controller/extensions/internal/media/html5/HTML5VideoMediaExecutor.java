package eu.ydp.empiria.player.client.controller.extensions.internal.media.html5;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.media.Video;
import eu.ydp.empiria.player.client.media.texttrack.TextTrack;
import eu.ydp.empiria.player.client.media.texttrack.TextTrackCue;
import eu.ydp.empiria.player.client.media.texttrack.TextTrackKind;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.util.UniqueIdGenerator;

public class HTML5VideoMediaExecutor extends AbstractHTML5MediaExecutor<Video> {

	@Inject
	private UniqueIdGenerator uniqueIdGenerator;

	@Override
	public void initExecutor() {
		BaseMediaConfiguration baseMediaConfiguration = getBaseMediaConfiguration();
		if (baseMediaConfiguration != null) {
			Video media = getMedia();
			setPosterIfPresent(baseMediaConfiguration, media);
			setWidthIfPresent(baseMediaConfiguration, media);
			setHeightIfPresent(baseMediaConfiguration, media);
			setNarrationTextIfPresent(baseMediaConfiguration, media);
		}

	}

	private void setNarrationTextIfPresent(BaseMediaConfiguration baseMediaConfiguration, Video media) {
		if (baseMediaConfiguration.getNarrationText().trim().length() > 0) {
			TextTrack textTrack = media.addTrack(TextTrackKind.SUBTITLES);
			//FIXME do poprawy gdy narrationScript bedzie zawieral informacje o czasie wyswietlania
			//zamiast Double.MAX_VALUE tu powinna sie znalezc wartosc czasowa okreslajaca
			//kiedy napis znika poniewaz w tej chwili narrationScript nie posiada takiej informacji
			//przypisuje najwieksza dostepna wartosc
			textTrack.addCue(new TextTrackCue(uniqueIdGenerator.createUniqueId(), 0, Double.MAX_VALUE, baseMediaConfiguration.getNarrationText()));
		}
	}

	private void setPosterIfPresent(BaseMediaConfiguration baseMediaConfiguration, Video media) {
		if (baseMediaConfiguration.getPoster() != null && baseMediaConfiguration.getPoster().trim().length() > 0) {
			media.setPoster(baseMediaConfiguration.getPoster());
		}
	}

	private void setHeightIfPresent(BaseMediaConfiguration baseMediaConfiguration, Video media) {
		if (baseMediaConfiguration.getHeight() > 0) {
			media.setHeight(baseMediaConfiguration.getHeight() + "px");
		}
	}

	private void setWidthIfPresent(BaseMediaConfiguration baseMediaConfiguration, Video media) {
		if (baseMediaConfiguration.getWidth() > 0) {
			media.setWidth(baseMediaConfiguration.getWidth() + "px");
		}
	}

}
