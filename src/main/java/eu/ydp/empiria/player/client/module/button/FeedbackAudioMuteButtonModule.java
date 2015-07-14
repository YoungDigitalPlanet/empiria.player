package eu.ydp.empiria.player.client.module.button;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.CurrentPageProperties;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEvent;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.feedback.FeedbackEvent;
import eu.ydp.empiria.player.client.util.events.internal.feedback.FeedbackEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.feedback.FeedbackEventTypes;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;

public class FeedbackAudioMuteButtonModule extends AbstractActivityButtonModule implements FeedbackEventHandler, PlayerEventHandler {

    public final static String STYLE_NAME__ON = "qp-feedback-audio-mute-on-button";

    public final static String STYLE_NAME__OFF = "qp-feedback-audio-mute-off-button";

    public final static String STYLE_NAME__DISABLED = "qp-feedback-audio-mute-disabled";

    private final EventsBus eventBus;
    private final CurrentPageProperties currentPageProperties;

    private boolean isMuted;

    private boolean isVisible;

    @Inject
    public FeedbackAudioMuteButtonModule(EventsBus eventBus, CurrentPageProperties currentPageProperties) {
        this.eventBus = eventBus;
        this.currentPageProperties = currentPageProperties;
        this.eventBus.addHandler(FeedbackEvent.getType(FeedbackEventTypes.MUTE), this);
        this.eventBus.addHandler(PlayerEvent.getType(PlayerEventTypes.PAGE_LOADED), this);
    }

    @Override
    protected void invokeRequest() {
        FeedbackEvent event = new FeedbackEvent(FeedbackEventTypes.MUTE, !isMuted, null);
        eventBus.fireEvent(event);
    }

    @Override
    protected String getStyleName() {
        final StringBuilder styleName = new StringBuilder(70);
        String styleSuffix = (isMuted) ? STYLE_NAME__ON : STYLE_NAME__OFF;
        styleName.append(styleSuffix);
        if (!isVisible) {
            styleName.append(' ').append(STYLE_NAME__DISABLED);
        }
        return styleName.toString();
    }

    @Override
    public void onDeliveryEvent(DeliveryEvent deliveryEvent) {
        // handling events moved to event bus
    }

    @Override
    public void onFeedbackEvent(FeedbackEvent event) {
        isMuted = event.isMuted();
        updateStyleName();
    }

    @Override
    public void onPlayerEvent(PlayerEvent event) {
        isVisible = currentPageProperties.hasInteractiveModules();
        updateStyleName();
    }

}
