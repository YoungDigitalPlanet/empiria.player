package eu.ydp.empiria.player.client.controller.feedback.processor;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
import eu.ydp.empiria.player.client.controller.feedback.blend.FeedbackBlend;
import eu.ydp.empiria.player.client.controller.feedback.processor.ActionProcessorHelper;
import eu.ydp.empiria.player.client.controller.feedback.processor.FeedbackActionProcessor;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.ActionProcessorTarget;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackAction;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.ShowTextAction;
import eu.ydp.empiria.player.client.module.IResetable;
import eu.ydp.empiria.player.client.module.ISimpleModule;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.ParentedModuleBase;
import eu.ydp.empiria.player.client.module.feedback.text.TextFeedback;
import eu.ydp.empiria.player.client.module.mathjax.common.MathJaxNative;
import eu.ydp.gwtutil.client.StringUtils;

import java.util.List;

public class TextActionProcessor extends ParentedModuleBase implements FeedbackActionProcessor, ActionProcessorTarget, ISimpleModule, IResetable {

    private ActionProcessorHelper helper;

    @Inject
    private TextFeedback feedbackPresenter;
    @Inject
    private MathJaxNative mathJaxNative;
    @Inject
    private FeedbackBlend feedbackBlend;

    private InlineBodyGeneratorSocket inlineBodyGeneratorSocket;

    @Override
    public List<FeedbackAction> processActions(List<FeedbackAction> actions, InlineBodyGeneratorSocket inlineBodyGeneratorSocket) {
        this.inlineBodyGeneratorSocket = inlineBodyGeneratorSocket;
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

        if (action instanceof ShowTextAction) {
            ShowTextAction textAction = (ShowTextAction) action;
            String nodeValue = textAction.getContent().getValue().getChildNodes().toString();
            canProcess = !StringUtils.EMPTY_STRING.equals(nodeValue);
        }

        return canProcess;
    }

    @Override
    public void processSingleAction(FeedbackAction action) {
        if (action instanceof ShowTextAction) {
            ShowTextAction textAction = (ShowTextAction) action;
            Element element = textAction.getContent().getValue();
            Widget widget = inlineBodyGeneratorSocket.generateInlineBody(element);
            JavaScriptObject mathJaxCallback = createCallback(widget);
            mathJaxNative.renderMath(mathJaxCallback);
        }
    }

    private native JavaScriptObject createCallback(Widget widget)/*-{
        var that = this;
        return function () {
            that.@TextActionProcessor::showFeedback(*)(widget);
        };
    }-*/;

    private void showFeedback(Widget widget) {
        feedbackPresenter.show(widget);
        feedbackBlend.show(feedbackPresenter);
    }

    @Override
    public void clearFeedback() {
        feedbackPresenter.hide();
        feedbackBlend.hide();
    }

    @Override
    public void initModule(Element element, ModuleSocket ms, InteractionEventsListener iel) {
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
