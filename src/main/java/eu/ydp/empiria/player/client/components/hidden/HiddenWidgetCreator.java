package eu.ydp.empiria.player.client.components.hidden;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Provides possibility to add widget to RootPanel and hide it. i.e.: hidden native media controls or buttons impossible to be styled (when dummy button calls
 * hidden button)
 */
public class HiddenWidgetCreator {

    /**
     * IOS nie lubi jak kilka elelemntow audio znajduje sie obok siebie dlatego dla kazdego nowo dodawanego elementu tworzymy nowy kontener.
     */
    @Inject
    Provider<HiddenContainerWidget> containerWidget;

    public void addWidgetToHiddenContainerOnRootPanel(final Widget widget) {

        HiddenContainerWidget hiddenContainerWidget = containerWidget.get();
        hiddenContainerWidget.addWidgetToContainer(widget);
        addPanelToRootPanel(hiddenContainerWidget);

    }

    private void addPanelToRootPanel(Widget hiddenPanel) {
        RootPanel.get().add(hiddenPanel);
    }
}
