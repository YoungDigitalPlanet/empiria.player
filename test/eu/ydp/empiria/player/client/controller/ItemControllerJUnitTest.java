package eu.ydp.empiria.player.client.controller;

import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import eu.ydp.empiria.player.client.controller.communication.DisplayContentOptions;
import eu.ydp.empiria.player.client.controller.communication.ItemData;
import eu.ydp.empiria.player.client.controller.events.interaction.StateChangedInteractionEvent;
import eu.ydp.empiria.player.client.controller.session.sockets.ItemSessionSocket;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.state.StateChangeEvent;
import eu.ydp.empiria.player.client.util.events.state.StateChangeEventTypes;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;

@RunWith(MockitoJUnitRunner.class)
public class ItemControllerJUnitTest {

	@InjectMocks
	private ItemController testObj;
	@Mock
	private ItemSessionSocket itemSessionSocket;
	@Mock
	private AssessmentControllerFactory controllerFactory;
	@Mock
	private ItemData itemData;
	@Mock
	private EventsBus eventsBus;

	@Before
	public void setup() {

	}

	@Test
	public void shouldNotUpdateStateOnStateChanged() {
		// given
		// when(itemData.data).thenReturn(new XmlData(null, null));
		itemData.data = mock(XmlData.class);

		Map<String, Outcome> outcomeVariables = new HashMap<String, Outcome>();
		Item item = mock(Item.class);
		when(controllerFactory.getItem(null, outcomeVariables, null)).thenReturn(item);

		StateChangedInteractionEvent scie = new StateChangedInteractionEvent(true, true, null);
		StateChangeEvent event = new StateChangeEvent(StateChangeEventTypes.STATE_CHANGED, scie);

		DisplayContentOptions options = mock(DisplayContentOptions.class);
		when(options.useSkin()).thenReturn(true);
		testObj.init(options);

		// when
		testObj.onStateChange(event);

		// then
		verifyNoMoreInteractions(itemSessionSocket);
	}
}
