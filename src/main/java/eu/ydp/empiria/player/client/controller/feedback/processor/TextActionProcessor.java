package eu.ydp.empiria.player.client.controller.feedback.processor;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
import eu.ydp.empiria.player.client.controller.feedback.FeedbackMark;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.*;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.feedback.text.TextFeedback;
import eu.ydp.empiria.player.client.module.feedback.text.blend.FeedbackBlend;
import eu.ydp.empiria.player.client.module.mathjax.common.MathJaxNative;
import eu.ydp.gwtutil.client.StringUtils;

public class TextActionProcessor extends AbstractActionProcessor {

    private TextFeedback feedbackPresenter;
    private MathJaxNative mathJaxNative;
    private FeedbackBlend feedbackBlend;

    @Inject
    public TextActionProcessor(TextFeedback feedbackPresenter, MathJaxNative mathJaxNative,
            FeedbackBlend feedbackBlend) {
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
        Widget widget = inlineBodyGeneratorSocket.generateInlineBody(element);
        JavaScriptObject mathJaxCallback = createCallback(widget, mark);
        mathJaxNative.renderMath(mathJaxCallback);
    }

    private native JavaScriptObject createCallback(Widget widget, FeedbackMark mark)/*-{
		var that = this;
		return function () {
			that.@TextActionProcessor::showFeedback(*)(widget, mark);
		};
	}-*/;

    private void showFeedback(Widget widget, FeedbackMark mark) {
        feedbackPresenter.show(widget, mark);
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
        feedbackPresenter.addCloseButtonClickHandler(createCloseButtonClickHandler());
    }

    @Override
    public Widget getView() {
        return (Widget) feedbackPresenter;
    }

    private ClickHandler createCloseButtonClickHandler() {
        return new ClickHandler() {
            @Override public void onClick(ClickEvent event) {
                clearFeedback();
            }
        };
    }
}
