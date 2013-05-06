package eu.ydp.empiria.player.client.components;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Provides possibility to add widget to RootPanel and hide it. 
 */
public class HiddenWidgetToDocumentInsertManager {

	/**
	 * Returns reference to hidden flow panel
	 */
	public FlowPanel addWidgetToDocumentAndHide(Widget widget) {
		FlowPanel panel = createPanel();
		hidePanel(panel);
		panel.add(widget);
		addPanelToRootPanel(panel);
		return panel;
	}

	private void addPanelToRootPanel(FlowPanel hiddenPanel) {
		RootPanel.get().add(hiddenPanel);
	}

	private FlowPanel createPanel() {
		return new FlowPanel();
	}

	private void hidePanel(FlowPanel panel) {
		Element element = panel.getElement();
		Style style = element.getStyle();
		
		style.setWidth(1d, Unit.PX);
		style.setHeight(1d, Unit.PX);
		style.setLeft(-1d, Unit.PX);
		style.setTop(-1d, Unit.PX);		
		style.setPosition(Position.ABSOLUTE);
		style.setVisibility(Visibility.HIDDEN);
	}

}
