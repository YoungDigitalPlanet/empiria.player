package eu.ydp.empiria.player.client.controller.feedback.player;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.module.media.MediaWrapper;

/**
 * This is needed to enable native events on media object. 
 * In other case sound can be heard, but will not produce any @HTML5MediaEventsType events.
 * Panel is hidden it because contains ugly, native controls.
 */
public class MediaObjectToDocumentInsertManager {

	public void addMediaToDocumentAndHideControls(Object mediaWrapperObject) {
		FlowPanel panel = createPanel();
		hidePanel(panel);
		addMediaObjectToPanel(mediaWrapperObject, panel);
		addPanelToRootPanel(panel);
	}

	private void addPanelToRootPanel(FlowPanel hiddenPanel) {
		RootPanel.get().add(hiddenPanel);
	}

	private void addMediaObjectToPanel(Object mediaWrapperObject, FlowPanel panel) {
		MediaWrapper<?> mediaWrapper = (MediaWrapper<?>) mediaWrapperObject;
		Object mediaObject = mediaWrapper.getMediaObject();
		Widget mediaWidget = (Widget) mediaObject;
		panel.add(mediaWidget);
	}

	private FlowPanel createPanel() {
		return new FlowPanel();
	}

	private void hidePanel(FlowPanel panel) {
		Element element = panel.getElement();
		Style style = element.getStyle();
		
		style.setWidth(1d, Unit.PX);
		style.setHeight(1d, Unit.PX);
		style.setVisibility(Visibility.HIDDEN);
	}

}
