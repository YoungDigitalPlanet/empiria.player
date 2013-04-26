package eu.ydp.empiria.player.client.controller.feedback.player;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;

import eu.ydp.empiria.player.client.module.media.MediaWrapper;

/**
 * This is needed to enable native events on media object. 
 * In other case sound can be heard, but will not produce any @HTML5MediaEventsType events.
 * Panel is hidden it because contains ugly, native controls.
 */
public class MediaObjectToDocumentInsertManager {

	public void addMediaToDocumentAndHideControls(Object object) {
		FlowPanel hiddenPanel = createPanel();
		hidePanel(hiddenPanel);
		addMediaObjectToPanel(object, hiddenPanel);
		addPanelToRootPanel(hiddenPanel);
	}

	private void addPanelToRootPanel(FlowPanel hiddenPanel) {
		RootPanel.get().add(hiddenPanel);
	}

	private void addMediaObjectToPanel(Object object, FlowPanel hiddenPanel) {
		MediaWrapper<?> mediaWrapper = (MediaWrapper<?>) object;
		hiddenPanel.add(mediaWrapper.getMediaObject());
	}

	private FlowPanel createPanel() {
		return new FlowPanel();
	}

	private void hidePanel(FlowPanel hiddenPanel) {
		hiddenPanel.getElement().getStyle().setWidth(1d, Unit.PX);
		hiddenPanel.getElement().getStyle().setHeight(1d, Unit.PX);
		hiddenPanel.getElement().getStyle().setVisibility(Visibility.VISIBLE);
	}

}
