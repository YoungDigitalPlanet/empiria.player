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
import com.google.inject.*;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.feedback.FeedbackMark;
import eu.ydp.empiria.player.client.controller.feedback.player.FeedbackSoundPlayer;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.*;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.feedback.*;
import java.util.List;

@Singleton
public class SoundActionProcessor implements FeedbackEventHandler, FeedbackActionProcessor {

    @Inject
    private FeedbackSoundPlayer player;

    private boolean isMuted;

    @Inject
    public SoundActionProcessor(EventsBus eventBus) {
        eventBus.addHandler(FeedbackEvent.getType(FeedbackEventTypes.MUTE), this);
    }

    @Override
    public List<FeedbackAction> processActions(List<FeedbackAction> actions, InlineBodyGeneratorSocket inlineBodyGeneratorSocket, FeedbackMark mark) {
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
    public boolean canProcessAction(FeedbackAction action) {
        boolean canProcess = false;

        if (action instanceof FeedbackUrlAction) {
            FeedbackUrlAction urlAction = (FeedbackUrlAction) action;
            canProcess = ActionType.NARRATION.equalsToString(urlAction.getType());
        }

        return canProcess;
    }

    @Override
    public void processSingleAction(FeedbackAction action, FeedbackMark mark) {
        if (action instanceof ShowUrlAction && !isMuted) {
            ShowUrlAction urlAction = ((ShowUrlAction) action);

            if (urlAction.getSources().size() > 0) {
                player.play(urlAction.getSourcesWithTypes());
            } else {
                List<String> sources = Lists.newArrayList(urlAction.getHref());
                player.play(sources);
            }
        }
    }

    @Override
    public void onFeedbackEvent(FeedbackEvent event) {
        isMuted = event.isMuted();
    }

}
