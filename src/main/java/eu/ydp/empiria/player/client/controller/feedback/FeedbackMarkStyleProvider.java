package eu.ydp.empiria.player.client.controller.feedback;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.feedback.FeedbackStyleNameConstants;
import javax.inject.Singleton;

@Singleton
public class FeedbackMarkStyleProvider {

    @Inject
    private FeedbackStyleNameConstants styleNames;

    public String getStyleName(FeedbackMark mark) {

        switch (mark) {
        case ALL_OK:
            return styleNames.QP_FEEDBACK_ALLOK();
        case OK:
            return styleNames.QP_FEEDBACK_OK();
        case WRONG:
            return styleNames.QP_FEEDBACK_WRONG();
        default:
            return "";
        }
    }
}
