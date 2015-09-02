package eu.ydp.empiria.player.client.controller.feedback;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.feedback.FeedbackStyleNameConstants;
import javax.inject.Singleton;

@Singleton
public class FeedbackTypeStyleProvider {

    @Inject
    private FeedbackStyleNameConstants styleNames;

    private String styleName;

    public void prepareStyleName(FeedbackProperties properties) {
        if (properties.getBooleanProperty(FeedbackPropertyName.ALL_OK)) {
            styleName = styleNames.QP_FEEDBACK_ALLOK();
        } else if (properties.getBooleanProperty(FeedbackPropertyName.OK)) {
            styleName = styleNames.QP_FEEDBACK_OK();
        } else {
            styleName = styleNames.QP_FEEDBACK_WRONG();
        }
    }

    public String getStyleName() {
        return styleName;
    }
}
