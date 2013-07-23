package eu.ydp.empiria.player.client.components.hidden;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.inject.Instance;

/**
 * Provides possibility to add widget to RootPanel and hide it.
 * i.e.: hidden native media controls or buttons impossible to
 * be styled (when dummy button calls hidden button)
 */
public class HiddenWidgetCreator {


	@Inject Instance<HiddenContainerWidget> containerWidget;
	/**
	 * Returns reference to hidden flow panel
	 */
	public IsWidget addWidgetToDocumentAndHide(final Widget widget) {
		HiddenContainerWidget hiddenContainerWidget = containerWidget.get();
		hiddenContainerWidget.addWidgetToContainer(widget);
		addPanelToRootPanel(hiddenContainerWidget);
		return hiddenContainerWidget;
	}

	private void addPanelToRootPanel(Widget hiddenPanel) {
		RootPanel.get().add(hiddenPanel);
	}
}
