package eu.ydp.empiria.player.client.module.media.button;

import javax.annotation.PostConstruct;

import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.lightbox2.LightBox2;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.events.fullscreen.FullScreenEvent;
import eu.ydp.empiria.player.client.util.events.fullscreen.FullScreenEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventHandler;

public class PicturePlayerFullScreenMediaButon extends FullScreenMediaButton<PicturePlayerFullScreenMediaButon> implements MediaEventHandler,
		FullScreenEventHandler {

	String imageUrl = null;
	String title = null;

	@Inject
	private LightBox2 lightBox;

	@Inject
	private Provider<PicturePlayerFullScreenMediaButon> buttonProvider;

	@Inject
	public PicturePlayerFullScreenMediaButon(StyleNameConstants styleNames) {
		super(styleNames.QP_MEDIA_FULLSCREEN_BUTTON());
	}

	@PostConstruct
	public void postConstruct() {
		lightBox.addFullScreenEventHandler(this);
	}

	public void addImage(String url, String title) {
		this.imageUrl = url;
		this.title = title;
	}

	@Override
	public PicturePlayerFullScreenMediaButon getNewInstance() {
		return buttonProvider.get();
	}

	@Override
	public boolean isSupported() {
		return true;
	}

	public static boolean isSupported(Element element) {
		return element.hasAttribute("srcFullScreen") && !element.getAttribute("srcFullScreen").trim().isEmpty();
	}

	@Override
	protected void openFullScreen() {
		lightBox.clear();
		lightBox.addImage(imageUrl, title);
		lightBox.start();
	}

	@Override
	protected void closeFullScreen() {
		// niepotrzebne lightbox sam o to zadba
	}

	@Override
	public void handleEvent(FullScreenEvent event) {
		fullScreenOpen = event.isInFullScreen();
	}

	@Override
	public void onMediaEvent(MediaEvent event) {
		//
	}

}
