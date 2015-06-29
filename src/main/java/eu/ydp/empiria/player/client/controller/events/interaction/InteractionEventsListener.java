package eu.ydp.empiria.player.client.controller.events.interaction;

import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;

/**
 * @deprecated Listeners model is no longer available. Use {@link MediaEvent} and {@link EventsBus} for media support.
 */
@Deprecated
public interface InteractionEventsListener extends StateChangedInteractionEventListener, FeedbackInteractionEventListner, MediaInteractionEventsListener {
}
