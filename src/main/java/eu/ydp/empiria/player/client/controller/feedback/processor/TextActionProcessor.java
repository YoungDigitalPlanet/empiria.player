package eu.ydp.empiria.player.client.controller.feedback.processor;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.feedback.FeedbackMark;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackAction;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.ShowTextAction;
import eu.ydp.empiria.player.client.module.feedback.text.TextFeedback;
import eu.ydp.empiria.player.client.module.feedback.text.blend.FeedbackBlend;
import eu.ydp.empiria.player.client.module.mathjax.common.MathJaxNative;
import eu.ydp.gwtutil.client.StringUtils;

public class TextActionProcessor extends AbstractActionProcessor {

    private TextFeedback feedbackPresenter;
    private MathJaxNative mathJaxNative;
    private FeedbackBlend feedbackBlend;

    @Inject
    public TextActionProcessor(TextFeedback feedbackPresenter, MathJaxNative mathJaxNative, FeedbackBlend feedbackBlend) {
        this.feedbackPresenter = feedbackPresenter;
        this.mathJaxNative = mathJaxNative;
        this.feedbackBlend = feedbackBlend;
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
        Element element = textAction.getContent().getValue();
        Widget widget = inlineBodyGenerator.generateInlineBody(element);
        JavaScriptObject mathJaxCallback = createCallback(widget, mark);
        mathJaxNative.renderMath(mathJaxCallback);
    }

    private native JavaScriptObject createCallback(Widget widget, FeedbackMark mark)/*-{
        var that = this;
        return function () {
            that.@TextActionProcessor::addFeedback(*)(widget, mark);
        };
    }-*/;

    private void addFeedback(Widget widget, FeedbackMark mark) {
        feedbackPresenter.addFeedback(widget, mark);
        feedbackPresenter.showFeedback();
        feedbackBlend.show(feedbackPresenter);
    }

    private void showFeedback() {
        feedbackPresenter.showFeedback();
        feedbackBlend.show(feedbackPresenter);
    }

    @Override
    public void clearFeedback() {
        feedbackPresenter.hideFeedback();
        feedbackBlend.hide();
    }

    @Override
    public void initModule(Element element) {
        feedbackPresenter.hideModule();
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
                clearFeedback();
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
