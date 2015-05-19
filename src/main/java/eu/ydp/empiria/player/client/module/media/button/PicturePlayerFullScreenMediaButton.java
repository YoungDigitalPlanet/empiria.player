package eu.ydp.empiria.player.client.module.media.button;

import com.google.gwt.xml.client.Element;
import com.google.inject.*;
import eu.ydp.empiria.player.client.lightbox.*;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.events.fullscreen.*;
import eu.ydp.empiria.player.client.util.events.media.*;
import eu.ydp.gwtutil.client.xml.XMLUtils;

public class PicturePlayerFullScreenMediaButton extends FullScreenMediaButton<PicturePlayerFullScreenMediaButton> implements MediaEventHandler,
																															 FullScreenEventHandler {
	@Inject
	private FullScreenModeProvider fullScreenModeProvider;
	@Inject
	private Provider<PicturePlayerFullScreenMediaButton> buttonProvider;

	private String imageUrl = null;
	private String title = null;

	private FullScreen fullScreen;

	@Inject
	public PicturePlayerFullScreenMediaButton(StyleNameConstants styleNames) {
		super(styleNames.QP_MEDIA_FULLSCREEN_BUTTON());
	}

	public void init(Element element) {
		super.init();
		Element titleNodes = XMLUtils.getFirstElementWithTagName(element, "title");

		this.title = XMLUtils.getTextFromChilds(titleNodes);
		this.imageUrl = element.getAttribute("srcFullScreen");

		String mode = element.getAttribute("fullscreenMode");
		this.fullScreen = fullScreenModeProvider.getFullscreen(mode);
	}

	@Override
	public PicturePlayerFullScreenMediaButton getNewInstance() {
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
		fullScreen.openImage(imageUrl, title);
	}

	@Override
	protected void closeFullScreen() {
	}

	@Override
	public void handleEvent(FullScreenEvent event) {
	}

	@Override
	public void onMediaEvent(MediaEvent event) {
	}
}
