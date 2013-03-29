package eu.ydp.empiria.player.client.controller.extensions.internal.sound.external;

import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.controller.extensions.internal.sound.MediaExecutor;

public interface CurrentMediaListener {

	void clearCurrentMedia();
	
	void updateCurrentMedia(MediaExecutor<? extends Widget> media);
}
