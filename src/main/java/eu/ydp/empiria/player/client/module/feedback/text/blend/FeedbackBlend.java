/*
 * Copyright 2017 Young Digital Planet S.A.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
