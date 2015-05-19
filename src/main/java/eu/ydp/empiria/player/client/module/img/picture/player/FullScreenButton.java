package eu.ydp.empiria.player.client.module.img.picture.player;

import com.google.gwt.xml.client.Element;
import eu.ydp.empiria.player.client.module.media.button.PicturePlayerFullScreenMediaButton;

public class FullScreenButton {

	private PicturePlayerFullScreenMediaButton fullScreenMediaButton;


	private void initFullScreenMediaButton(Element element, boolean template) {
		if (PicturePlayerFullScreenMediaButton.isSupported(element) && !template) {
			fullScreenMediaButton.init(element);
		}
	}

}
