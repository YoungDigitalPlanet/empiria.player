package eu.ydp.empiria.player.client.controller.feedback.processor;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.feedback.player.FeedbackSoundPlayer;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.ActionType;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackAction;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackUrlAction;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.ShowUrlAction;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.feedback.FeedbackEvent;
import eu.ydp.empiria.player.client.util.events.internal.feedback.FeedbackEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.feedback.FeedbackEventTypes;

import java.util.List;

public class SoundActionProcessor extends AbstractFeedbackActionProcessor implements FeedbackEventHandler {

    @Inject
    private FeedbackSoundPlayer player;

    private boolean isMuted;

    @Inject
    public SoundActionProcessor(EventsBus eventBus) {
        eventBus.addHandler(FeedbackEvent.getType(FeedbackEventTypes.MUTE), this);
    }

    @Override
    protected boolean canProcessAction(FeedbackAction action) {
        boolean canProcess = false;

        if (action instanceof FeedbackUrlAction) {
            FeedbackUrlAction urlAction = (FeedbackUrlAction) action;
            canProcess = ActionType.NARRATION.equalsToString(urlAction.getType());
        }

        return canProcess;
    }

    @Override
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
    protected void clearFeedback() {
    }

    @Override
    public void onFeedbackEvent(FeedbackEvent event) {
        isMuted = event.isMuted();
    }

}
