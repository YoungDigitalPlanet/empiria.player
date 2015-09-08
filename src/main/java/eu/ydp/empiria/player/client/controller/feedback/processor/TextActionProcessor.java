package eu.ydp.empiria.player.client.controller.feedback.processor;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.query.client.impl.ConsoleBrowser;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.feedback.FeedbackMark;
import eu.ydp.empiria.player.client.controller.feedback.counter.FeedbackNotifyCounter;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackAction;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.ShowTextAction;
import eu.ydp.empiria.player.client.module.feedback.text.TextFeedback;
import eu.ydp.empiria.player.client.module.feedback.text.blend.FeedbackBlend;
import eu.ydp.empiria.player.client.module.mathjax.common.MathJaxNative;
import eu.ydp.gwtutil.client.StringUtils;
import eu.ydp.gwtutil.client.operator.MatchOperator;

public class TextActionProcessor extends AbstractActionProcessor {

    private TextFeedback feedbackPresenter;
    private MathJaxNative mathJaxNative;
    private FeedbackBlend feedbackBlend;
    private final FeedbackNotifyCounter feedbackNotifyCounter;

    @Inject
    public TextActionProcessor(TextFeedback feedbackPresenter, MathJaxNative mathJaxNative, FeedbackBlend feedbackBlend,
                               FeedbackNotifyCounter feedbackNotifyCounter) {
        this.feedbackPresenter = feedbackPresenter;
        this.mathJaxNative = mathJaxNative;
        this.feedbackBlend = feedbackBlend;
        this.feedbackNotifyCounter = feedbackNotifyCounter;
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
    public void processSingleAction(FeedbackAction action, FeedbackMark mark) {
        ShowTextAction textAction = (ShowTextAction) action;
        feedbackNotifyCounter.add(textAction);

        Element element = textAction.getContent().getValue();
        Widget widget = inlineBodyGenerator.generateInlineBody(element);
        feedbackPresenter.setFeedbackContent(widget, mark);

        JavaScriptObject mathJaxCallback = createCallback(textAction);
        mathJaxNative.renderMath(mathJaxCallback);
    }

    private native JavaScriptObject createCallback(ShowTextAction action)/*-{
        var that = this;
        return function () {
            that.@TextActionProcessor::showModule(*)(action);
        };
    }-*/;

    private void showModule(ShowTextAction action) {
        feedbackPresenter.showModule();

        boolean shouldShowFeedback = shouldShowFeedback(action);
        if (shouldShowFeedback) {
            showFeedback();
        }
    }

    private void showFeedback(){
        feedbackPresenter.showFeedback();
        feedbackBlend.show(feedbackPresenter);
    }

    private boolean shouldShowFeedback(ShowTextAction action) {
        if (action.hasNotify()) {
            String notifyOperator = action.getNotifyOperator();
            MatchOperator operator = MatchOperator.getOperator(notifyOperator);
            int count = feedbackNotifyCounter.getCount(action);
            return operator.match(count, action.getNotify());
        }
        return true;
    }

    @Override
    protected final void hideModule() {
        hideFeedback();
        feedbackPresenter.hideModule();
    }

    private void hideFeedback() {
        feedbackPresenter.hideFeedback();
        feedbackBlend.hide();
    }

    @Override
    public void initModule(Element element) {
        hideModule();
        feedbackPresenter.addCloseButtonClickHandler(createCloseButtonClickHandler());
        feedbackPresenter.addShowButtonClickHandler(createShowButtonClickHandler());
    }

    @Override
    public Widget getView() {
        return feedbackPresenter.asWidget();
    }

    private ClickHandler createCloseButtonClickHandler() {
        return new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                hideFeedback();
            }
        };
    }

    private ClickHandler createShowButtonClickHandler() {
        return new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                showFeedback();
            }
        };
    }
}
