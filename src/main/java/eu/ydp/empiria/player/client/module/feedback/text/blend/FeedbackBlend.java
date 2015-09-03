package eu.ydp.empiria.player.client.module.feedback.text.blend;

import com.google.common.base.Optional;
import com.google.gwt.dom.client.NativeEvent;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.controller.multiview.touch.TouchReservationCommand;
import eu.ydp.empiria.player.client.module.feedback.FeedbackStyleNameConstants;
import eu.ydp.empiria.player.client.module.feedback.text.TextFeedback;
import eu.ydp.gwtutil.client.event.factory.Command;
import eu.ydp.gwtutil.client.event.factory.EventHandlerProxy;
import eu.ydp.gwtutil.client.event.factory.UserInteractionHandlerFactory;
import eu.ydp.gwtutil.client.proxy.RootPanelDelegate;

@Singleton
public class FeedbackBlend {

    private final FeedbackBlendView view;
    private final FeedbackStyleNameConstants styleNameConstants;

    private Optional<TextFeedback> textFeedback;

    @Inject
    public FeedbackBlend(FeedbackBlendView view, FeedbackStyleNameConstants styleNameConstants, RootPanelDelegate rootPanelDelegate,
                         UserInteractionHandlerFactory userInteractionHandlerFactory, TouchReservationCommand touchReservationCommand) {
        this.view = view;
        this.styleNameConstants = styleNameConstants;
        textFeedback = Optional.absent();

        rootPanelDelegate.getRootPanel().add(view.asWidget());
        userInteractionHandlerFactory.applyUserClickHandler(createClickCommand(), view.asWidget());

        EventHandlerProxy userTouchStartHandler = userInteractionHandlerFactory.createUserTouchStartHandler(touchReservationCommand);
        userTouchStartHandler.apply(view.asWidget());
    }

    private Command createClickCommand() {
        return new Command() {
            @Override
            public void execute(NativeEvent event) {
                if (textFeedback.isPresent()) {
                    textFeedback.get().hideFeedback();
                }
                hide();
            }
        };
    }

    public void show(TextFeedback textFeedback) {
        view.removeStyleName(styleNameConstants.QP_FEEDBACK_BLEND_HIDDEN());
        this.textFeedback = Optional.of(textFeedback);
    }

    public void hide() {
        view.addStyleName(styleNameConstants.QP_FEEDBACK_BLEND_HIDDEN());
        textFeedback = Optional.absent();
    }
}
