package eu.ydp.empiria.player.client.preloader.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class InfinityProgressWidget extends Composite implements ProgressView {

    private static InfinityProgressWidgetUiBinder uiBinder = GWT.create(InfinityProgressWidgetUiBinder.class);

    interface InfinityProgressWidgetUiBinder extends UiBinder<Widget, InfinityProgressWidget> {
    }

    public InfinityProgressWidget() {
        initWidget(uiBinder.createAndBindUi(this));
    }

}
