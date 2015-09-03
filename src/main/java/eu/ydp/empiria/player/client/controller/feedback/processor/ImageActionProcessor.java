package eu.ydp.empiria.player.client.controller.feedback.processor;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
import eu.ydp.empiria.player.client.controller.feedback.FeedbackMark;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.*;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.feedback.image.ImageFeedback;

public class ImageActionProcessor extends AbstractActionProcessor {

    private ImageFeedback feedbackPresenter;

    @Inject
    public ImageActionProcessor(ImageFeedback feedbackPresenter) {
        this.feedbackPresenter = feedbackPresenter;
    }

    @Override
    public boolean canProcessAction(FeedbackAction action) {
        boolean canProcess = false;

        if (action instanceof FeedbackUrlAction) {
            FeedbackUrlAction urlAction = (FeedbackUrlAction) action;
            canProcess = ActionType.IMAGE.equalsToString(urlAction.getType());
        }

        return canProcess;
    }

    @Override
    public void processSingleAction(FeedbackAction action, FeedbackMark mark) {
        FeedbackUrlAction imageAction = (FeedbackUrlAction) action;
        feedbackPresenter.setUrl(imageAction.getHref());
        feedbackPresenter.show(mark);
    }

    @Override
    public void clearFeedback() {
        feedbackPresenter.setUrl("");
        feedbackPresenter.hide();
    }

    @Override
    public void initModule(Element element) {
        feedbackPresenter.hide();
    }

    @Override
    public Widget getView() {
        return feedbackPresenter.asWidget();
    }

}
