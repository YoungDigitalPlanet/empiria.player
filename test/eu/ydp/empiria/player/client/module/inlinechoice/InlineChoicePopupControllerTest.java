package eu.ydp.empiria.player.client.module.inlinechoice;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.gwt.junit.GWTMockUtilities;
import com.google.inject.Binder;
import com.google.inject.Module;

import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.util.events.scope.EventScope;
import eu.ydp.gwtutil.client.components.exlistbox.ExListBox;

@SuppressWarnings("PMD")
public class InlineChoicePopupControllerTest extends AbstractTestBaseWithoutAutoInjectorInit {
	EventsBus eventsBus = null;
	InlineChoicePopupController instance = null;
	ExListBox exListBox = null;

	@BeforeClass
	public static void disarm() {
		GWTMockUtilities.disarm();
	}

	@AfterClass
	public static void rearm() {
		GWTMockUtilities.restore();
	}

	private static class CustomGuiceModule implements Module {
		@Override
		public void configure(Binder binder) {
			binder.bind(ExListBox.class).toInstance(mock(ExListBox.class));
		}
	}

	@Before
	public void before() {
		setUp(new Class<?>[] {}, new Class<?>[] {}, new Class<?>[] { EventsBus.class }, new CustomGuiceModule());
		eventsBus = injector.getInstance(EventsBus.class);
		instance = injector.getInstance(InlineChoicePopupController.class);
		exListBox = injector.getInstance(ExListBox.class);
	}

	@Test
	public void testInitModuleModuleSocketInteractionEventsListener() {
		ModuleSocket moduleSocket = mock(ModuleSocket.class);
		InteractionEventsListener moduleInteractionListener = mock(InteractionEventsListener.class);
		instance.initModule(moduleSocket, moduleInteractionListener);
		verify(eventsBus).addHandler(Mockito.eq(PlayerEvent.getType(PlayerEventTypes.PAGE_SWIPE_STARTED)), Mockito.eq(instance), Mockito.any(EventScope.class));
	}

	@Test
	public void testOnPlayerEvent() {
		PlayerEvent event = mock(PlayerEvent.class);
		when(event.getType()).thenReturn(PlayerEventTypes.PAGE_SWIPE_STARTED);
		instance.listBox = exListBox;
		instance.onPlayerEvent(event);
		verify(exListBox).hidePopup();
		Set<PlayerEventTypes> types = new HashSet<PlayerEventTypes>(Arrays.asList(PlayerEventTypes.values()));
		types.remove(PlayerEventTypes.PAGE_SWIPE_STARTED);
		for (PlayerEventTypes type : types) {
			when(event.getType()).thenReturn(type);
			instance.onPlayerEvent(event);
		}
		verify(exListBox).hidePopup();

	}

}
