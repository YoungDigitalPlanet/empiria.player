package eu.ydp.empiria.player.client.components.hidden;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public class HiddenContainerWidget extends Composite {

    private static HiddenContainerWidgetUiBinder uiBinder = GWT.create(HiddenContainerWidgetUiBinder.class);

    interface HiddenContainerWidgetUiBinder extends UiBinder<Widget, HiddenContainerWidget> {
    }

    @UiField
    FlowPanel container;

    public HiddenContainerWidget() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    public void addWidgetToContainer(IsWidget widget) {
        container.add(widget);
    }

}
