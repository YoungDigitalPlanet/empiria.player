package eu.ydp.empiria.player.client.module.media.button;

import com.google.gwt.dom.client.NativeEvent;

public class MediaProgressBarAndroid extends MediaProgressBarImpl {

	@Override
	public MediaProgressBarImpl getNewInstance() {
		return new MediaProgressBarAndroid();
	}

	@Override
	protected void setPosition(NativeEvent event) {
		if (isAttached()) {
			event.preventDefault();
			int positionX = getPositionX(event);
			moveScroll(positionX > 0 ? positionX : 0, true);
			if (!isPressed()) {// robimy seeka tylko gdy zakonczono dotyk
				seekInMedia(positionX > 0 ? positionX : 0);
			}
		}
	}
}
