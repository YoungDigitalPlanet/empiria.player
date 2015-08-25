package eu.ydp.empiria.player.client.controller.feedback.blend;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.module.feedback.FeedbackStyleNameConstants;
import eu.ydp.gwtutil.client.proxy.RootPanelDelegate;

@Singleton
public class FeedbackBlend {

    private final FeedbackBlendView view;
    private final FeedbackStyleNameConstants styleNameConstants;

    @Inject
    public FeedbackBlend(FeedbackBlendView view, FeedbackStyleNameConstants styleNameConstants, RootPanelDelegate rootPanelDelegate) {
        this.view = view;
        this.styleNameConstants = styleNameConstants;

        rootPanelDelegate.getRootPanel().add(view.asWidget());
    }

    public void show() {
        view.removeStyleName(styleNameConstants.QP_FEEDBACK_BLEND_HIDDEN());
    }

    public void hide() {
        view.addStyleName(styleNameConstants.QP_FEEDBACK_BLEND_HIDDEN());
    }
}
