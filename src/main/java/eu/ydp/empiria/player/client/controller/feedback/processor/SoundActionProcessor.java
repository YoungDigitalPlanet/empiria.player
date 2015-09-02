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
                processSingleAction(action);
                processedActions.add(action);
            }
        }

        return processedActions;
    }

    protected boolean canProcessAction(FeedbackAction action) {
        boolean canProcess = false;

        if (action instanceof FeedbackUrlAction) {
            FeedbackUrlAction urlAction = (FeedbackUrlAction) action;
            canProcess = ActionType.NARRATION.equalsToString(urlAction.getType());
        }

        return canProcess;
    }

    protected void processSingleAction(FeedbackAction action) {
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
