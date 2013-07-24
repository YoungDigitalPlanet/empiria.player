package eu.ydp.empiria.player.client.controller.feedback.player;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.components.hidden.HiddenWidgetCreator;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;

/**
 * This is needed to enable native events on media object. 
 * In other case sound can be heard, but will not produce any @HTML5MediaEventsType events.
 * Panel is hidden it because contains ugly, native controls.
 */
public class HideNativeMediaControlsManager {

	@Inject
	private HiddenWidgetCreator hiddenWidgetCreator;
	
	public void addToDocumentAndHideControls(MediaWrapper<Widget> mediaWrapper) {
		Widget mediaObject = mediaWrapper.getMediaObject();				
		hiddenWidgetCreator.addWidgetToHiddenContainerOnRootPanel(mediaObject);
	}

}
