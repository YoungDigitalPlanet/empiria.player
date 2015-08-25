package eu.ydp.empiria.player.client.controller.feedback.blend;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class FeedbackBlendViewImpl extends Composite implements FeedbackBlendView {
    private static FeedbackBlendUiBinder uiBinder = GWT.create(FeedbackBlendUiBinder.class);

    @UiTemplate("FeedbackBlendView.ui.xml")
    interface FeedbackBlendUiBinder extends UiBinder<Widget, FeedbackBlendViewImpl> {
    }

    public FeedbackBlendViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }
}
