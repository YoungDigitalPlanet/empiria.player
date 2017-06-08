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

import com.google.common.collect.Lists;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.feedback.FeedbackMark;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackAction;
import eu.ydp.empiria.player.client.module.core.base.SimpleModuleBase;
import eu.ydp.empiria.player.client.module.core.flow.Resetable;

import java.util.List;

public abstract class AbstractActionProcessor extends SimpleModuleBase implements FeedbackActionProcessor, Resetable {

    protected InlineBodyGeneratorSocket inlineBodyGenerator;

    @Override
    public List<FeedbackAction> processActions(List<FeedbackAction> actions, InlineBodyGeneratorSocket inlineBodyGenerator, FeedbackMark mark) {
        this.inlineBodyGenerator = inlineBodyGenerator;

        hideModule();

        return processActions(actions, mark);
    }

    private List<FeedbackAction> processActions(List<FeedbackAction> actions, FeedbackMark mark) {
        List<FeedbackAction> processedActions = Lists.newArrayList();
        for (FeedbackAction action : actions) {
            if (canProcessAction(action)) {
                processSingleAction(action, mark);
                processedActions.add(action);
            }
        }

        return processedActions;
    }

    @Override
    public void reset() {
        hideModule();
    }

    protected abstract void hideModule();

}
