package eu.ydp.empiria.player.client.module.inlinechoice;

import static org.mockito.Mockito.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Matchers;
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
		instance.listBox = exListBox;
	}

	@Test
	public void testInitModuleModuleSocketInteractionEventsListener() {
		ModuleSocket moduleSocket = mock(ModuleSocket.class);
		InteractionEventsListener moduleInteractionListener = mock(InteractionEventsListener.class);
		instance.initModule(moduleSocket, moduleInteractionListener);
		verify(eventsBus)
				.addHandler(Matchers.eq(PlayerEvent.getType(PlayerEventTypes.PAGE_CHANGE_STARTED)), Matchers.eq(instance), Matchers.any(EventScope.class));
	}

	@Test
	public void exListBoxShouldHideWhen_PageChangeStarted() {
		PlayerEvent event = mock(PlayerEvent.class);
		when(event.getType()).thenReturn(PlayerEventTypes.PAGE_CHANGE_STARTED);
		instance.onPlayerEvent(event);
		verify(exListBox).hidePopup();
	}

	@Test
	public void exListBoxShouldNotHideWhen_PageSwipeStarted() {
		PlayerEvent event = mock(PlayerEvent.class);
		when(event.getType()).thenReturn(PlayerEventTypes.PAGE_SWIPE_STARTED);
		instance.onPlayerEvent(event);
		verify(exListBox, never()).hidePopup();
	}

}
