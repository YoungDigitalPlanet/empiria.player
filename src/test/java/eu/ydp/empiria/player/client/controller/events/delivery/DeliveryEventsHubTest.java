package eu.ydp.empiria.player.client.controller.events.delivery;

import eu.ydp.empiria.player.client.controller.events.interaction.FeedbackInteractionEvent;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DeliveryEventsHubTest {

    @InjectMocks
    private DeliveryEventsHub testObj;
    @Mock
    private DeliveryEventsListener deliveryEventsListener;
    @Mock
    private FeedbackInteractionEvent feedbackInteractionEvent;
    @Captor
    private ArgumentCaptor<DeliveryEvent> deliveryEventCaptor;

    private Map<String, Object> paramsMap;

    @Before
    public void init() {
        paramsMap = new HashMap<>();
        testObj.addDeliveryEventsListener(deliveryEventsListener);
        when(feedbackInteractionEvent.getParams()).thenReturn(paramsMap);
    }

    @Test
    public void shouldFireFeedbackMuteDeliveryEvent_onFeedbackMuteInteractionEvent() {
        // given
        when(feedbackInteractionEvent.getType()).thenReturn(InteractionEventType.FEEDBACK_MUTE);

        // when
        testObj.onFeedbackSound(feedbackInteractionEvent);
        verify(deliveryEventsListener).onDeliveryEvent(deliveryEventCaptor.capture());

        // then
        DeliveryEvent value = deliveryEventCaptor.getValue();
        DeliveryEventType type = value.getType();
        assertThat(type).isEqualTo(DeliveryEventType.FEEDBACK_MUTE);
    }

    @Test
    public void shouldFireFeedbackSoundDeliveryEvent_onFeedbackSoundInteractionEvent() {
        // given
        when(feedbackInteractionEvent.getType()).thenReturn(InteractionEventType.FEEDBACK_SOUND);

        // when
        testObj.onFeedbackSound(feedbackInteractionEvent);
        verify(deliveryEventsListener).onDeliveryEvent(deliveryEventCaptor.capture());

        // then
        DeliveryEvent value = deliveryEventCaptor.getValue();
        DeliveryEventType type = value.getType();
        assertThat(type).isEqualTo(DeliveryEventType.FEEDBACK_SOUND);
    }
}