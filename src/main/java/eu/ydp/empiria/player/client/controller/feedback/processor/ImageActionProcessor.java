package eu.ydp.empiria.player.client.controller.feedback.processor;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.ActionProcessorTarget;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.ActionType;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackAction;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackUrlAction;
import eu.ydp.empiria.player.client.module.IResetable;
import eu.ydp.empiria.player.client.module.ISimpleModule;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.ParentedModuleBase;
import eu.ydp.empiria.player.client.module.feedback.image.ImageFeedback;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;

import java.util.List;

public class ImageActionProcessor extends ParentedModuleBase implements FeedbackActionProcessor, ActionProcessorTarget, ISimpleModule, IResetable {

    private ActionProcessorHelper helper;

    @Inject
    private ImageFeedback feedbackPresenter;

    @Override
    public List<FeedbackAction> processActions(List<FeedbackAction> actions, InlineBodyGeneratorSocket inlineBodyGeneratorSocket) {
        return getHelper().processActions(actions, inlineBodyGeneratorSocket);
    }

    private ActionProcessorHelper getHelper() {
        if (helper == null) {
            helper = new ActionProcessorHelper(this);
        }

        return helper;
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
    public void processSingleAction(FeedbackAction action) {
        if (action instanceof FeedbackUrlAction) {
            FeedbackUrlAction imageAction = (FeedbackUrlAction) action;
            feedbackPresenter.setUrl(imageAction.getHref());
            feedbackPresenter.show();
        }
    }

    @Override
    public void clearFeedback() {
        feedbackPresenter.setUrl("");
        feedbackPresenter.hide();
    }

    @Override
    public void initModule(Element element, ModuleSocket ms, EventsBus eventsBus) {
        initModule(ms);
        feedbackPresenter.hide();
    }

    @Override
    public Widget getView() {
        return (Widget) feedbackPresenter;
    }

    @Override
    public void reset() {
        clearFeedback();
    }

}
