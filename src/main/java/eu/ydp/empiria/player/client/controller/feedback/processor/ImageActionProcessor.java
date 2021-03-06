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

package eu.ydp.empiria.player.client.controller.feedback.processor;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.feedback.FeedbackMark;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.ActionType;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackAction;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackUrlAction;
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
    public void hideModule() {
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
